package test.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射机制测试类
 */
public class ReflectTest {

    public ReflectClass getReflectClass(){
        return new ReflectClass();
    }


    public static void main(String[] args) {
        ReflectTest test=new ReflectTest();
        // 获得对象类型
        Class<? extends Object> classType = ReflectClass.class;
        // 获得对象属性
        Field fields[] = classType.getDeclaredFields();
        for(Field f:fields){
            System.out.println(f.toString());
        }
        try {
            //获取类型里面test方法
            Method method=classType.getMethod("test");
            //新建测试类
            ReflectClass testClass=test.getReflectClass();
            //调用测试类中的test方法
            method.invoke(testClass);
            //获取类型里面rtest_int方法
            Method method1=classType.getMethod("rtest_int",int.class);
            //调用测试类中的rtest_int方法
            int result=(int)method1.invoke(testClass,1);
            System.out.println("result :" + result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }








    private class ReflectClass {
        private String name;
        private int id;
        private double db;

        public void test (){
            System.out.println("this is ReflectClass test function");
        }

        public int rtest_int(int i){
            System.out.println("this is ReflectClass rtest_int function return "+i);
            return i;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getDb() {
            return db;
        }

        public void setDb(double db) {
            this.db = db;
        }
    }



}
