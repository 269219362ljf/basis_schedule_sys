package model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/21.
 */
public class RunnableList  implements Serializable {
    private int task_id;
    private int st;
    private String para;
    private String taskclassname;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getTaskclassname() {
        return taskclassname;
    }

    public void setTaskclassname(String taskclassname) {
        this.taskclassname = taskclassname;
    }
}
