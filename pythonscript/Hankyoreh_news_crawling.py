import requests as req
from selenium import webdriver as wb
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup as bs
import pandas as pd
import time
# selenium headless 모드 옵션 적용
options = wb.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
driver = wb.Chrome('chromedriver', chrome_options=options)

driver.get('https://search.hani.co.kr/Search?command=query&keyword=%EC%9C%A4%EC%84%9D%EC%97%B4&media=news&submedia=&sort=d&period=year&datefrom=20201014&dateto=20211014&pageseq=1')

soup = bs(driver.page_source, 'lxml')

div = driver.find_element_by_css_selector('#contents > div.search-result-section.first-child > ul > li.first-child > dl > dt > a')
div.click()

# 기사 제목
title = driver.find_element_by_css_selector('#article_view_headline > h4 > span').text

title

# 기사 날짜
date = driver.find_element_by_css_selector('#article_view_headline > p.date-time > span:nth-child(1)').text

date

# 기사 내용
contents = driver.find_element_by_css_selector('#a-left-scroll-in > div.article-text > div > div.text').text

contents

# 기사 url
driver.current_url


# 한겨레 신문 기사1년 크롤링
title_list=[]
date_list=[]
contents_list=[]
url_list=[]

driver = wb.Chrome('chromedriver', chrome_options=options)
soup = bs(driver.page_source, 'lxml')

for n in range(1,230):
    driver.get(f'https://search.hani.co.kr/Search?command=query&keyword=%EC%9C%A4%EC%84%9D%EC%97%B4&media=news&submedia=&sort=d&period=year&datefrom=20201014&dateto=20211014&pageseq={n}')
    driver.implicitly_wait(2)
    for i in range(1,11):
        try:
            div = driver.find_element_by_css_selector(f'#contents > div.search-result-section.first-child > ul > li:nth-child({i}) > dl > dt > a')
            div.click()
            time.sleep(0.8)
            title_list.append(driver.find_element_by_css_selector('#article_view_headline > h4 > span').text)
            date_list.append(driver.find_element_by_css_selector('#article_view_headline > p.date-time > span:nth-child(1)').text)
            contents_list.append(driver.find_element_by_css_selector('#a-left-scroll-in > div.article-text > div > div.text').text)
            url_list.append(driver.current_url)
            driver.back()
            time.sleep(0.8)

        except:
            continue

driver.quit()

title_list

len(date_list)

len(contents_list)

len(contents_list)

# date_list안에 담긴 수정: 부분 삭제후 new_date_list에 새로담아주기
new_date_list=[]

for i in date_list:
    new_date_list.append(i[4:])

new_date_list

len(url_list)

# 딕셔너리 생성
info = {'title':title_list, 'date':new_date_list, 'contents':contents_list, 'url':url_list}

# 데이터 프레임 생성
Hankyoreh_SEOKYEOL_pd=pd.DataFrame(info)
Hankyoreh_SEOKYEOL_pd=Hankyoreh_SEOKYEOL_pd.assign(candidate_id='3')

Hankyoreh_SEOKYEOL_pd

# mysql데이터 연동이후 데이터삽입
import sqlalchemy
engine = sqlalchemy.create_engine(
    "mysql+mysqlconnector://team1:smhrd1@project-db-stu.ddns.net:3307/campus_k_1_1006?charset=utf8")
conn = engine.connect()
Hankyoreh_SEOKYEOL_pd.to_sql(name="news", con=engine, if_exists='append', index=False)

conn.close()

