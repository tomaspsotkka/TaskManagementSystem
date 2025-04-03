package dk.via.taskmanagement.validation;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.builders.UserBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

    @Test
    void testZeroUsername() {
        assertFalse(UserValidation.validateUsername(null));
    }

    @Test
    void testOneUsername() {
        assertTrue(UserValidation.validateUsername("Username"));
    }

    @Test
    void testBoundaryUsername() {
        assertFalse(UserValidation.validateUsername("User"));
    }

    @Test
    void testZeroPassword() {
        assertFalse(UserValidation.validatePassword(null));
    }

    @Test
    void testOnePassword() {
        assertTrue(UserValidation.validatePassword("Password"));
    }

    @Test
    void testBoundaryPassword() {
        assertFalse(UserValidation.validatePassword("Pass"));
    }

    @Test
    void testZeroRole() {
        assertFalse(UserValidation.validateRole(null));
    }

    @Test
    void testOneRole() {
        assertTrue(UserValidation.validateRole("admin"));
    }

    @Test
    void testBoundaryRole() {
        assertFalse(UserValidation.validateRole("user"));
    }

    @Test
    void testValidateZero() {
        User user = new UserBuilder().build();
        assertThrows(ValidationException.class, () -> UserValidation.validate(user));
    }

    @Test
    void testValidateOne() {
        User user = new UserBuilder()
                .setUserName("Username")
                .setPassword("Password")
                .setRole("admin")
                .build();
        assertDoesNotThrow(() -> UserValidation.validate(user));
    }

    @Test
    void testValidateBoundary() {
        User user = new UserBuilder()
                .setUserName("User")
                .setPassword("Pass")
                .setRole("user")
                .build();
        assertThrows(ValidationException.class, () -> UserValidation.validate(user));
    }
}