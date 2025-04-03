package dk.via.taskmanagement.validation;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.Workspace;

public class WorkspaceValidation {
    public static void validate(Workspace workspace) throws ValidationException {
        if (!validateName(workspace.getName())) {
            throw new ValidationException("Name is not valid");
        }
    }

    public static boolean validateName(String name) {
        return name != null && !name.isEmpty();
    }
}
