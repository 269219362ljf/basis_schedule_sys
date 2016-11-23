package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task_List implements Serializable {

    private int task_id;
    private int st;
    private String t_date;

    public Task_List (){super();}

    public Task_List(int task_id,int st){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        this.task_id=task_id;
        this.st=st;
        this.t_date=sdf.format(new Date());
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


}
