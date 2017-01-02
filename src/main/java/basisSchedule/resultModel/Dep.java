package basisSchedule.resultModel;

import java.io.Serializable;

/**
 * Created by lu on 2016/10/19.
 */
public class Dep  implements Serializable {
    private int task_id;
    private int parent_id;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
