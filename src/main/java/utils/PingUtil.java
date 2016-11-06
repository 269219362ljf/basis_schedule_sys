package utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class PingUtil {

    /**
     * java实现ping,使用控制台执行ping命令，查看解释返回数据以检测连接
     * @param destIp
     * @param maxCount
     * @return
     */
    public Integer doPingCmd(String destIp, int maxCount) {
        LineNumberReader input = null;
        try {
            String osName = System.getProperties().getProperty("os.name");
            String pingCmd = null;
            if (osName.startsWith("Windows")) {
                // -n是发送包数
                pingCmd = "cmd /c ping -n {0} {1}";
                pingCmd = MessageFormat.format(pingCmd, maxCount, destIp);
            } else if (osName.startsWith("Linux")) {
                // -c是发送包数 -w是等待时间(以秒为单位)
                pingCmd = "ping -c {0} -w 1 {1} ";
                pingCmd = MessageFormat.format(pingCmd, maxCount, destIp);
            } else {
                System.out.println("not support OS");
                return null;
            }
            Process process = Runtime.getRuntime().exec(pingCmd);
            InputStreamReader ir = new InputStreamReader(process
                    .getInputStream());
            input = new LineNumberReader(ir);
            String line;
            List<String> reponse = new ArrayList<String>();

            while ((line = input.readLine()) != null) {
                if (!"".equals(line)) {
                    reponse.add(line);
                    // System.out.println("====:" + line);
                }
            }
            if (osName.startsWith("Windows")) {
                return parseWindowsMsg(reponse, maxCount);
            } else if (osName.startsWith("Linux")) {
                return parseLinuxMsg(reponse, maxCount);
            }

        } catch (IOException e) {
            System.out.println("IOException   " + e.getMessage());

        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException ex) {
                    System.out.println("close error:" + ex.getMessage());

                }
            }
        }
        return null;
    }

    //windows下对返回数据的解释并统计成功返回次数
    private  int parseWindowsMsg(List<String> reponse, int total) {
        int countTrue = 0;
        for (String str : reponse) {
            if (str.startsWith("来自") || str.startsWith("Reply from")) {
                countTrue++;
            }
        }
        return countTrue;
    }

    //linux下对返回数据的解释并统计成功返回次数
    private  int parseLinuxMsg(List<String> reponse, int total) {
        int countTrue = 0;
        for (String str : reponse) {
            if (str.contains("bytes from") && str.contains("icmp_seq=")) {
                countTrue++;
            }
        }
        return countTrue;
    }

}
