package dk.via.taskmanagement.shared;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Connector extends Remote {
    Workspace createWorkspace(Workspace workspace) throws RemoteException, SQLException;

    void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException, SQLException;

    User createUser(User user) throws RemoteException, SQLException;

    User authenticateUser(String username, String password) throws RemoteException, AuthenticationException, SQLException;

    ArrayList<User> getUsersWithoutWorkspace() throws RemoteException, SQLException;

    ArrayList<User> getUsersForWorkspace(Workspace workspace) throws RemoteException, SQLException;
    
    /*
    TASKS
     */


    Task createTask(Task task) throws RemoteException, SQLException;

    Task updateTask(Task task) throws RemoteException, SQLException;

    Task deleteTask(Task task) throws RemoteException, SQLException;

    Task startTask(Task task) throws RemoteException, SQLException;

    Task completeTask(Task task) throws RemoteException, SQLException;

    ArrayList<Task> getTasksForWorkspace(Workspace workspace) throws RemoteException, SQLException;

    /*
    PROPERTY CHANGE LISTENER
     */

    void addRemotePropertyChangeListener(RemotePropertyChangeListener listener) throws RemoteException;
}
