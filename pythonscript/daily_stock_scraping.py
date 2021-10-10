from pykrx import stock
import time
import sqlalchemy
from datetime import datetime, timedelta

engine= sqlalchemy.create_engine("mysql+mysqlconnector://team1:smhrd1@project-db-stu.ddns.net:3307/campus_k_1_1006?charset=utf8")
conn=engine.connect()

d = conn.execute("select date from daily_stock order by date desc limit 1")
for i in d:
    ed=i[0]
tickers=[]
codes=conn.execute("select stock_code from stock")
for code in codes:
    tickers+=code

start_date = (ed+timedelta(days=1)).strftime("%Y%m%d")
end_date = datetime.today().strftime("%Y%m%d")

for ticker in tickers:
    try:
        df = stock.get_market_ohlcv_by_date(start_date, end_date, ticker, "d").reset_index()
        df.columns=['date','open','high','low','close','trading_volume']
        df = df.assign(stock_code=ticker)
        print(df)
        df.to_sql(name="daily_stock",con=engine,if_exists='append',index=False)
        time.sleep(10)
    except:
        continue



