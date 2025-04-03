package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.utilities.Auth;
import dk.via.taskmanagement.validation.WorkspaceValidation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class CreateWorkspaceViewModel {
    private final Model model;

    private final StringProperty name;

    private final StringProperty message;

    public CreateWorkspaceViewModel(Model model) {
        this.model = model;
        name = new SimpleStringProperty();
        message = new SimpleStringProperty();
    }

    public void bindName(StringProperty property) {
        property.bindBidirectional(name);
    }

    public void bindMessage(StringProperty property) {
        property.bind(message);
    }

    public Workspace createWorkspace() {
        if (!WorkspaceValidation.validateName(name.get())) {
            message.set("Name must not be empty");
            return null;
        }

        Workspace workspace;

        try {
            workspace = model.createWorkspace(new Workspace(name.get()));
        } catch (SQLException | ValidationException | RemoteException e) {
            message.set(e.getMessage());
            return null;
        }

        User user = Auth.getInstance().getCurrentUser();

        try {
            model.addWorkSpaceUser(workspace, user);
        } catch (SQLException | RemoteException e) {
            message.set(e.getMessage());
            return null;
        }

        message.set("Workspace created successfully");

        Auth.getInstance().setCurrentUser(null);
        return workspace;
    }
}
