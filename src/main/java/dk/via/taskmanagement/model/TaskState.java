package dk.via.taskmanagement.model;

public interface TaskState {
    void startTask(Task task);

    void finnishTask(Task task);
}
