package dk.via.taskmanagement.view;


import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class WelcomeView {
    private ViewHandler viewHandler;
    private Region root;

    public void init(ViewHandler viewHandler, Region root) {
        this.viewHandler = viewHandler;
        this.root = root;
    }

    @FXML
    public void openLoginView() {
        viewHandler.openView(ViewFactory.LOGIN);
    }

    @FXML
    public void openRegisterView() {
        viewHandler.openView(ViewFactory.REGISTER);
    }
}
