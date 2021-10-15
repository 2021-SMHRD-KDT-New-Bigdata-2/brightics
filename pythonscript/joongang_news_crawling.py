from selenium import webdriver as wb
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup as bs
import pandas as pd
import time

driver = wb.Chrome()
driver.get('https://www.joongang.co.kr/search/news?keyword=%EC%9D%B4%EC%9E%AC%EB%AA%85&startDate=2020-09-12&endDate=2021-10-13&sfield=all')

soup = bs(driver.page_source, 'lxml')

div = driver.find_element_by_css_selector('#container > section > div > section > ul > li:nth-child(1)> div.card_body > h2 > a')

div.click()

driver.find_element_by_css_selector('#container > section > article > header > h1').text

driver.find_element_by_css_selector('#container > section > article > header > div.datetime > div > p:nth-child(1)').text

driver.find_element_by_css_selector('#article_body').text

driver.current_url

driver.back()

btn = driver.find_element_by_css_selector('#container > section > div > section > div > a')

try:
    while True:
        btn.click()
        time.sleep(0.5)
except:
    print("더보기 종료")

# 새창 띄우기
target = driver.find_element_by_css_selector('#container > section > div > section > ul > li:nth-child(1)> div.card_body > h2 > a')
target.send_keys(Keys.CONTROL + "\n")

# 새창으로 변경
driver.switch_to.window(driver.window_handles[1])

# 기사제목
driver.find_element_by_css_selector('#container > section > article > header > h1').text

# 시간
driver.find_element_by_css_selector('#container > section > article > header > div.datetime > div > p:nth-child(1)').text

# 본문내용
driver.find_element_by_css_selector('#article_body').text

# 현재 띄워진 창 닫기
driver.close()


# 처음 창으로 돌아오기
driver.switch_to.window(driver.window_handles[0])

title_list=[]
date_list=[]
url_list=[]
body_list=[]

driver = wb.Chrome()
driver.get('https://www.joongang.co.kr/search/news?keyword=%EC%9D%B4%EC%9E%AC%EB%AA%85&startDate=2020-09-12&endDate=2021-10-13&sfield=all')
btn = driver.find_element_by_css_selector('#container > section > div > section > div > a')
try:
    while True:
        btn.click()
        time.sleep(0.5)
except:
    print("더보기 종료")

i=1

while True:
    div = driver.find_element_by_css_selector(f'#container > section > div > section > ul > li:nth-child({i})> div.card_body > h2 > a')
    div.send_keys(Keys.CONTROL + "\n")
    driver.switch_to.window(driver.window_handles[1])
    title_list.append(driver.find_element_by_css_selector('#container > section > article > header > h1').text)
    date_list.append(driver.find_element_by_css_selector('#container > section > article > header > div.datetime > div > p:nth-child(1)').text)
    url_list.append(driver.current_url)
    body_list.append(driver.find_element_by_css_selector('#article_body').text)
    time.sleep(0.5)
    driver.close()
    time.sleep(0.5)
    driver.switch_to.window(driver.window_handles[0])
    i+=1

driver.quit()

new_date_list=[]
for i in date_list:
    new_date_list.append(i[3:])

new_date_list

dic = {"title" : title_list, "date" : new_date_list, "contents" : body_list, "url" : url_list}

df = pd.DataFrame(dic)
df = df.assign(candidate_id="1")

import sqlalchemy
engine = sqlalchemy.create_engine(
    "mysql+mysqlconnector://team1:smhrd1@project-db-stu.ddns.net:3307/campus_k_1_1006?")
conn = engine.connect()
df.to_sql(name="news", con=engine, if_exists='append', index=False)

conn.close()




