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
            //完整文件路径
            String classFilePath = path + name + ".class";
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
        //指定为根目录下的uploadclass，即WEB-INF/classes/uploadclass
        loader.setPath(this.getClass().getResource("/").getPath()+"uploadclass/");
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

