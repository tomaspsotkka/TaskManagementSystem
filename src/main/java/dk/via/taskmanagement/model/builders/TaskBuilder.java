package dk.via.taskmanagement.model.builders;

import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.TaskState;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.time.LocalDate;
import java.util.ArrayList;

public class TaskBuilder {
    private Integer id;
    private String name;
    private String description;
    private Task.Priority priority;
    private TaskState state;
    private LocalDate deadline;
    private Workspace workspace;
    private ArrayList<User> users = new ArrayList<>();

    public TaskBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public TaskBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public TaskBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder setPriority(Task.Priority priority) {
        this.priority = priority;
        return this;
    }

    public TaskBuilder setState(TaskState state) {
        this.state = state;
        return this;
    }

    public TaskBuilder setDeadline(LocalDate deadline) {
        this.deadline = deadline;
        return this;
    }

    public TaskBuilder setWorkspace(Workspace workspace) {
        this.workspace = workspace;
        return this;
    }

    public Task build() {
        Task task = new Task();
        task.setId(this.id);
        task.setName(this.name);
        task.setDescription(this.description);
        task.setPriority(this.priority);
        task.setState(this.state);
        task.setDeadline(this.deadline);
        task.setWorkspace(this.workspace);
        task.setUsers(this.users);

        return task;
    }

    public TaskBuilder setUsers(ArrayList<User> assignedUsers) {
        users = assignedUsers;
        return this;
    }
}
