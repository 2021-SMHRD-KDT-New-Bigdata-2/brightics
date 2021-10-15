from selenium import webdriver as wb
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup as bs
import pandas as pd
import time
import urllib.request as req
from urllib.request import urlopen
from urllib.request import HTTPError
# selenium head less모드 적용
options = wb.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
driver = wb.Chrome('chromedriver', chrome_options=options)

driver.get('http://www.kmib.co.kr/search/searchResult.asp?period=year&searchWord=%uC724%uC11D%uC5F4')


soup = bs(driver.page_source, 'lxml')

div = driver.find_element_by_css_selector('#searchList > div:nth-child(1) > dl > dt > a')
div.click()

# 기사 제목
title = driver.find_element_by_css_selector('#sub > div.sub_header > div > div.nwsti > h3').text

title

# 기사 날짜
date=driver.find_element_by_xpath('//*[@id="sub"]/div[1]/div/div[2]/div/div[1]/span').text

date

# 기사 내용
content=driver.find_element_by_xpath('//*[@id="articleBody"]').text

content

# 기사 url
driver.current_url

# 국민일보 기사 1년 크롤링
title_list=[]
date_list=[]
content_list=[]
url_list=[]


driver = wb.Chrome('chromedriver', chrome_options=options)
soup = bs(driver.page_source, 'lxml')

for n in range(1,385):
    driver.get(f'http://www.kmib.co.kr/search/searchResult.asp?searchWord=%uC724%uC11D%uC5F4&pageNo={n}&period=year')
    driver.implicitly_wait(2)
    for i in range(1,11):
        try:
            div = driver.find_element_by_css_selector(f'#searchList > div:nth-child({i}) > dl > dt > a')
            div.click()
            time.sleep(0.5)
            title_list.append(driver.find_element_by_css_selector('#sub > div.sub_header > div > div.nwsti > h3').text)
            date_list.append(driver.find_element_by_xpath('//*[@id="sub"]/div[1]/div/div[2]/div/div[1]/span').text)
            content_list.append(driver.find_element_by_xpath('//*[@id="articleBody"]').text)
            url_list.append(driver.current_url)
            driver.back()
            time.sleep(0.5)

        except:
            continue

driver.quit()

len(title_list)
len(date_list)
len(content_list)

# 딕셔너리 생성
info={'title':title_list, 'date':date_list,'contents':content_list,'url':url_list}

# 데이터 프레임 생성
GookMin_SEOKYEOL_pd=pd.DataFrame(info)
GookMin_SEOKYEOL_pd=GookMin_SEOKYEOL_pd.assign(candidate_id='3')

GookMin_SEOKYEOL_pd

# mysql데이터 연동 이후 데이터삽입
import sqlalchemy
engine = sqlalchemy.create_engine(
    "mysql+mysqlconnector://team1:smhrd1@project-db-stu.ddns.net:3307/campus_k_1_1006?charset=utf8")
conn = engine.connect()
GookMin_SEOKYEOL_pd.to_sql(name="news", con=engine, if_exists='append', index=False)

conn.close()

