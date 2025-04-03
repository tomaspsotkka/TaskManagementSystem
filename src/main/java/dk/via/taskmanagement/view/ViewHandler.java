package dk.via.taskmanagement.view;

import dk.via.taskmanagement.viewmodel.ViewModelFactory;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ViewHandler {
    private final Scene currentScene;
    private final ViewFactory viewFactory;
    private final ViewModelFactory viewModelFactory;
    private Stage primaryStage;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        this.viewFactory = new ViewFactory(this, viewModelFactory);
        this.viewModelFactory = viewModelFactory;
        this.currentScene = new Scene(new Region());
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        openView(ViewFactory.WELCOME);
    }

    public void openView(String id) {
        Region root = viewFactory.load(id);
        currentScene.setRoot(root);
        if (root.getUserData() == null) {
            primaryStage.setTitle("");
        } else {
            primaryStage.setTitle(root.getUserData().toString());
        }
        primaryStage.setScene(currentScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

}
