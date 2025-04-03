package dk.via.taskmanagement.model;

import java.io.Serializable;

public class Completed implements TaskState, Serializable {
    @Override
    public void startTask(Task task) {
        task.setState(new InProgress());
    }

    @Override
    public void finnishTask(Task task) {
        task.setState(this);
    }

    @Override
    public String toString() {
        return "Completed";
    }
}
