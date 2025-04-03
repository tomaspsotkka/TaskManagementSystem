package dk.via.taskmanagement.model;

import dk.via.taskmanagement.client.Client;
import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.shared.Connector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

class ModelManagerTest {
    private ModelManager model;
    private Connector connector;

    @BeforeEach
    void setUp() throws RemoteException {
        connector = Mockito.mock(Connector.class);
        model = new ModelManager(connector);
    }

    @Test
    void testCreateWorkspace() throws SQLException, RemoteException, ValidationException, NoSuchFieldException, IllegalAccessException {
        Workspace workspace = new Workspace("Test Workspace");

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.createWorkspace(workspace)).thenReturn(workspace);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        assertEquals(workspace, model.createWorkspace(workspace));
    }

    @Test
    void testCreateUser() throws SQLException, RemoteException, ValidationException, NoSuchFieldException, IllegalAccessException {
        User user = new User("admin123", "passw123", "admin", null);

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.createUser(user)).thenReturn(user);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        assertEquals(user, model.createUser(user));
    }

    @Test
    void testAddWorkSpaceUser() throws SQLException, RemoteException, NoSuchFieldException, IllegalAccessException {
        Workspace workspace = new Workspace("Test Workspace");
        User user = new User("admin123", "passw123", "admin", null);

        Client mockClient = Mockito.mock(Client.class);
        doNothing().when(mockClient).addWorkSpaceUser(workspace, user);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        model.addWorkSpaceUser(workspace, user);

        Mockito.verify(mockClient, Mockito.times(1)).addWorkSpaceUser(workspace, user);
    }

    @Test
    void testAuthenticateUser() throws SQLException, RemoteException, AuthenticationException, NoSuchFieldException, IllegalAccessException {
        String username = "admin123";
        String password = "passw123";

        User user = new User(username, password, "admin", null);

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.authenticateUser(username, password)).thenReturn(user);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        assertEquals(user, model.authenticateUser(username, password));

        Mockito.verify(mockClient, Mockito.times(1)).authenticateUser(username, password);
    }

    @Test
    void testGetUsersWithoutWorkspace() throws SQLException, RemoteException, NoSuchFieldException, IllegalAccessException {
        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.getUsersWithoutWorkspace()).thenReturn(null);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        model.getUsersWithoutWorkspace();

        Mockito.verify(mockClient, Mockito.times(1)).getUsersWithoutWorkspace();
    }

    @Test
    void testGetUsersForWorkspace() throws SQLException, RemoteException, NoSuchFieldException, IllegalAccessException {
        Workspace workspace = new Workspace("Test Workspace");
        User user = new User("admin123", "passw123", "admin", workspace);
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.getUsersForWorkspace(workspace)).thenReturn(users);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        model.getUsersForWorkspace(workspace);

        Mockito.verify(mockClient, Mockito.times(1)).getUsersForWorkspace(workspace);
    }

    @Test
    void testCreateTask() throws SQLException, RemoteException, ValidationException, NoSuchFieldException, IllegalAccessException {
        Task task = new Task("Test Task", null);
        task.setDescription("Test Description");
        task.setDeadline(java.time.LocalDate.now());

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.createTask(task)).thenReturn(task);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        assertEquals(task, model.createTask(task));
    }

    @Test
    void testUpdateTask() throws SQLException, RemoteException, ValidationException, NoSuchFieldException, IllegalAccessException {
        Task task = new Task("Test Task", null);
        task.setDescription("Test Description");
        task.setDeadline(java.time.LocalDate.now());

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.updateTask(task)).thenReturn(task);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        assertEquals(task, model.updateTask(task));
    }

    @Test
    void testDeleteTask() throws SQLException, RemoteException, NoSuchFieldException, IllegalAccessException {
        Task task = new Task("Test Task", null);

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.deleteTask(task)).thenReturn(task);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        assertEquals(task, model.deleteTask(task));
    }

    @Test
    void testStartTask() throws SQLException, RemoteException, NoSuchFieldException, IllegalAccessException {
        Task task = new Task("Test Task", null);

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.startTask(task)).thenReturn(task);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        assertEquals(task, model.startTask(task));
    }

    @Test
    void testCompleteTask() throws SQLException, RemoteException, NoSuchFieldException, IllegalAccessException {
        Task task = new Task("Test Task", null);

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.completeTask(task)).thenReturn(task);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        assertEquals(task, model.completeTask(task));
    }

    @Test
    void testGetTasksForWorkspace() throws SQLException, RemoteException, NoSuchFieldException, IllegalAccessException {
        Workspace workspace = new Workspace("Test Workspace");
        Task task = new Task("Test Task", workspace);
        ArrayList<Task> tasks = new ArrayList<Task>();
        tasks.add(task);

        Client mockClient = Mockito.mock(Client.class);
        Mockito.when(mockClient.getTasksForWorkspace(workspace)).thenReturn(tasks);

        Field clientField = ModelManager.class.getDeclaredField("client");
        clientField.setAccessible(true);
        clientField.set(model, mockClient);

        model.getTasksForWorkspace(workspace);

        Mockito.verify(mockClient, Mockito.times(1)).getTasksForWorkspace(workspace);
    }
}

