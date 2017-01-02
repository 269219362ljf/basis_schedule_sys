package basisSchedule.resultModel;

import java.io.Serializable;

/**
 * Created by lu on 2016/10/19.
 */
public class Task_Session  implements Serializable {
    private String session_id;
    private int tasks;
    private double total_cost;
    private String session_date;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getTasks() {
        return tasks;
    }

    public void setTasks(int tasks) {
        this.tasks = tasks;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
    }

    public String getSession_date() {
        return session_date;
    }

    public void setSession_date(String session_date) {
        this.session_date = session_date;
    }
}
