import utils 
import pandas as pd

test=utils.postgresqlConnect()

print(test.user)

data=test.querySql('select * from t_dep ')
