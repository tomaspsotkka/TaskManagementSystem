package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.utilities.Auth;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

public class ManageWorkspaceViewModelTest {
    private Model model;
    private ManageWorkspaceViewModel viewModel;

    ObjectProperty<ObservableList<User>> currentUsers;
    ObjectProperty<ObservableList<User>> usersWithoutWorkspace;
    ReadOnlyObjectProperty<User> usersWithoutWorkspaceSelected;
    StringProperty userWithoutWorkspaceSelectedText;
    StringProperty message;

    @BeforeEach
    void setUp() {
        model = Mockito.mock(Model.class);
        viewModel = new ManageWorkspaceViewModel(model);

        currentUsers = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        usersWithoutWorkspace = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        message = new SimpleStringProperty();
        usersWithoutWorkspaceSelected = new SimpleObjectProperty<>();
        userWithoutWorkspaceSelectedText = new SimpleStringProperty();

        viewModel.bindCurrentUsers(currentUsers);
        viewModel.bindUsersWithoutWorkspace(usersWithoutWorkspace);
        viewModel.bindMessage(message);
        viewModel.bindUsersWithoutWorkspaceSelected(usersWithoutWorkspaceSelected);
        viewModel.bindUserWithoutWorkspaceSelectedText(userWithoutWorkspaceSelectedText);
    }

    @Test
    void testInit() throws SQLException, RemoteException {
        ArrayList<User> usersWithoutWorkspaceList = new ArrayList<>();
        ArrayList<User> usersForWorkspaceList = new ArrayList<>();

        User user = new User("user1234", "password1234", "admin", null);
        Auth auth11 = Auth.getInstance();

        try(MockedStatic<Auth> auth = Mockito.mockStatic(Auth.class)) {
            auth11.setCurrentUser(user);

            auth.when(Auth::getInstance).thenReturn(auth11);
        }

        Mockito.when(model.getUsersWithoutWorkspace()).thenReturn(usersWithoutWorkspaceList);
        Mockito.when(model.getUsersForWorkspace(null)).thenReturn(usersForWorkspaceList);

        viewModel.init();

        assertEquals(usersWithoutWorkspaceList, usersWithoutWorkspace.get());
        assertEquals(usersForWorkspaceList, currentUsers.get());
    }

    @Test
    void testBindCurrentUsers() {
        ObjectProperty<ObservableList<User>> property = new SimpleObjectProperty<>();
        viewModel.bindCurrentUsers(property);
        assertEquals(currentUsers.get(), property.get());
    }

    @Test
    void testBindUsersWithoutWorkspace() {
        ObjectProperty<ObservableList<User>> property = new SimpleObjectProperty<>();
        viewModel.bindUsersWithoutWorkspace(property);
        assertEquals(usersWithoutWorkspace.get(), property.get());
    }

    @Test
    void testBindUsersWithoutWorkspaceSelected() {
        ReadOnlyObjectProperty<User> property = new SimpleObjectProperty<>();
        viewModel.bindUsersWithoutWorkspaceSelected(property);
        assertEquals(usersWithoutWorkspaceSelected.get(), property.get());
    }

    @Test
    void testBindUserWithoutWorkspaceSelectedText() {
        StringProperty property = new SimpleStringProperty();
        viewModel.bindUserWithoutWorkspaceSelectedText(property);
        assertEquals(userWithoutWorkspaceSelectedText.get(), property.get());
    }

    @Test
    void testBindMessage() {
        StringProperty property = new SimpleStringProperty();
        viewModel.bindMessage(property);
        assertEquals(message.get(), property.get());
    }

    @Test
    void testOnSelect() {
        viewModel.onSelect();
        assertNull(usersWithoutWorkspaceSelected.get());
    }

    @Test
    void testOnAddUserToWorkspace() throws SQLException, RemoteException {

        User user = new User("user1234", "password1234", "admin", null);

        Auth auth11 = Auth.getInstance();
        Mockito.doNothing().when(model).addWorkSpaceUser(null, user);
        try(MockedStatic<Auth> auth = Mockito.mockStatic(Auth.class)) {
            auth11.setCurrentUser(user);

            auth.when(Auth::getInstance).thenReturn(auth11);
        }
    }
}
