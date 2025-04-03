package dk.via.taskmanagement.viewmodel;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.Model;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.validation.UserValidation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class RegisterViewModel {
    private final Model model;

    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty passwordConfirmation;
    private final StringProperty message;

    private final StringProperty role;

    public RegisterViewModel(Model model) {
        this.model = model;
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        passwordConfirmation = new SimpleStringProperty();
        message = new SimpleStringProperty();
        role = new SimpleStringProperty();
    }

    public void bindUsername(StringProperty property) {
        property.bindBidirectional(username);
    }

    public void bindPassword(StringProperty property) {
        property.bindBidirectional(password);
    }

    public void bindPasswordConfirmation(StringProperty property) {
        property.bindBidirectional(passwordConfirmation);
    }

    public void bindMessage(StringProperty property) {
        property.bind(message);
    }

    public void bindRole(ObjectProperty<String> property) {
        property.bindBidirectional(role);
    }

    public User register() {
        if (!UserValidation.validateUsername(username.get())) {
            message.set("Username must be at least 8 characters long");
            return null;
        }

        if (!UserValidation.validatePassword(password.get())) {
            message.set("Password must be at least 8 characters long");
            return null;
        }

        if (!UserValidation.validatePasswordConfirmation(password.get(), passwordConfirmation.get())) {
            message.set("Passwords do not match");
            return null;
        }

        if (!UserValidation.validateRole(role.get())) {
            message.set("Invalid role");
            return null;
        }

        User user = new User(username.get(), password.get(), role.get(), null);

        try {
            user = model.createUser(user);
        } catch (SQLException | RemoteException | ValidationException e) {
            message.set(e.getMessage());
            return null;
        }

        message.set("User created successfully");

        return user;
    }


}
