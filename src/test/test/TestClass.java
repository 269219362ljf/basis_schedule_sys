package test;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lu on 2016/11/20.
 */
public class TestClass {

    public static void main(String[] args) {
        try {
            System.out.println(PrintClass.class.toString());
            Class testClass=Class.forName("test.PrintClass");

            //使用此方法必须有0参数的构造函数
            Object obj=testClass.newInstance();
            TestInterface testobj=(TestInterface)testClass.newInstance();
            testobj.test();
            // 获得对象属性
            Field fields[] = testClass.getDeclaredFields();

            Method methods[]=testClass.getMethods();
            for(Field f:fields){
                System.out.println(f.toString());
            }

            for(Method m:methods){
                System.out.println(m.toString());
            }
            PrintClass a=new PrintClass("test");
            TestInterface testInterface=a;
            testInterface.test();





        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
