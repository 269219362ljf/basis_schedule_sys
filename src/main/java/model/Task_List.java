package model;

import java.io.Serializable;

/**
 * Created by lu on 2016/10/19.
 */
public class Task_List implements Serializable {

    private int task_id;
    private int st;
    private String t_date;

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

    public String getT_date() {
        return t_date;
    }

    public void setT_date(String t_date) {
        this.t_date = t_date;
    }
}
