package basisSchedule.threadService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by lu on 2017/6/11.
 */
public class JobsLoader extends ClassLoader {

    private String path=null;

    //单例线程
    private static JobsLoader jobsLoader=new JobsLoader();

    public static JobsLoader getInstance(){
        return jobsLoader;
    }

    private JobsLoader(){
        //指定为根目录下的uploadclass，即WEB-INF/classes/uploadclass
        path=this.getClass().getResource("/").getPath()+"uploadclass/";
    }

    @Override
    protected Class<?> findClass(String name)throws ClassNotFoundException {
        Class clazz=null;
        //加载class文件并转成2进制数组
        byte[] classData=getClassData(name);
        //将class的字节码数组转换成Class类的实例
        clazz = defineClass(name, classData, 0, classData.length);
        return clazz;
    }

    private byte[] getClassData(String name){
        //完整文件路径
        String classFilePath=path+name+".class";
        File classFile=new File(classFilePath);
        try {
            InputStream in=new FileInputStream(classFile);
            byte[] result=new byte[(int)classFile.length()];
            in.read(result);
            in.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }







}
