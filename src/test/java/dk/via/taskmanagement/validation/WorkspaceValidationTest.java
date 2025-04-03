package dk.via.taskmanagement.validation;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.model.builders.WorkspaceBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceValidationTest {

    @Test
    void testZeroName() {
        assertFalse(WorkspaceValidation.validateName(""));
    }

    @Test
    void testOneName() {
        assertTrue(WorkspaceValidation.validateName("Workspace"));
    }

    @Test
    void testValidateZero() {
        Workspace workspace = new WorkspaceBuilder().setName("").build();
        assertThrows(ValidationException.class, () -> WorkspaceValidation.validate(workspace));
    }

    @Test
    void testValidateOne() {
        Workspace workspace = new WorkspaceBuilder().setName("Workspace").build();
        assertDoesNotThrow(() -> WorkspaceValidation.validate(workspace));
    }
}