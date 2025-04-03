package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class RegisterViewModelTest {
    private Model model;
    private RegisterViewModel viewModel;

    private StringProperty username;
    private StringProperty password;
    private StringProperty passwordConfirmation;
    private StringProperty message;
    private ObjectProperty<String> role;


    @BeforeEach
    void setUp() {
        model = Mockito.mock(Model.class);
        viewModel = new RegisterViewModel(model);

        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        passwordConfirmation = new SimpleStringProperty();
        message = new SimpleStringProperty();
        role = new SimpleObjectProperty<>();

        viewModel.bindUsername(username);
        viewModel.bindPassword(password);
        viewModel.bindPasswordConfirmation(passwordConfirmation);
        viewModel.bindMessage(message);
        viewModel.bindRole(role);
    }

    @Test
    void testRegisterUser() throws ValidationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("password1234");
        passwordConfirmation.set("password1234");
        role.set("admin");


        User user = new User(username.get(), password.get(), role.get(), null);
        Mockito.when(model.createUser(any())).thenReturn(user);
        User ret = viewModel.register();


        assertNotEquals("Username must be at least 8 characters long", message.get());
        assertNotEquals("Password must be at least 8 characters long", message.get());
        assertNotEquals("Passwords do not match", message.get());
        assertEquals("User created successfully", message.get());
        assertEquals(user, ret);
    }

    @Test
    void testRegisterUserTooShortUsername() throws ValidationException, SQLException, RemoteException {
        username.set("user");
        password.set("password1234");
        passwordConfirmation.set("password1234");
        role.set("admin");

        User user = new User(username.get(), password.get(), role.get(), null);
        Mockito.when(model.createUser(any())).thenReturn(user);
        User ret = viewModel.register();

        assertEquals("Username must be at least 8 characters long", message.get());
        assertNull(ret);
    }

    @Test
    void testRegisterUserTooShortPassword() throws ValidationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("pass");
        passwordConfirmation.set("pass");
        role.set("admin");

        User user = new User(username.get(), password.get(), role.get(), null);
        Mockito.when(model.createUser(any())).thenReturn(user);
        User ret = viewModel.register();

        assertEquals("Password must be at least 8 characters long", message.get());
        assertNull(ret);
    }

    @Test
    void testRegisterUserPasswordsDoNotMatch() throws ValidationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("password1234");
        passwordConfirmation.set("password12345");
        role.set("admin");

        User user = new User(username.get(), password.get(), role.get(), null);
        Mockito.when(model.createUser(any())).thenReturn(user);
        User ret = viewModel.register();

        assertEquals("Passwords do not match", message.get());
        assertNull(ret);
    }

    @Test
    void testRegisterUserInvalidRole() throws ValidationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("password1234");
        passwordConfirmation.set("password1234");
        role.set("invalid");

        User user = new User(username.get(), password.get(), role.get(), null);
        Mockito.when(model.createUser(any())).thenReturn(user);
        User ret = viewModel.register();

        assertEquals("Invalid role", message.get());
        assertNull(ret);
    }

    @Test
    void testRegisterUserException() throws ValidationException, SQLException, RemoteException {
        username.set("user1234");
        password.set("password1234");
        passwordConfirmation.set("password1234");
        role.set("admin");

        Mockito.when(model.createUser(any())).thenThrow(new ValidationException("Username is not valid"));
        User ret = viewModel.register();

        assertEquals("Username is not valid", message.get());
        assertNull(ret);
    }

}
