package utils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * Created by lu on 2017/6/13.
 */
public class ClassUtil {

    private class JobsLoader extends ClassLoader {

        private String path = null;

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Class clazz = null;
            //加载class文件并转成2进制数组
            byte[] classData = getClassData(name);
            //将class的字节码数组转换成Class类的实例
            clazz = defineClass(name, classData, 0, classData.length);
            return clazz;
        }

        //将class文件转化为2进制流
        private byte[] getClassData(String name) {
            String classFilePath="";
            //完整文件路径
            //不以class结尾加上.class
            if(name.length()-name.lastIndexOf(".class")!=6 || name.lastIndexOf(".class")<0)name=name + ".class";
            //加上.class后，如果.位置一样，则直接加上路径，否则取类名
            if(name.indexOf(".")==name.lastIndexOf(".")) {
                classFilePath = path + name;
            }
            else{
                classFilePath=path+name.substring(name.substring(0,name.lastIndexOf(".")).lastIndexOf(".")+1);
            }
            //获取文件对象并转化为2进制数组
            File classFile = new File(classFilePath);
            try {
                InputStream in = new FileInputStream(classFile);
                byte[] result = new byte[(int) classFile.length()];
                in.read(result);
                in.close();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }



    //单例线程
    private static ClassUtil classUtil=new ClassUtil();

    public static ClassUtil getInstance(){
        return classUtil;
    }

    private  JobsLoader loader;

    private ClassUtil(){
        loader=new JobsLoader();
        //指定为根目录下的uploadclass，即WEB-INF/upload
        loader.setPath(Constants.UPLOADDIR);
    }

    public boolean hasClass(String classname){
        if(getClass(classname)!=null){
            return true;
        }else{
            return false;
        }
    }

    public Class getClass(String classname){
        Class result=null;
        try {
            result=Class.forName(classname);
            return result;
        } catch (ClassNotFoundException e) {
            try {

                result=loader.loadClass(classname);
                return result;
            } catch (ClassNotFoundException e1) {
                return result;
            }
        }
    }

    //调用方法
    public int invokemethod(Class classname,String methodname,Object... args){
        try {
            Object target=classname.newInstance();
            Method method=classname.getDeclaredMethod(methodname, JSONObject.class);
            return (int)method.invoke(target,args);
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAIL;
        }
    }







    }

