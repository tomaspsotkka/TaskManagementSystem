package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.utilities.Auth;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageWorkspaceViewModel implements PropertyChangeListener {
    private final Model model;
    private final ObjectProperty<User> usersWithoutWorkspaceSelected;
    private final StringProperty userWithoutWorkspaceSelectedText;
    StringProperty workspaceName;
    ListProperty<User> currentUsers;
    ListProperty<User> usersWithoutWorkspace;
    StringProperty message;


    public ManageWorkspaceViewModel(Model model) {
        this.model = model;

        currentUsers = new SimpleListProperty<>(FXCollections.observableArrayList());
        usersWithoutWorkspace = new SimpleListProperty<>(FXCollections.observableArrayList());
        workspaceName = new SimpleStringProperty();
        usersWithoutWorkspaceSelected = new SimpleObjectProperty<>();
        userWithoutWorkspaceSelectedText = new SimpleStringProperty();
        message = new SimpleStringProperty();
        model.addPropertyChangeListener(this);

    }

    public void init() {
        ArrayList<User> usersWithoutWorkspace;
        ArrayList<User> usersForWorkspace;

        try {
            usersWithoutWorkspace = model.getUsersWithoutWorkspace();
            usersForWorkspace = model.getUsersForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());
        } catch (SQLException | RemoteException e) {
            message.set(e.getMessage());
            return;
        }

        this.usersWithoutWorkspace.clear();
        this.usersWithoutWorkspace.addAll(usersWithoutWorkspace);

        this.currentUsers.clear();
        this.currentUsers.addAll(usersForWorkspace);
    }

    public void bindCurrentUsers(ObjectProperty<ObservableList<User>> property) {
        property.bind(currentUsers);
    }

    public void bindUsersWithoutWorkspace(ObjectProperty<ObservableList<User>> property) {
        property.bind(usersWithoutWorkspace);
    }

    public void bindUsersWithoutWorkspaceSelected(ReadOnlyObjectProperty<User> property) {
        usersWithoutWorkspaceSelected.bind(property);
    }

    public void bindUserWithoutWorkspaceSelectedText(StringProperty property) {
        property.bind(userWithoutWorkspaceSelectedText);
    }

    public void bindMessage(StringProperty property) {
        property.bind(message);
    }

    public void onSelect() {
        if (usersWithoutWorkspaceSelected.get() != null) {
            userWithoutWorkspaceSelectedText.set("Selected: " + usersWithoutWorkspaceSelected.get().getUserName());
        } else {
            userWithoutWorkspaceSelectedText.set("Selected: ");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("addWorkSpaceUser")) {
            if (Auth.getInstance().getCurrentUser() == null) {
                return;
            }

            ArrayList<User> usersForWorkspace;
            ArrayList<User> usersWithoutWorkspace;

            try {
                usersForWorkspace = model.getUsersForWorkspace(Auth.getInstance().getCurrentUser().getWorkspace());
                usersWithoutWorkspace = model.getUsersWithoutWorkspace();
            } catch (SQLException | RemoteException e) {
                message.set(e.getMessage());
                return;
            }

            this.currentUsers.clear();
            this.currentUsers.addAll(usersForWorkspace);

            this.usersWithoutWorkspace.clear();
            this.usersWithoutWorkspace.addAll(usersWithoutWorkspace);
        }
    }

    public void addUserToWorkspace() {
        User user = usersWithoutWorkspaceSelected.get();
        Workspace workspace = Auth.getInstance().getCurrentUser().getWorkspace();

        try {
            model.addWorkSpaceUser(workspace, user);
        } catch (SQLException | RemoteException e) {
            message.set(e.getMessage());
        }
    }
}
