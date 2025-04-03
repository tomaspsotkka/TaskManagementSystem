package dk.via.taskmanagement.utilities;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.builders.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthTest {
    @BeforeEach
    void setUp() {
        Auth instance = Auth.getInstance();
        instance.setCurrentUser(null);
    }

    @Test
    void testZeroInstance() {
        Auth instance = Auth.getInstance();
        assertNotNull(instance);
    }

    @Test
    void testOneInstance() {
        Auth firstInstance = Auth.getInstance();
        Auth secondInstance = Auth.getInstance();
        assertSame(firstInstance, secondInstance);
    }

    @Test
    void testZeroCurrentUser() {
        Auth instance = Auth.getInstance();
        assertNull(instance.getCurrentUser());
    }

    @Test
    void testOneCurrentUser() {
        Auth instance = Auth.getInstance();
        User user = new UserBuilder()
                .setUserName("Username")
                .setPassword("Password")
                .setRole("admin")
                .build();
        instance.setCurrentUser(user);
        assertSame(user, instance.getCurrentUser());
    }
}