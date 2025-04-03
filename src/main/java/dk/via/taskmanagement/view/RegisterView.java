package dk.via.taskmanagement.view;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.utilities.Auth;
import dk.via.taskmanagement.viewmodel.RegisterViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;


public class RegisterView {
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    TextField passwordConfirmation;
    @FXML
    ComboBox<String> role;
    @FXML
    Label message;
    private ViewHandler viewHandler;
    private RegisterViewModel registerViewModel;
    private Region root;

    public void init(ViewHandler viewHandler, RegisterViewModel registerViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.registerViewModel = registerViewModel;
        this.root = root;

        role.getItems().addAll("admin", "member");

        registerViewModel.bindUsername(username.textProperty());
        registerViewModel.bindPassword(password.textProperty());
        registerViewModel.bindPasswordConfirmation(passwordConfirmation.textProperty());
        registerViewModel.bindMessage(message.textProperty());
        registerViewModel.bindRole(role.valueProperty());
    }

    @FXML
    public void openWelcomeView() {
        viewHandler.openView(ViewFactory.WELCOME);
    }


    private void openCreateWorkspaceView() {
        viewHandler.openView(ViewFactory.CREATE_WORKSPACE);
    }

    @FXML
    public void register() {
        User user = registerViewModel.register();
        if (user != null && role.getValue().equals("admin")) {
            openCreateWorkspaceView();
        }

        Auth.getInstance().setCurrentUser(user);
    }

}
