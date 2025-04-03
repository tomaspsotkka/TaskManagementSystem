package dk.via.taskmanagement.view;

import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.viewmodel.CreateWorkspaceViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

public class CreateWorkspaceView {
    @FXML
    TextField name;
    @FXML
    Label message;
    private ViewHandler viewHandler;
    private CreateWorkspaceViewModel createWorkspaceViewModel;
    private Region root;

    public void init(ViewHandler viewHandler, CreateWorkspaceViewModel createWorkspaceViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.createWorkspaceViewModel = createWorkspaceViewModel;
        this.root = root;

        createWorkspaceViewModel.bindName(name.textProperty());
        createWorkspaceViewModel.bindMessage(message.textProperty());
    }

    @FXML
    public void createWorkspace() {
        Workspace workspace = createWorkspaceViewModel.createWorkspace();

        if (workspace != null) {
            openWelcomeView();
        }
    }

    private void openWelcomeView() {
        viewHandler.openView(ViewFactory.WELCOME);
    }

    private void openWorkspaceView() {
        viewHandler.openView(ViewFactory.WORKSPACE);
    }
}
