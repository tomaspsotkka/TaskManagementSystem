package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO {

    User getByUsername(String username) throws SQLException;

    User create(User user) throws SQLException;

    ArrayList<User> getUsersWithoutWorkspace() throws SQLException;

    ArrayList<User> getUsersForWorkspace(Workspace workspace) throws SQLException;

    User getById(int userId) throws SQLException;
}
