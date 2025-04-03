package dk.via.taskmanagement.view;

import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.utilities.Auth;
import dk.via.taskmanagement.viewmodel.WorkspaceViewModel;
import javafx.beans.property.*;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.SegmentedButton;

import java.util.ArrayList;

public class WorkspaceView {
    @FXML Label workspaceName;
    @FXML Button manageWorkspaceButton;
    @FXML Region manageWorkspaceRegion;
    @FXML TextField name;
    @FXML TextArea description;
    @FXML SegmentedButton priority;
    @FXML SegmentedButton state;
    @FXML DatePicker deadline;
    StringProperty taskState;
    StringProperty taskPriority;
    @FXML ListView<Task> notStartedTasks;
    @FXML ListView<Task> inProgressTasks;
    @FXML ListView<Task> completedTasks;
    ObjectProperty<Task> selectedTask;
    BooleanProperty isNewTask;
    @FXML Button newButton;
    @FXML Button deleteButton;
    @FXML Button startButton;
    @FXML Button completeButton;
    @FXML TextField nameFilter;
    @FXML CheckComboBox<Task.Priority> priorityFilter;
    ListProperty<Task.Priority> checkedFilterPriorities;
    @FXML ListSelectionView<User> assignedUsers;
    @FXML CheckComboBox<User> assignedUsersFilter;
    ListProperty<User> assignedUsersFilterList;
    @FXML Label message;
    private ViewHandler viewHandler;
    private WorkspaceViewModel viewModel;

    public void init(ViewHandler viewHandler, WorkspaceViewModel workspaceViewModel, Region root) {
        this.viewHandler = viewHandler;
        viewModel = workspaceViewModel;
        taskState = new SimpleStringProperty();
        taskPriority = new SimpleStringProperty();
        selectedTask = new SimpleObjectProperty<>();
        isNewTask = new SimpleBooleanProperty();
        checkedFilterPriorities = new SimpleListProperty<>();
        assignedUsersFilterList = new SimpleListProperty<>();

        viewModel.bindWorkspaceName(workspaceName.textProperty());
        viewModel.bindTaskName(name.textProperty());
        viewModel.bindTaskDescription(description.textProperty());
        viewModel.bindTaskPriority(taskPriority);
        viewModel.bindTaskState(taskState);
        viewModel.bindTaskDeadline(deadline.valueProperty());
        viewModel.bindNotStartedTasks(notStartedTasks.itemsProperty());
        viewModel.bindInProgressTasks(inProgressTasks.itemsProperty());
        viewModel.bindCompletedTasks(completedTasks.itemsProperty());
        viewModel.bindSelectedTask(selectedTask);
        viewModel.bindCheckedFilterPriorities(checkedFilterPriorities);
        viewModel.bindNameFilter(nameFilter.textProperty());
        viewModel.bindAssignedUsers(assignedUsers.targetItemsProperty());
        viewModel.bindAvailableUsers(assignedUsers.sourceItemsProperty());
        viewModel.bindAssignedUsersFilter(assignedUsersFilterList);
        viewModel.bindMessage(message.textProperty());

        boolean isAdmin = Auth.getInstance().getCurrentUser().getRole().equals("admin");

        manageWorkspaceButton.setVisible(isAdmin);
        manageWorkspaceRegion.setVisible(isAdmin);

        initPriority();
        initState();
        initSelectedTask();
        initIsNewTask();
        initPriorityFilter();
        initAssignedUsersFilter();

        workspaceViewModel.init();
        workspaceViewModel.getTasksForWorkspace();
    }

    private void initAssignedUsersFilter() {
        assignedUsersFilter.getItems().addAll(viewModel.getAvailableUsers());

        assignedUsersFilter.getCheckModel().getCheckedItems().addListener((ListChangeListener<User>) c -> {
            assignedUsersFilterList.clear();

            ArrayList<User> checked = new ArrayList<>();
            for (User user : assignedUsersFilter.getItems()) {
                if (assignedUsersFilter.getCheckModel().isChecked(user)) {
                    checked.add(user);
                }
            }

            assignedUsersFilterList.addAll(checked);
        });

        assignedUsersFilter.getCheckModel().checkAll();
    }

    private void initPriorityFilter() {
        priorityFilter.getItems().addAll(Task.Priority.values());

        priorityFilter.getCheckModel().getCheckedItems().addListener((ListChangeListener<Task.Priority>) c -> {
            checkedFilterPriorities.clear();
            checkedFilterPriorities.addAll(priorityFilter.getCheckModel().getCheckedItems());
        });

        priorityFilter.getCheckModel().checkAll();
    }

    private void initIsNewTask() {
        isNewTask.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                name.clear();
                description.clear();
                deadline.setValue(null);
                taskPriority.set(Task.Priority.LOW.toString());
                taskState.set("NotStarted");
                newButton.setVisible(false);
                deleteButton.setVisible(false);
                startButton.setVisible(false);
                completeButton.setVisible(false);

                notStartedTasks.getSelectionModel().clearSelection();
                inProgressTasks.getSelectionModel().clearSelection();
                completedTasks.getSelectionModel().clearSelection();
                viewModel.fetchAvailableUsers(null);
            } else {
                newButton.setVisible(true);
                deleteButton.setVisible(true);
                startButton.setVisible(true);
                completeButton.setVisible(true);
                viewModel.fetchAvailableUsers(selectedTask.get());
            }

        });

        isNewTask.set(true);
    }

    private void initSelectedTask() {
        notStartedTasks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTask.set(newValue);
                inProgressTasks.getSelectionModel().clearSelection();
                completedTasks.getSelectionModel().clearSelection();
                isNewTask.set(false);
            }
        });

        inProgressTasks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTask.set(newValue);
                notStartedTasks.getSelectionModel().clearSelection();
                completedTasks.getSelectionModel().clearSelection();
                isNewTask.set(false);
            }
        });

        completedTasks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTask.set(newValue);
                notStartedTasks.getSelectionModel().clearSelection();
                inProgressTasks.getSelectionModel().clearSelection();
                isNewTask.set(false);
            }
        });
    }

    private void initState() {
        ToggleGroup taskStateToggleGroup = new ToggleGroup();

        ToggleButton notStartedButton = new ToggleButton("Not Started");
        notStartedButton.setToggleGroup(taskStateToggleGroup);
        notStartedButton.setUserData("NotStarted");
        notStartedButton.setDisable(true);

        ToggleButton inProgressButton = new ToggleButton("In Progress");
        inProgressButton.setToggleGroup(taskStateToggleGroup);
        inProgressButton.setUserData("InProgress");
        inProgressButton.setDisable(true);

        ToggleButton completedButton = new ToggleButton("Completed");
        completedButton.setToggleGroup(taskStateToggleGroup);
        completedButton.setUserData("Completed");
        completedButton.setDisable(true);

        state.getButtons().addAll(notStartedButton, inProgressButton, completedButton);
        state.setToggleGroup(taskStateToggleGroup);

        state.getToggleGroup().selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                taskState.set(newValue.getUserData().toString());
            }
        });

        taskState.addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                for (ToggleButton button : state.getButtons()) {
                    if (button.getUserData().toString().equals(newValue)) {
                        button.setSelected(true);
                    }
                }
            }
        });
    }

    private void initPriority() {
        ToggleGroup priorityToggleGroup = new ToggleGroup();
        for (Task.Priority taskPriorityEnum : Task.Priority.values()) {
            ToggleButton radioButton = new ToggleButton(taskPriorityEnum.toString());
            radioButton.setToggleGroup(priorityToggleGroup);
            radioButton.setUserData(taskPriorityEnum);
            priority.getButtons().add(radioButton);
        }

        priority.setToggleGroup(priorityToggleGroup);


        priority.getToggleGroup().selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle != null) {
                taskPriority.set(newToggle.getUserData().toString());
            }
        });

        priorityToggleGroup.selectToggle(priority.getButtons().get(0));

        taskPriority.addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals(oldValue)) {
                for (ToggleButton button : priority.getButtons()) {
                    if (button.getUserData().toString().equals(newValue)) {
                        button.setSelected(true);
                    }
                }
            }
        });
    }

    @FXML
    public void openManageWorkspaceView() {
        viewHandler.openView(ViewFactory.MANAGE_WORKSPACE);
    }

    @FXML
    public void onSubmit() {
        if (isNewTask.get()) {
            viewModel.createTask();
        } else {
            viewModel.updateTask();
        }
    }

    @FXML
    public void createTask() {
        isNewTask.set(true);
    }

    @FXML
    public void deleteTask() {
        viewModel.deleteTask();
    }

    @FXML
    public void startTask() {
        viewModel.startTask();
    }

    @FXML
    public void completeTask() {
        viewModel.completeTask();
    }

    @FXML
    public void logout() {
        viewModel.logout();
        viewHandler.openView(ViewFactory.WELCOME);
    }
}
