package dk.via.taskmanagement.model.builders;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserBuilderTest {

    @Test
    void testZero() {
        UserBuilder builder = new UserBuilder();
        User user = builder.build();
        assertNull(user.getId());
        assertNull(user.getUserName());
        assertNull(user.getPassword());
        assertNull(user.getRole());
        assertNull(user.getWorkspace());
    }

    @Test
    void testOne() {
        UserBuilder builder = new UserBuilder();
        builder.setId(1)
                .setUserName("Username")
                .setPassword("Password")
                .setRole("admin")
                .setWorkspace(new Workspace("Workspace"));
        User user = builder.build();
        assertEquals(1, user.getId());
        assertEquals("Username", user.toString());
        assertEquals("Username", user.getUserName());
        assertEquals("Password", user.getPassword());
        assertEquals("admin", user.getRole());
        assertEquals("Workspace", user.getWorkspace().getName());
    }
}