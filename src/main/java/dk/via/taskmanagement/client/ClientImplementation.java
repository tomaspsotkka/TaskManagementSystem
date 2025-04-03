package dk.via.taskmanagement.client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.shared.Connector;
import javafx.application.Platform;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientImplementation extends UnicastRemoteObject implements Client, RemotePropertyChangeListener {
    private final Connector connector;
    private final PropertyChangeSupport propertyChangeSupport;

    public ClientImplementation(Connector connector) throws RemoteException {
        this.connector = connector;
        propertyChangeSupport = new PropertyChangeSupport(this);
        this.connector.addRemotePropertyChangeListener(this);
    }

    @Override
    public Workspace createWorkspace(Workspace workspace) throws RemoteException, SQLException {
        return connector.createWorkspace(workspace);
    }

    @Override
    public void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException, SQLException {
        connector.addWorkSpaceUser(workspace, newUser);
    }

    @Override
    public User createUser(User user) throws RemoteException, SQLException {
        return connector.createUser(user);
    }

    @Override
    public User authenticateUser(String username, String password) throws RemoteException, AuthenticationException, SQLException {

        return connector.authenticateUser(username, password);
    }

    @Override
    public ArrayList<User> getUsersWithoutWorkspace() throws RemoteException, SQLException {
        return connector.getUsersWithoutWorkspace();
    }

    @Override
    public ArrayList<User> getUsersForWorkspace(Workspace workspace) throws RemoteException, SQLException {
        return connector.getUsersForWorkspace(workspace);
    }

    @Override
    public Task createTask(Task task) throws RemoteException, SQLException {
        return connector.createTask(task);
    }

    @Override
    public Task updateTask(Task task) throws RemoteException, SQLException {
        return connector.updateTask(task);
    }

    @Override
    public Task deleteTask(Task task) throws RemoteException, SQLException {
        return connector.deleteTask(task);
    }

    @Override
    public Task startTask(Task task) throws RemoteException, SQLException {
        return connector.startTask(task);
    }

    @Override
    public Task completeTask(Task task) throws RemoteException, SQLException {
        return connector.completeTask(task);
    }

    @Override
    public ArrayList<Task> getTasksForWorkspace(Workspace workspace) throws RemoteException, SQLException {
        return connector.getTasksForWorkspace(workspace);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(RemotePropertyChangeEvent remotePropertyChangeEvent) throws RemoteException {
        Platform.runLater(() -> {
            if (remotePropertyChangeEvent.getPropertyName().equals("createWorkspace")) {
                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "createWorkspace", null, remotePropertyChangeEvent.getNewValue()));
            } else if (remotePropertyChangeEvent.getPropertyName().equals("addWorkSpaceUser")) {
                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "addWorkSpaceUser", null, remotePropertyChangeEvent.getNewValue()));
            } else if (remotePropertyChangeEvent.getPropertyName().equals("updateTasks")) {
                propertyChangeSupport.firePropertyChange(new PropertyChangeEvent(this, "updateTasks", null, remotePropertyChangeEvent.getNewValue()));
            }
        });
    }
}
