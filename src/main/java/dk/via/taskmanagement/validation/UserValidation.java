package dk.via.taskmanagement.validation;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.User;

public class UserValidation {
    public static void validate(User user) throws ValidationException {
        if (!validateUsername(user.getUserName())) {
            throw new ValidationException("Username is not valid");
        }

        if (!validatePassword(user.getPassword())) {
            throw new ValidationException("Password is not valid");
        }

        if (!validateRole(user.getRole())) {
            throw new ValidationException("Role is not valid");
        }
    }

    public static boolean validateUsername(String username) {
        return username != null && username.length() >= 8;
    }

    public static boolean validatePassword(String password) {
        return password != null && password.length() >= 8;
    }

    public static boolean validatePasswordConfirmation(String password, String passwordConfirmation) {
        return password != null && password.equals(passwordConfirmation);
    }

    public static boolean validateRole(String role) {
        return role != null && (role.equals("admin") || role.equals("member"));
    }
}
