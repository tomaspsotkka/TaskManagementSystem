package dk.via.taskmanagement.view;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.utilities.Auth;
import dk.via.taskmanagement.viewmodel.ManageWorkspaceViewModel;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

public class ManageWorkspaceView {
    @FXML
    TableView<User> currentUsers;
    @FXML
    TableView<User> usersWithoutWorkspace;
    @FXML
    Label userWithoutWorkspaceSelectedLabel;
    @FXML
    Label workspaceName;
    private ViewHandler viewHandler;
    private ManageWorkspaceViewModel manageWorkspaceViewModel;
    private Region root;
    @FXML
    private TableColumn<User, String> currentUsersIdColumn;
    @FXML
    private TableColumn<User, String> currentUsersUsernameColumn;
    @FXML
    private TableColumn<User, String> usersWithoutWorkspaceIdColumn;
    @FXML
    private TableColumn<User, String> usersWithoutWorkspaceUsernameColumn;
    private ReadOnlyObjectProperty<User> usersWithoutWorkspaceSelected;

    public void init(ViewHandler viewHandler, ManageWorkspaceViewModel manageWorkspaceViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.manageWorkspaceViewModel = manageWorkspaceViewModel;
        this.root = root;

        this.manageWorkspaceViewModel.init();

        manageWorkspaceViewModel.bindCurrentUsers(currentUsers.itemsProperty());
        this.currentUsersIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.currentUsersUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));

        manageWorkspaceViewModel.bindUsersWithoutWorkspace(usersWithoutWorkspace.itemsProperty());
        this.usersWithoutWorkspaceIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.usersWithoutWorkspaceUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        this.usersWithoutWorkspaceSelected = usersWithoutWorkspace.getSelectionModel().selectedItemProperty();

        manageWorkspaceViewModel.bindUsersWithoutWorkspaceSelected(usersWithoutWorkspaceSelected);
        manageWorkspaceViewModel.bindUserWithoutWorkspaceSelectedText(userWithoutWorkspaceSelectedLabel.textProperty());

        workspaceName.setText(Auth.getInstance().getCurrentUser().getWorkspace().getName());
    }

    public void onSelect() {
        manageWorkspaceViewModel.onSelect();
    }

    public void addUserToWorkspace() {
        manageWorkspaceViewModel.addUserToWorkspace();
    }

    public void openWorkspaceView() {
        viewHandler.openView(ViewFactory.WORKSPACE);
    }
}