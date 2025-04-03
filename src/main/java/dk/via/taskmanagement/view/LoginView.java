package dk.via.taskmanagement.view;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.viewmodel.LoginViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;


public class LoginView {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label message;
    private ViewHandler viewHandler;
    private LoginViewModel loginViewModel;
    private Region root;

    public void init(ViewHandler viewHandler, LoginViewModel loginViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.loginViewModel = loginViewModel;
        this.root = root;

        loginViewModel.bindUsername(username.textProperty());
        loginViewModel.bindPassword(password.textProperty());
        loginViewModel.bindMessage(message.textProperty());
    }

    @FXML
    public void login() {
        User user = loginViewModel.login();

        if (user != null && user.getWorkspace() == null) {
            showWorkSpaceMissingAlert();
            return;
        }

        if (user != null) {
            openWorkspaceView();
        }
    }

    private void openWorkspaceView() {
        viewHandler.openView(ViewFactory.WORKSPACE);
    }

    private void showWorkSpaceMissingAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("You don't have a workspace yet. Wait for an administrator to assign you to one.");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                openWelcomeView();
            }
        });
    }

    @FXML
    public void openWelcomeView() {
        viewHandler.openView(ViewFactory.WELCOME);
    }
}
