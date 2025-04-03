package dk.via.taskmanagement.server;

import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.server.dao.TaskDaoImplementation;
import dk.via.taskmanagement.server.dao.UserDAOImplementation;
import dk.via.taskmanagement.server.dao.WorkspaceDAOImplementation;
import dk.via.taskmanagement.shared.Connector;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RemoteConnector implements Connector {
    private final RemotePropertyChangeSupport support;

    public RemoteConnector() {
        support = new RemotePropertyChangeSupport();
    }


    @Override
    public Workspace createWorkspace(Workspace workspace) throws RemoteException, SQLException {
        Workspace newWorkspace = WorkspaceDAOImplementation.getInstance().createWorkspace(workspace);

        support.firePropertyChange("createWorkspace", null, newWorkspace);

        return newWorkspace;
    }

    @Override
    public void addWorkSpaceUser(Workspace workspace, User newUser) throws RemoteException, SQLException {
        WorkspaceDAOImplementation.getInstance().addWorkSpaceUser(workspace, newUser);

        support.firePropertyChange("addWorkSpaceUser", null, newUser);
    }

    @Override
    public User createUser(User user) throws RemoteException, SQLException {
        return UserDAOImplementation.getInstance().create(user);
    }

    @Override
    public User authenticateUser(String username, String password) throws RemoteException, AuthenticationException, SQLException {
        User user = UserDAOImplementation.getInstance().getByUsername(username);

        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid username or password");
        }

        return user;
    }

    @Override
    public ArrayList<User> getUsersWithoutWorkspace() throws RemoteException, SQLException {
        return UserDAOImplementation.getInstance().getUsersWithoutWorkspace();
    }

    @Override
    public ArrayList<User> getUsersForWorkspace(Workspace workspace) throws RemoteException, SQLException {
        return UserDAOImplementation.getInstance().getUsersForWorkspace(workspace);
    }

    @Override
    public Task createTask(Task task) throws RemoteException, SQLException {
        TaskDaoImplementation.getInstance().createTask(task);

        support.firePropertyChange("updateTasks", null, task);

        return task;

    }

    @Override
    public Task updateTask(Task task) throws RemoteException, SQLException {
        TaskDaoImplementation.getInstance().updateTask(task);

        support.firePropertyChange("updateTasks", null, task);

        return task;
    }

    @Override
    public Task deleteTask(Task task) throws RemoteException, SQLException {
        TaskDaoImplementation.getInstance().deleteTask(task);

        support.firePropertyChange("updateTasks", null, task);

        return task;
    }

    @Override
    public Task startTask(Task task) throws RemoteException, SQLException {
        TaskDaoImplementation.getInstance().updateTaskState(task);

        support.firePropertyChange("updateTasks", null, task);

        return task;
    }

    @Override
    public Task completeTask(Task task) throws RemoteException, SQLException {
        TaskDaoImplementation.getInstance().updateTaskState(task);

        support.firePropertyChange("updateTasks", null, task);

        return task;
    }

    @Override
    public ArrayList<Task> getTasksForWorkspace(Workspace workspace) throws RemoteException, SQLException {
        return TaskDaoImplementation.getInstance().getTasksForWorkspace(workspace);
    }

    @Override
    public void addRemotePropertyChangeListener(RemotePropertyChangeListener listener) throws RemoteException {
        support.addPropertyChangeListener(listener);
    }
}
