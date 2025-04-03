package dk.via.taskmanagement.model;

import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.exceptions.ValidationException;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Model {
    Workspace createWorkspace(Workspace workspace) throws RemoteException, SQLException, ValidationException;

    void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException, SQLException;

    User createUser(User user) throws RemoteException, SQLException, ValidationException;

    User authenticateUser(String username, String password) throws AuthenticationException, RemoteException, SQLException;

    ArrayList<User> getUsersWithoutWorkspace() throws RemoteException, SQLException;

    ArrayList<User> getUsersForWorkspace(Workspace workspace) throws RemoteException, SQLException;

    Task createTask(Task task) throws ValidationException, RemoteException, SQLException;

    Task updateTask(Task task) throws ValidationException, RemoteException, SQLException;

    Task deleteTask(Task task) throws RemoteException, SQLException;

    Task startTask(Task task) throws RemoteException, SQLException;

    Task completeTask(Task task) throws RemoteException, SQLException;

    ArrayList<Task> getTasksForWorkspace(Workspace workspace) throws RemoteException, SQLException;

    void addPropertyChangeListener(PropertyChangeListener listener);

    void removePropertyChangeListener(PropertyChangeListener listener);
}
