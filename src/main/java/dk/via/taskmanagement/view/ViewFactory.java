package dk.via.taskmanagement.view;

import dk.via.taskmanagement.viewmodel.ViewModelFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.io.IOError;
import java.io.IOException;

public class ViewFactory {
    public static final String WELCOME = "WELCOME";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String WORKSPACE = "WORKSPACE";
    public static final String CREATE_WORKSPACE = "CREATE_WORKSPACE";

    public static final String MANAGE_WORKSPACE = "MANAGE_WORKSPACE";


    private final ViewHandler viewHandler;
    private final ViewModelFactory viewModelFactory;

    private WelcomeView welcomeView;
    private LoginView loginView;
    private RegisterView registerView;

    public ViewFactory(ViewHandler viewHandler, ViewModelFactory viewModelFactory) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;

        this.welcomeView = null;
        this.loginView = null;
        this.registerView = null;
    }

    public Region loadWelcomeView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("WelcomeView.fxml"));
        try {
            Region root = loader.load();
            welcomeView = loader.getController();
            welcomeView.init(viewHandler, root);
            return root;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public Region loadLoginView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoginView.fxml"));
        try {
            Region root = loader.load();
            loginView = loader.getController();
            loginView.init(viewHandler, viewModelFactory.getLoginViewModel(), root);
            return root;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public Region loadRegisterView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("RegisterView.fxml"));
        try {
            Region root = loader.load();
            registerView = loader.getController();
            registerView.init(viewHandler, viewModelFactory.getRegisterViewModel(), root);
            return root;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public Region loadCreateWorkspaceView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("CreateWorkspaceView.fxml"));
        try {
            Region root = loader.load();
            CreateWorkspaceView createWorkspaceView = loader.getController();
            createWorkspaceView.init(viewHandler, viewModelFactory.getCreateWorkspaceViewModel(), root);
            return root;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public Region loadWorkspaceView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("WorkspaceView.fxml"));
        try {
            Region root = loader.load();
            WorkspaceView workspaceView = loader.getController();
            workspaceView.init(viewHandler, viewModelFactory.getWorkspaceViewModel(), root);
            return root;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public Region loadManageWorkspaceView() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ManageWorkspaceView.fxml"));
        try {
            Region root = loader.load();
            ManageWorkspaceView manageWorkspaceView = loader.getController();
            manageWorkspaceView.init(viewHandler, viewModelFactory.getManageWorkspaceViewModel(), root);
            return root;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    public Region load(String id) {
        return switch (id) {
            case WELCOME -> loadWelcomeView();
            case LOGIN -> loadLoginView();
            case REGISTER -> loadRegisterView();
            case CREATE_WORKSPACE -> loadCreateWorkspaceView();
            case WORKSPACE -> loadWorkspaceView();
            case MANAGE_WORKSPACE -> loadManageWorkspaceView();
            default -> throw new IllegalArgumentException("Unknown view: " + id);
        };
    }
}
