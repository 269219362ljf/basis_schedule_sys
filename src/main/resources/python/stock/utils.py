import pandas as pd
from sqlalchemy import create_engine

class postgresqlConnect:

    def __init__(self,dbtype='postgresql',database="schedule_sys",user="lu",password="123456"\
                 ,host="localhost",port="5432"):
        self.database=database
        self.user=user
        self.password=password
        self.host=host
        self.port=port
        self.conn=create_engine(dbtype+'://'+user+':'+password+'@'+ host+':'+ port+'/'+ database,echo=True)
        return

    def __enter__(self):
        return self

    def __exit__(self,type,value,trace):
        if trace is not None:
            print(trace)
        self.close()

    def close(self):
        self.cur.close()
        self.conn.close()

    def querySql(self,sql):    
        return pd.read_sql_query(sql,con = self.conn)

def example():
    test=postgresqlConnect()
    data=test.querySql('select * from t_dep ')
    
