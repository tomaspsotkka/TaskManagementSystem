package dk.via.taskmanagement;

import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.ModelManager;
import dk.via.taskmanagement.shared.Connector;
import dk.via.taskmanagement.view.ViewHandler;
import dk.via.taskmanagement.viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartClient extends Application {
    public void start(Stage stage) throws RemoteException, NotBoundException {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            Connector connector = (Connector) registry.lookup("Connector");
            Model model = new ModelManager(connector);

            ViewModelFactory viewModelFactory = new ViewModelFactory(model);
            ViewHandler viewHandler = new ViewHandler(viewModelFactory);

            viewHandler.start(stage);
        } catch (RemoteException e) {
            displayCouldNotConnectToServerAlert();
        }
    }

    private void displayCouldNotConnectToServerAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not connect to server");
        alert.setContentText("Please make sure the server is running and try again.");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
}
