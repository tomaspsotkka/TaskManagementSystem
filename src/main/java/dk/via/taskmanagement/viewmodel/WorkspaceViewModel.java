package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.*;
import dk.via.taskmanagement.model.builders.TaskBuilder;
import dk.via.taskmanagement.utilities.Auth;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public class WorkspaceViewModel implements PropertyChangeListener {
    private final Model model;

    StringProperty workspaceName;
    ArrayList<Task> tasks;

    StringProperty taskName;
    StringProperty taskDescription;
    StringProperty taskPriority;
    StringProperty taskState;
    ObjectProperty<LocalDate> taskDeadline;

    ListProperty<Task> notStartedTasks;
    ListProperty<Task> inProgressTasks;
    ListProperty<Task> completedTasks;

    ObjectProperty<Task> selectedTask;

    Task selectedTaskObject = null;

    ListProperty<Task.Priority> checkedFilterPriorities;
    ListProperty<User> assignedUsersFilter;

    StringProperty nameFilter;

    ListProperty<User> assignedUsers;
    ListProperty<User> availableUsers;

    StringProperty message;


    public WorkspaceViewModel(Model model) {
        this.model = model;
        workspaceName = new SimpleStringProperty();
        taskName = new SimpleStringProperty();
        taskDescription = new SimpleStringProperty();
        taskPriority = new SimpleStringProperty();
        taskState = new SimpleStringProperty();
        taskDeadline = new SimpleObjectProperty<>();
        notStartedTasks = new SimpleListProperty<>(FXCollections.observableArrayList());
        inProgressTasks = new SimpleListProperty<>(FXCollections.observableArrayList());
        completedTasks = new SimpleListProperty<>(FXCollections.observableArrayList());
        checkedFilterPriorities = new SimpleListProperty<>(FXCollections.observableArrayList(Task.Priority.values()));
        selectedTask = new SimpleObjectProperty<>();
        nameFilter = new SimpleStringProperty();
        assignedUsers = new SimpleListProperty<>(FXCollections.observableArrayList());
        availableUsers = new SimpleListProperty<>(FXCollections.observableArrayList());
        assignedUsersFilter = new SimpleListProperty<>(FXCollections.observableArrayList());
        message = new SimpleStringProperty();

        model.addPropertyChangeListener(this);

        selectedTask.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                taskName.setValue(newValue.getName());
                taskDescription.setValue(newValue.getDescription());
                taskPriority.setValue(newValue.getPriority().toString());
                taskState.setValue(newValue.getState().toString());
                taskDeadline.setValue(newValue.getDeadline());

                selectedTaskObject = newValue;

                fetchAvailableUsers(newValue);
            }
        });

        checkedFilterPriorities.addListener((observable, oldValue, newValue) -> {
            filterTasks();
        });

        assignedUsersFilter.addListener((observable, oldValue, newValue) -> {
            filterTasks();
        });

        nameFilter.addListener((observable, oldValue, newValue) -> {
            filterTasks();
        });

    }

    public void init() {
        workspaceName.set(Auth.getInstance().getCurrentUser().getWorkspace().getName());
    }

    public void fetchAvailableUsers(Task task) {
        if (task == null) {
            ArrayList<User> users;
            try {
                users = model.getUsersForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());
            } catch (SQLException | RemoteException e) {
                message.set(e.getMessage());
                return;
            }

            availableUsers.clear();
            assignedUsers.clear();

            availableUsers.addAll(users);
        } else {
            ArrayList<User> usersForWorkspace;
            try {
                usersForWorkspace = model.getUsersForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());
            } catch (SQLException | RemoteException e) {
                message.set(e.getMessage());
                return;
            }

            availableUsers.clear();
            assignedUsers.clear();

            ArrayList<User> usersForTask = task.getUsers();

            ArrayList<User> users = new ArrayList<>(usersForWorkspace);
            users.removeIf(user -> usersForTask.stream().anyMatch(userForTask -> userForTask.getId().equals(user.getId())));


            availableUsers.addAll(users);
            assignedUsers.addAll(usersForTask);
        }
    }

    public void getTasksForWorkspace() {
        try {
            tasks = model.getTasksForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());
        } catch (SQLException | RemoteException e) {
            message.set(e.getMessage());
            return;
        }

        notStartedTasks.clear();
        inProgressTasks.clear();
        completedTasks.clear();

        tasks.stream()
                .sorted(Comparator.comparing((Task task) -> task.getPriority().ordinal()).reversed())
                .forEach(task -> {
                    switch (task.getState().toString()) {
                        case "NotStarted":
                            notStartedTasks.add(task);
                            break;
                        case "InProgress":
                            inProgressTasks.add(task);
                            break;
                        case "Completed":
                            completedTasks.add(task);
                            break;
                    }
                });
    }

    public void bindWorkspaceName(StringProperty property) {
        property.bindBidirectional(workspaceName);
    }

    public void bindTaskName(StringProperty property) {
        property.bindBidirectional(taskName);
    }

    public void bindTaskDescription(StringProperty property) {
        property.bindBidirectional(taskDescription);
    }

    public void bindTaskPriority(StringProperty property) {
        property.bindBidirectional(taskPriority);
    }

    public void bindTaskState(StringProperty property) {
        property.bindBidirectional(taskState);
    }

    public void bindTaskDeadline(ObjectProperty<LocalDate> property) {
        property.bindBidirectional(taskDeadline);
    }

    public void bindNotStartedTasks(ObjectProperty<ObservableList<Task>> property) {
        property.bindBidirectional(notStartedTasks);
    }

    public void bindInProgressTasks(ObjectProperty<ObservableList<Task>> property) {
        property.bindBidirectional(inProgressTasks);
    }

    public void bindCompletedTasks(ObjectProperty<ObservableList<Task>> property) {
        property.bindBidirectional(completedTasks);
    }

    public void bindSelectedTask(ObjectProperty<Task> property) {
        property.bindBidirectional(selectedTask);
    }

    public void bindCheckedFilterPriorities(ListProperty<Task.Priority> property) {
        property.bindBidirectional(checkedFilterPriorities);
    }

    public void bindNameFilter(StringProperty property) {
        property.bindBidirectional(nameFilter);
    }

    public void bindAssignedUsersFilter(ListProperty<User> property) {
        property.bindBidirectional(assignedUsersFilter);
    }

    public void bindMessage(StringProperty property) {
        property.bind(message);
    }

    public void createTask() {
        Task task = getTask();

        try {
            model.createTask(task);
            message.setValue("");
        } catch (SQLException | RemoteException | ValidationException e) {
            message.setValue(e.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("updateTasks")) {
            getTasksForWorkspace();
        }
    }

    public void updateTask() {
        try {
            model.updateTask(getTask());
            message.setValue("");
        } catch (SQLException | RemoteException | ValidationException e) {
            message.setValue(e.getMessage());
        }
    }

    private Task getTask() {
        TaskBuilder taskBuilder = new TaskBuilder();

        TaskState state = switch (taskState.getValue()) {
            case "NotStarted" -> new NotStarted();
            case "InProgress" -> new InProgress();
            case "Completed" -> new Completed();
            default -> null;
        };

        Workspace workspace = Auth.getInstance().getCurrentUser().getWorkspace();

        taskBuilder
                .setName(taskName.getValue())
                .setDescription(taskDescription.getValue())
                .setPriority(Task.Priority.valueOf(taskPriority.getValue()))
                .setState(state)
                .setDeadline(taskDeadline.getValue())
                .setWorkspace(workspace);

        if (selectedTaskObject != null) {
            taskBuilder.setId(selectedTaskObject.getId());
        }

        ArrayList<User> users = new ArrayList<>(assignedUsers.getValue());

        taskBuilder.setUsers(users);

        return taskBuilder.build();
    }

    public void deleteTask() {
        try {
            model.deleteTask(getTask());
        } catch (SQLException | RemoteException e) {
            message.setValue(e.getMessage());
        }
    }

    public void startTask() {
        try {
            model.startTask(getTask());
        } catch (SQLException | RemoteException e) {
            message.setValue(e.getMessage());
        }
    }

    public void completeTask() {
        try {
            model.completeTask(getTask());
        } catch (SQLException | RemoteException e) {
            message.setValue(e.getMessage());
        }
    }

    public synchronized void filterTasks() {
        if (tasks == null) return;

        Predicate<Task> isAssignedToFilteredUser = task -> assignedUsersFilter
                .getValue()
                .stream()
                .anyMatch(user -> task
                        .getUsers()
                        .stream()
                        .anyMatch(taskUser -> taskUser.getId().equals(user.getId())));
        Predicate<Task> isNotAssigned = task -> task
                .getUsers()
                .isEmpty() &&
                assignedUsersFilter
                        .getValue()
                        .stream()
                        .anyMatch(user -> user.getUserName().equals("NotAssigned"));

        notStartedTasks.clear();
        inProgressTasks.clear();
        completedTasks.clear();

        System.out.println(tasks.size());
        tasks.stream()
                .sorted(Comparator.comparing((Task task) -> task.getPriority().ordinal()).reversed())
                .filter(task -> checkedFilterPriorities.getValue().contains(task.getPriority()))
                .filter(isAssignedToFilteredUser.or(isNotAssigned))
                .forEach(task -> {
                    switch (task.getState().toString()) {


                        case "NotStarted":
                            notStartedTasks.add(task);
                            break;
                        case "InProgress":
                            inProgressTasks.add(task);
                            break;
                        case "Completed":
                            completedTasks.add(task);
                            break;
                    }
                });

        if (nameFilter.getValue() != null && !nameFilter.getValue().isEmpty()) {
            notStartedTasks.removeIf(task -> !task.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase()));
            inProgressTasks.removeIf(task -> !task.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase()));
            completedTasks.removeIf(task -> !task.getName().toLowerCase().contains(nameFilter.getValue().toLowerCase()));
        }
    }

    public void bindAssignedUsers(ObjectProperty<ObservableList<User>> targetItems) {
        assignedUsers.bindBidirectional(targetItems);
    }

    public void bindAvailableUsers(ObjectProperty<ObservableList<User>> targetItems) {
        availableUsers.bindBidirectional(targetItems);
    }

    public ArrayList<User> getAvailableUsers() {
        ArrayList<User> ret;
        try {
            ret = model.getUsersForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());
        } catch (SQLException | RemoteException e) {
            message.set(e.getMessage());
            return null;
        }

        User nonExistingUser = new User("NotAssigned", "", "", null);
        ret.add(nonExistingUser);

        return ret;
    }

    public void logout() {
        Auth.getInstance().setCurrentUser(null);
    }
}
