package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lu on 2016/10/19.
 */
public class Task_Log  implements Serializable {


    private Date log_time;
    private int task_id;
    private int LOG_LEVEL;
    private int LOG_TYPE;
    private String LOG_MSG;

    public String getLOG_MSG() {
        return LOG_MSG;
    }

    public void setLOG_MSG(String LOG_MSG) {
        this.LOG_MSG = LOG_MSG;
    }

    public Date getLog_time() {
        return log_time;
    }

    public void setLog_time(Date log_time) {
        this.log_time = log_time;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getLOG_LEVEL() {
        return LOG_LEVEL;
    }

    public void setLOG_LEVEL(int LOG_LEVEL) {
        this.LOG_LEVEL = LOG_LEVEL;
    }

    public int getLOG_TYPE() {
        return LOG_TYPE;
    }

    public void setLOG_TYPE(int LOG_TYPE) {
        this.LOG_TYPE = LOG_TYPE;
    }



}
