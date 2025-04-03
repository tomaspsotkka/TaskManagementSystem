package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoginViewModelTest {
    private Model model;
    private LoginViewModel viewModel;

    private StringProperty username;
    private StringProperty password;
    private StringProperty message;

    @BeforeEach
    void setUp() {
        model = Mockito.mock(Model.class);
        viewModel = new LoginViewModel(model);

        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        message = new SimpleStringProperty();

        viewModel.bindUsername(username);
        viewModel.bindPassword(password);
        viewModel.bindMessage(message);
    }


    @Test
    void testUserNameIsLongEnough() {
        username.set("user1234");
        viewModel.login();

        assertNotEquals("Username must be at least 8 characters long", message.get());
    }

    @Test
    void testUserNameIsNotLongEnough() {
        username.set("user");
        viewModel.login();

        assertEquals("Username must be at least 8 characters long", message.get());
    }

    @Test
    void testPasswordIsLongEnough() {
        username.set("user1234");
        password.set("password1234");
        viewModel.login();

        assertNotEquals("Password must be at least 8 characters long", message.get());
    }

    @Test
    void testPasswordIsNotLongEnough() {
        username.set("user1234");
        password.set("pass");
        viewModel.login();

        assertEquals("Password must be at least 8 characters long", message.get());
    }

    @Test
    void testLoginSuccessful() throws AuthenticationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("password1234");

        Mockito.when(model.authenticateUser(username.get(), password.get())).thenReturn(new User("user1234", "password1234", "admin", null));

        viewModel.login();

        assertEquals("Login successful", message.get());
    }

    // test authentication exception
    @Test
    void testAuthenticationException() throws AuthenticationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("password1234");

        Mockito.when(model.authenticateUser(username.get(), password.get())).thenThrow(new AuthenticationException("Invalid username or password"));

        viewModel.login();

        assertEquals("Invalid username or password", message.get());
    }

    @Test
    void testRemoteException() throws AuthenticationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("password1234");

        Mockito.when(model.authenticateUser(username.get(), password.get())).thenThrow(new RemoteException("Remote exception"));

        viewModel.login();

        assertEquals("Remote exception", message.get());
    }

    @Test
    void testSQLException() throws AuthenticationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("password1234");

        Mockito.when(model.authenticateUser(username.get(), password.get())).thenThrow(new SQLException("SQL exception"));

        viewModel.login();

        assertEquals("SQL exception", message.get());
    }


}
