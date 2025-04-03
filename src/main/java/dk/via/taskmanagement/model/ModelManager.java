package dk.via.taskmanagement.model;

import dk.via.taskmanagement.client.Client;
import dk.via.taskmanagement.client.ClientImplementation;
import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.shared.Connector;
import dk.via.taskmanagement.validation.TaskValidation;
import dk.via.taskmanagement.validation.UserValidation;
import dk.via.taskmanagement.validation.WorkspaceValidation;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelManager implements Model, PropertyChangeListener {
    private final Client client;
    private final PropertyChangeSupport support;

    public ModelManager(Connector connector) throws RemoteException {
        this.client = new ClientImplementation(connector);
        this.client.addPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public synchronized Workspace createWorkspace(Workspace workspace) throws ValidationException, SQLException, RemoteException {
        WorkspaceValidation.validate(workspace);

        return client.createWorkspace(workspace);
    }

    @Override
    public synchronized void addWorkSpaceUser(Workspace workspace, User newUser) throws SQLException, RemoteException {
        client.addWorkSpaceUser(workspace, newUser);
    }

    @Override
    public synchronized User createUser(User user) throws SQLException, RemoteException, ValidationException {
        UserValidation.validate(user);

        return client.createUser(user);
    }

    @Override
    public synchronized User authenticateUser(String username, String password) throws AuthenticationException, SQLException, RemoteException {
        return client.authenticateUser(username, password);
    }

    @Override
    public ArrayList<User> getUsersWithoutWorkspace() throws SQLException, RemoteException {
        return client.getUsersWithoutWorkspace();
    }

    @Override
    public ArrayList<User> getUsersForWorkspace(Workspace workspace) throws SQLException, RemoteException {
        return client.getUsersForWorkspace(workspace);
    }

    @Override
    public synchronized Task createTask(Task task) throws ValidationException, SQLException, RemoteException {
        TaskValidation.validate(task);

        return client.createTask(task);
    }

    @Override
    public synchronized Task updateTask(Task task) throws ValidationException, SQLException, RemoteException {
        TaskValidation.validate(task);

        return client.updateTask(task);
    }

    @Override
    public synchronized Task deleteTask(Task task) throws SQLException, RemoteException {
        return client.deleteTask(task);
    }

    @Override
    public synchronized Task startTask(Task task) throws SQLException, RemoteException {
        task.startTask();

        return client.startTask(task);
    }

    @Override
    public synchronized Task completeTask(Task task) throws SQLException, RemoteException {
        task.completeTask();

        return client.completeTask(task);
    }

    @Override
    public ArrayList<Task> getTasksForWorkspace(Workspace workspace) throws SQLException, RemoteException {
        return client.getTasksForWorkspace(workspace);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if (evt.getPropertyName().equals("createWorkspace")) {
                support.firePropertyChange(new PropertyChangeEvent(this, "createWorkspace", null, evt.getNewValue()));
            } else if (evt.getPropertyName().equals("addWorkSpaceUser")) {
                support.firePropertyChange(new PropertyChangeEvent(this, "addWorkSpaceUser", null, evt.getNewValue()));
            } else if (evt.getPropertyName().equals("updateTasks")) {
                support.firePropertyChange(new PropertyChangeEvent(this, "updateTasks", null, evt.getNewValue()));
            }
        });
    }
}
