package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lu on 2016/10/16.
 */
public class Task  implements Serializable {

    private int task_id;
    private String name;
    private String des;
    private int type;
    private int st;
    private String para;
    private int prior;
    private Date beg_time;
    private Date end_time;
    private double cost;
    private double avg_cost;


    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public String getParam() {
        return para;
    }

    public void setParam(String para) {
        this.para = para;
    }

    public int getPrior() {
        return prior;
    }

    public void setPrior(int prior) {
        this.prior = prior;
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

    public double getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getAvg_cost() {
        return avg_cost;
    }

    public void setAvg_cost(int avg_cost) {
        this.avg_cost = avg_cost;
    }
}
