package dk.via.taskmanagement.validation;

import dk.via.taskmanagement.exceptions.ValidationException;
import dk.via.taskmanagement.model.Task;

import java.time.LocalDate;

public class TaskValidation {
    public static void validate(Task task) throws ValidationException {
        if (!isTitleValid(task.getName())) {
            throw new ValidationException("Title is not valid");
        }

        if (!isDescriptionValid(task.getDescription())) {
            throw new ValidationException("Description is not valid");
        }

        if (!isDeadlineValid(task.getDeadline())) {
            throw new ValidationException("Deadline is not valid");
        }
    }

    public static boolean isTitleValid(String title) {
        return title != null && !title.isEmpty();
    }

    public static boolean isDescriptionValid(String description) {
        return description != null && !description.isEmpty();
    }

    public static boolean isDeadlineValid(LocalDate deadline) {
        return deadline != null;
    }
}
