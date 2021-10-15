import requests as req
from selenium import webdriver as wb
from selenium.webdriver.common.keys import Keys
from bs4 import BeautifulSoup as bs
import pandas as pd
import time

driver=wb.Chrome()
driver.get('https://www.chosun.com/nsearch/?query=%EC%9C%A4%EC%84%9D%EC%97%B4&siteid=&sort=1&date_period=1y&writer=&field=&emd_word=&expt_word=&opt_chk=false&app_check=0&website=www,chosun&category=')

soup = bs(driver.page_source, 'lxml')

body = driver.find_element_by_tag_name('body')
btn_more = driver.find_element_by_id("load-more-stories")


for i in range(5):

    body.send_keys(Keys.PAGE_DOWN)
    time.sleep(2)
    body.send_keys(Keys.PAGE_DOWN)
    time.sleep(2)
    body.send_keys(Keys.PAGE_DOWN)
    time.sleep(2)
    btn_more = driver.find_element_by_id("load-more-stories")
    btn_more.click()
    time.sleep(2)


title=driver.find_element_by_xpath('//*[@id="main"]/div[4]/div[1]/div/div[1]/div[2]/div[1]/div/a/span').text

title

date=driver.find_element_by_xpath('//*[@id="fusion-app"]/div[1]/div[2]/div/section/article/div[2]/span').text

date

content =driver.find_element_by_class_name('#fusion-app > div.article > div:nth-child(2) > div > section > article > section > p:nth-child(6)').text

content











date=driver.find_element_by_xpath('//*[@id="fusion-app"]/div[1]/div[2]/div/section/article/div[2]/span').text

date

content=driver.find_element_by_xpath('//*[@id="fusion-app"]/div[1]/div[2]/div/section/article/section').text

content

btn_more.back()