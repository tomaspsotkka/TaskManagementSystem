package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.sql.SQLException;

public interface WorkspaceDAO {
    Workspace createWorkspace(Workspace workspace) throws SQLException;

    void addWorkSpaceUser(Workspace workspace, User newUser) throws SQLException;

    Workspace getByName(String name) throws SQLException;

    Workspace getById(int id) throws SQLException;
}
