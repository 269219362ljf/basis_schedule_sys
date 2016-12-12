/*
股票代码信息表(其余信息后续追加)
 */
drop table IF EXISTS T_STOCK_INFO;
CREATE TABLE T_STOCK_INFO(
  stock_id varchar(10) PRIMARY KEY ,
  stock_name VARCHAR(20)
);

/*
股票价格表(其余信息后续追加)
 */
drop table IF EXISTS T_STOCK_TRAINNING_INFO;
CREATE TABLE T_STOCK_TRAINNING_INFO(
  stock_id varchar(10),
  s_date varchar(8),
  open_price NUMERIC(9,3),
  close_price NUMERIC(9,3),
  max_price NUMERIC(9,3),
  min_price NUMERIC(9,3),
  PRIMARY KEY (stock_id,s_date)
);



