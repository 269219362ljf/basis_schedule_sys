package utils;

import jobs.JobInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;


public class CommonUtil {

   /**
     * 通过PrintWriter将响应数据写入response，ajax可以接受到这个数据
     *
     * @param response
     * @param data
     */
    public static void renderData(HttpServletResponse response, Object data) {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.print(data);
        } catch (IOException ex) {
            LogUtil.ErrorLogAdd(Constants.LOG_ERROR,"renderData ","返回数据",ex.getCause().toString(),true);
        } finally {
            if (null != printWriter) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }


    public static JSONArray list2JSONResult(List<?> list) {
        JSONArray result = new JSONArray();
        if (list.size() == 0) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonObject=object2JSONObject(list.get(i));
            result.put(jsonObject);
        }
        return result;
    }

    //由于原有的初始化函数会导致未初始化属性掉失，所以截取JSONObject内部代码来补充
    //当未初始化属性时应如何处理
    private static JSONObject object2JSONObject(Object bean) {
        JSONObject jresult = new JSONObject();
        Class klass = bean.getClass();
        boolean includeSuperClass = klass.getClassLoader() != null;
        Method[] methods = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();

        for (int i = 0; i < methods.length; ++i) {
            try {
                Method ignore = methods[i];
                if (Modifier.isPublic(ignore.getModifiers())) {
                    String name = ignore.getName();
                    String key = "";
                    if (name.startsWith("get")) {
                        if (!"getClass".equals(name) && !"getDeclaringClass".equals(name)) {
                            key = name.substring(3);
                        } else {
                            key = "";
                        }
                    } else if (name.startsWith("is")) {
                        key = name.substring(2);
                    }

                    if (key.length() > 0 && Character.isUpperCase(key.charAt(0)) && ignore.getParameterTypes().length == 0) {
                        if (key.length() == 1) {
                            key = key.toLowerCase();
                        } else if (!Character.isUpperCase(key.charAt(1))) {
                            key = key.substring(0, 1).toLowerCase() + key.substring(1);
                        }

                        Object result = ignore.invoke(bean, (Object[]) null);
                        //在此增加处理代码，未初始化的属性设为""
                        if (result != null) {
                            jresult.put(key, JSONObject.wrap(result));
                        } else {
                            jresult.put(key, "");
                        }
                    }
                }
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        }
        return jresult;
    }




}
