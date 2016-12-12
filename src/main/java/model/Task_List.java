package model;

import utils.CommonUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task_List implements Serializable {

    private int task_id;
    private int st;
    private String t_date;
    private Date beg_time=null;
    private Date end_time=null;

    public Task_List (){super();}

    public Task_List(int task_id,int st){
        this.task_id=task_id;
        this.st=st;
        this.t_date= CommonUtil.date2string8(new Date());
    }



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

    public Date getBeg_time() {
        return beg_time;
    }

    public void setBeg_time(Date beg_time) {
        this.beg_time = beg_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}
