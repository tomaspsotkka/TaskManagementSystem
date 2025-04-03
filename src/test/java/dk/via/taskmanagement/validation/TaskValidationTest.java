package dk.via.taskmanagement.validation;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.builders.TaskBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TaskValidationTest {

    @Test
    void testZeroTitle() {
        assertFalse(TaskValidation.isTitleValid(null));
    }

    @Test
    void testOneTitle() {
        assertTrue(TaskValidation.isTitleValid("Title"));
    }

    @Test
    void testBoundaryTitle() {
        assertFalse(TaskValidation.isTitleValid(""));
    }

    @Test
    void testZeroDescription() {
        assertFalse(TaskValidation.isDescriptionValid(null));
    }

    @Test
    void testOneDescription() {
        assertTrue(TaskValidation.isDescriptionValid("Description"));
    }

    @Test
    void testBoundaryDescription() {
        assertFalse(TaskValidation.isDescriptionValid(""));
    }

    @Test
    void testZeroDeadline() {
        assertFalse(TaskValidation.isDeadlineValid(null));
    }

    @Test
    void testOneDeadline() {
        assertTrue(TaskValidation.isDeadlineValid(LocalDate.now()));
    }

    @Test
    void testBoundaryDeadline() {
        assertTrue(TaskValidation.isDeadlineValid(LocalDate.now()));
    }


    @Test
    void testValidateZero() {
        Task task = new TaskBuilder().build();
        assertThrows(ValidationException.class, () -> TaskValidation.validate(task));
    }

    @Test
    void testValidateOne() {
        Task task = new TaskBuilder()
                .setName("Title")
                .setDescription("Description")
                .setDeadline(LocalDate.now())
                .build();
        assertDoesNotThrow(() -> TaskValidation.validate(task));
    }

    @Test
    void testValidateBoundary() {
        Task task = new TaskBuilder()
                .setName("")
                .setDescription("")
                .build();
        assertThrows(ValidationException.class, () -> TaskValidation.validate(task));
    }
}