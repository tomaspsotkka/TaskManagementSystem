package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.exceptions.AuthenticationException;
import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.utilities.Auth;
import dk.via.taskmanagement.validation.UserValidation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class LoginViewModel {
    private final Model model;

    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty message;

    public LoginViewModel(Model model) {
        this.model = model;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        message = new SimpleStringProperty();
    }

    public void bindUsername(StringProperty property) {
        property.bindBidirectional(username);
    }

    public void bindPassword(StringProperty property) {
        property.bindBidirectional(password);
    }

    public void bindMessage(StringProperty property) {
        property.bind(message);
    }

    public User login() {
        if (!UserValidation.validateUsername(username.get())) {
            message.set("Username must be at least 8 characters long");
            return null;
        }

        if (!UserValidation.validatePassword(password.get())) {
            message.set("Password must be at least 8 characters long");
            return null;
        }

        try {
            User user = model.authenticateUser(username.get(), password.get());

            Auth.getInstance().setCurrentUser(user);
            message.set("Login successful");

            return user;
        } catch (AuthenticationException e) {
            message.set("Invalid username or password");
            return null;
        } catch (RemoteException | SQLException e) {
            message.set(e.getMessage());
            return null;
        }
    }
}
