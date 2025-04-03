package dk.via.taskmanagement.model;

import java.io.Serializable;

public class NotStarted implements TaskState, Serializable {
    @Override
    public void startTask(Task task) {
        task.setState(new InProgress());
    }

    @Override
    public void finnishTask(Task task) {
        task.setState(new Completed());
    }

    @Override
    public String toString() {
        return "NotStarted";
    }
}
