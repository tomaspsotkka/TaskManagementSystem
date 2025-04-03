package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.Workspace;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class CreateWorkspaceViewModelTest {
    private Model model;
    private CreateWorkspaceViewModel viewModel;

    private StringProperty name;
    private StringProperty message;

    @BeforeEach
    void setUp() {
        model = Mockito.mock(Model.class);
        viewModel = new CreateWorkspaceViewModel(model);

        name = new SimpleStringProperty();
        message = new SimpleStringProperty();

        viewModel.bindName(name);
        viewModel.bindMessage(message);
    }

    @Test
    void testCreateWorkspace() throws ValidationException, SQLException, RemoteException {
        name.set("Workspace");

        Workspace workspace = new Workspace(name.get());
        Mockito.when(model.createWorkspace(any())).thenReturn(workspace);
        Mockito.doNothing().when(model).addWorkSpaceUser(workspace, null);

        Workspace ret = viewModel.createWorkspace();

        assertNotEquals("Name must not be empty", message.get());
        assertEquals(workspace, ret);
        assertEquals("Workspace created successfully", message.get());
    }

    @Test
    void testWorkspaceEmptyName() throws ValidationException, SQLException, RemoteException {
        name.set("");

        Workspace workspace = new Workspace(name.get());
        Mockito.when(model.createWorkspace(any())).thenReturn(workspace);
        Mockito.doNothing().when(model).addWorkSpaceUser(workspace, null);

        Workspace ret = viewModel.createWorkspace();

        assertEquals("Name must not be empty", message.get());
        assertNull(ret);
    }

    @Test
    void testWorkspaceNullName() throws ValidationException, SQLException, RemoteException {
        name.set(null);

        Workspace workspace = new Workspace(name.get());
        Mockito.when(model.createWorkspace(any())).thenReturn(workspace);
        Mockito.doNothing().when(model).addWorkSpaceUser(workspace, null);

        Workspace ret = viewModel.createWorkspace();

        assertEquals("Name must not be empty", message.get());
        assertNull(ret);
    }

    @Test
    void testCreatingWorkspaceWithException() throws ValidationException, SQLException, RemoteException {
        name.set("Workspace");

        Workspace workspace = new Workspace(name.get());
        Mockito.when(model.createWorkspace(any())).thenThrow(new ValidationException("Validation exception"));
        Mockito.doNothing().when(model).addWorkSpaceUser(workspace, null);

        Workspace ret = viewModel.createWorkspace();

        assertEquals("Validation exception", message.get());
        assertNull(ret);
    }

    @Test
    void testAddingUserToWorkspaceWithException() throws ValidationException, SQLException, RemoteException {
        name.set("Workspace");

        Workspace workspace = new Workspace(name.get());
        Mockito.when(model.createWorkspace(any())).thenReturn(workspace);
        Mockito.doThrow(new SQLException("SQL exception")).when(model).addWorkSpaceUser(workspace, null);

        Workspace ret = viewModel.createWorkspace();

        assertEquals("SQL exception", message.get());
        assertNull(ret);
    }
}
