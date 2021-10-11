import os
import sys
import urllib.request
import pandas as pd
import json
import re
import datetime
import sqlalchemy

engine = sqlalchemy.create_engine(
    "mysql+mysqlconnector://team1:smhrd1@project-db-stu.ddns.net:3307/campus_k_1_1006?charset=utf8")
prevtime=datetime.datetime.now()-datetime.timedelta(weeks=1)
conn = engine.connect()
result =conn.execute("select date from news limit 1")
for i in result:
    prevtime=i[0]

client_id = "D0SJvjlJN9AcFgYffFSR"
client_secret = "Eohhvy5UsO"
candidate = [('이재명', 1), ('홍준표', 2), ('윤석열', 3), ('이낙연', 4)]
idx = 0
display = 100
start = 1
end = 1000
sort = 'date'
remove_tag = re.compile('<.*?>')
for c in candidate:
    for start_index in range(start, end, display):
        web_df = pd.DataFrame(columns=("title", "contents", "url", "date", "candidate_id"))
        encText = urllib.parse.quote(c[0])
        url = "https://openapi.naver.com/v1/search/news?query=" \
              + encText + "&display=" \
              + str(display) \
              + "&start=" + str(start_index) + "&sort=" + sort
        request = urllib.request.Request(url)
        request.add_header("X-Naver-Client-Id", client_id)
        request.add_header("X-Naver-Client-Secret", client_secret)
        response = urllib.request.urlopen(request)
        rescode = response.getcode()
        if (rescode == 200):
            response_body = response.read()
            response_dict = json.loads(response_body.decode('utf-8'))
            items = response_dict['items']
            for item_index in range(len(items)):
                title = re.sub(remove_tag, '', items[item_index]['title'])
                url = items[item_index]['link']
                contents = re.sub(remove_tag, '', items[item_index]['description'])
                date = datetime.datetime.strptime(items[item_index]["pubDate"], '%a, %d %b %Y %H:%M:%S +0900')
                if date < prevtime:
                    continue
                candidate_id = c[1]
                web_df.loc[idx] = [title, url, contents, date, candidate_id]
                idx += 1
            web_df.drop_duplicates(['title'])
            web_df.to_sql(name="news", con=engine, if_exists='append', index=False)
        else:
            print("Error Code:" + rescode)
conn.close()


