package dk.via.taskmanagement.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskStateTest {

    @Test
    void testZero() {
        Task task = new Task();
        assertInstanceOf(NotStarted.class, task.getState());
    }

    @Test
    void testOneNotStarted() {
        Task task = new Task();
        task.setState(new NotStarted());
        assertEquals("NotStarted", task.getState().toString());

        assertInstanceOf(NotStarted.class, task.getState());
        task.startTask();
        assertInstanceOf(InProgress.class, task.getState());
        task.completeTask();
        assertInstanceOf(Completed.class, task.getState());
    }

    @Test
    void testOneNotStartedCaseTwo() {
        Task task = new Task();
        task.setState(new NotStarted());
        assertInstanceOf(NotStarted.class, task.getState());
        task.completeTask();
        assertInstanceOf(Completed.class, task.getState());
        task.completeTask();
        assertInstanceOf(Completed.class, task.getState());
    }


    @Test
    void testOneInProgress() {
        Task task = new Task();
        task.setState(new InProgress());
        assertEquals("InProgress", task.getState().toString());
        assertInstanceOf(InProgress.class, task.getState());
        task.startTask();
        assertInstanceOf(InProgress.class, task.getState());
        task.completeTask();
        assertInstanceOf(Completed.class, task.getState());
    }

    @Test
    void testOneCompleted() {
        Task task = new Task();
        task.setState(new Completed());
        assertInstanceOf(Completed.class, task.getState());
        assertEquals("Completed", task.getState().toString());
        task.startTask();
        assertInstanceOf(InProgress.class, task.getState());
        task.completeTask();
        assertInstanceOf(Completed.class, task.getState());
    }
}
