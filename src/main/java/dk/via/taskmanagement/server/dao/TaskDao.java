package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.Task;
import dk.via.taskmanagement.model.Workspace;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TaskDao {
    Task createTask(Task task) throws SQLException;

    Task updateTask(Task task) throws SQLException;

    Task deleteTask(Task task) throws SQLException;

    Task updateTaskState(Task task) throws SQLException;

    ArrayList<Task> getTasksForWorkspace(Workspace workspace) throws SQLException;
}
