package dk.via.taskmanagement.model.builders;

import dk.via.taskmanagement.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskBuilderTest {

    @Test
    void testZero() {
        TaskBuilder builder = new TaskBuilder();
        Task task = builder.build();
        assertNull(task.getId());
        assertNull(task.getName());
        assertNull(task.getDescription());
        assertNull(task.getPriority());
        assertNull(task.getState());
        assertNull(task.getDeadline());
        assertNull(task.getWorkspace());
        assertTrue(task.getUsers().isEmpty());
    }

    @Test
    void testOne() {
        TaskBuilder builder = new TaskBuilder();
        builder.setId(1)
                .setName("Task")
                .setDescription("Description")
                .setPriority(Task.Priority.HIGH)
                .setState(new NotStarted())
                .setDeadline(LocalDate.now())
                .setWorkspace(new Workspace("Workspace"))
                .setUsers(new ArrayList<>());
        Task task = builder.build();
        assertEquals("[HIGH] Task", task.toString());
        assertEquals(1, task.getId());
        assertEquals("Task", task.getName());
        assertEquals("Description", task.getDescription());
        assertEquals(Task.Priority.HIGH, task.getPriority());
        assertSame(NotStarted.class, task.getState().getClass());
        assertNotNull(task.getDeadline());
        assertEquals("Workspace", task.getWorkspace().getName());
        assertTrue(task.getUsers().isEmpty());
    }
}