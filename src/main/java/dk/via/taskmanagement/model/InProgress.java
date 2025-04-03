package dk.via.taskmanagement.model;

import java.io.Serializable;

public class InProgress implements TaskState, Serializable {
    @Override
    public void startTask(Task task) {
        task.setState(this);
    }

    @Override
    public void finnishTask(Task task) {
        task.setState(new Completed());
    }

    @Override
    public String toString() {
        return "InProgress";
    }
}
