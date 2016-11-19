# 基本调度系统，用于各种测试用和收集的工具方法
注意事项：
1增加数据库操作步骤：
    1 在model包内新增数据类型，可以为输入参数的数据结构或者结果的数据结构
    2 在myabtis-config.xml内添加别名
    例子：<typeAlias alias="Task" type="model.Task"/>
    3 在mapper文件夹内增加*-mapper.xml
    模板参考test-mapper.xml
    空间命名规则为<mapper namespace="mapperNS.Test">，其中Test为自定义
    4 在utils中Constants中添加，MAPPER_*变量，赋值为*-mapper.xml中的命名空间
    5 在Dao包中建立*Dao类，提供与数据库进行交互的接口

2获取xml配置文件信息，xml配置文件需要放置在resources的config文件夹下
    增加系统变量步骤如下：
    1 在Constants中添加变量，设置未初始化值（null/-1）
    2 在system-property.xml中添加property
    3 修改Constants中initSystemProperties方法，增加初始化
    4 在Constants中添加获取变量方法（含校验）







