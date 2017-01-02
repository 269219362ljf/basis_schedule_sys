package basisSchedule.resultModel;

import java.io.Serializable;
import java.util.Date;


public class Task  implements Serializable {

    private int task_id;
    private String name=null;
    private String des=null;
    private String taskclassname=null;
    private int type;
    private String para=null;
    private int prior;
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

    public String getTaskclassname() {
        return taskclassname;
    }

    public void setTaskclassname(String taskclassname) {
        this.taskclassname = taskclassname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public int getPrior() {
        return prior;
    }

    public void setPrior(int prior) {
        this.prior = prior;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getAvg_cost() {
        return avg_cost;
    }

    public void setAvg_cost(double avg_cost) {
        this.avg_cost = avg_cost;
    }
}
