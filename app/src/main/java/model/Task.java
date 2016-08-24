package model;

/**
 * Created by Administrator on 2016/8/20 0020.
 */
public class Task {
    private String task_id;

    private String taskName;

    private String taskScore;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskScore() {
        return taskScore;
    }

    public void setTaskScore(String taskScore) {
        this.taskScore = taskScore;
    }
}
