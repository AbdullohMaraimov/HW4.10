package hw1;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class ToDo {
    private String task;
    private LocalTime time;

    public ToDo(String task, LocalTime time) {
        this.task = task;
        this.time = time;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
