from selenium import webdriver as wb
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup as bs
import pandas as pd
import time
import urllib.request as req
import re
# selenum headless 모드 옵션 적용
options = wb.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
driver = wb.Chrome('chromedriver', chrome_options=options)

driver.get('https://www.yna.co.kr/search/index?query=%EC%9D%B4%EC%9E%AC%EB%AA%85&ctype=A&from=20201012&to=20211012&period=1y')

soup = bs(driver.page_source, 'lxml')

div = driver.find_element_by_css_selector('#article_list > div.cts_atclst > ul > li:nth-child(1) > a > span.tt2')

div.click()

# 기사 제목
driver.find_element_by_css_selector('#articleWrap > div.content03 > header > h1').text

# 시간
driver.find_element_by_css_selector('#articleWrap > div.content03 > header > p').text

# 본문 내용
driver.find_element_by_css_selector('#articleWrap > div.content01.scroll-article-zone01 > div > div > article').text

# 기사 url
driver.current_url

driver.back()

#1년치 크롤링
title_list=[]
date_list=[]
body_list=[]
url_list=[]


driver = wb.Chrome('chromedriver', chrome_options=options)
soup = bs(driver.page_source, 'lxml')

for n in range(1,51):
    driver.get('https://www.yna.co.kr/search/index?query=%EC%9D%B4%EC%9E%AC%EB%AA%85&ctype=A&from=20201012&to=20211012&period=1y&page_no=' +str(n))
    driver.implicitly_wait(2)
    for i in range(1,11):
        div = driver.find_element_by_css_selector(f'#article_list > div.cts_atclst > ul > li:nth-child({i}) > a > span.tt2')
        div.click()
        time.sleep(1)
        title_list.append(driver.find_element_by_css_selector('#articleWrap > div.content03 > header > h1').text)
        date_list.append(driver.find_element_by_css_selector('#articleWrap > div.content03 > header > p').text)
        body_list.append(driver.find_element_by_css_selector('#articleWrap > div.content01.scroll-article-zone01 > div > div > article').text)
        url_list.append(driver.current_url)
        driver.back()
        time.sleep(1)


driver.quit()

# 기존 date_list 앞에 있는 송고시간 제거 후 새 리스트에 담기
new_date_list=[]
for i in date_list:
    new_date_list.append(i[4:])

new_date_list

# 데이터프레임 전 딕셔너리 생성
dic = {"title" : title_list, "date" : new_date_list, "contents": body_list, "url" : url_list}

# 데이터 프레임
df= pd.DataFrame(dic)
df=df.assign(candidate_id="1")
df

# mysql 데이터베이스 연동 후 데이터 삽입

import sqlalchemy
engine = sqlalchemy.create_engine(
    "mysql+mysqlconnector://team1:smhrd1@project-db-stu.ddns.net:3307/campus_k_1_1006?charset=utf8")
conn = engine.connect()
df.to_sql(name="news", con=engine, if_exists='append', index=False)

conn.close()
