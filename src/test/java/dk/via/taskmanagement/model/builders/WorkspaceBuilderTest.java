package dk.via.taskmanagement.model.builders;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceBuilderTest {

    @Test
    void testZero() {
        WorkspaceBuilder builder = new WorkspaceBuilder();
        Workspace workspace = builder.build();
        assertNull(workspace.getId());
        assertNull(workspace.getName());
        assertTrue(workspace.getUsers().isEmpty());
    }

    @Test
    void testOne() {
        WorkspaceBuilder builder = new WorkspaceBuilder();
        builder.setId(1)
                .setName("Workspace")
                .setUsers(new ArrayList<>());
        Workspace workspace = builder.build();
        assertEquals(1, workspace.getId());
        assertEquals("Workspace", workspace.getName());
        assertTrue(workspace.getUsers().isEmpty());
    }

    @Test
    void testOneWithoutUsers() {
        WorkspaceBuilder builder = new WorkspaceBuilder();
        builder.setId(1)
                .setName("Workspace")
                .setUsers(null);
        Workspace workspace = builder.build();

        // test toString
        assertEquals(1, workspace.getId());
        assertEquals("Workspace", workspace.getName());
        assertTrue(workspace.getUsers().isEmpty());
    }
}
