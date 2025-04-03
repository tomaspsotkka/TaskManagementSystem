package dk.via.taskmanagement.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Task implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private TaskState state;
    private LocalDate deadline;

    private Priority priority;
    private ArrayList<User> users;

    private Workspace workspace;

    public Task(String taskName, Workspace workspace) {
        id = null;
        state = new NotStarted();
        users = new ArrayList<>();

        priority = Priority.LOW;
        name = taskName;
        this.workspace = workspace;
    }

    public Task() {
        id = null;
        state = new NotStarted();
        users = new ArrayList<>();

        priority = Priority.LOW;
        workspace = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void startTask() {
        state.startTask(this);
    }

    public void completeTask() {
        state.finnishTask(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public String toString() {
        return "[" + priority + "] " + name;
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }
}
