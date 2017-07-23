import psycopg2

class postgresqlConnect:

    def __init__(self):
        self.database="schedule_sys"
        self.user="lu"
        self.password="123456"
        self.host="localhost"
        self.port="5432"
        self.conn=psycopg2.connect(database=self.database,\
                        user=self.user, password=self.password,\
                        host=self.host, port=self.port)
        self.cur=self.conn.cursor()
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

    def executeSql(self,sql):
        self.cur.execute(sql)
        rows=self.cur.fetchall()
        self.conn.commit()
        return rows

#with postgresqlConnect() as conn:
    #rows=conn.executeSql("select * from t_stock_trainning_info;")
    #print(rows)
    
        
        
