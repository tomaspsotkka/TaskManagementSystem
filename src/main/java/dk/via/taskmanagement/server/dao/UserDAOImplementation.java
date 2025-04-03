package dk.via.taskmanagement.server.dao;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;
import dk.via.taskmanagement.model.builders.UserBuilder;
import org.postgresql.Driver;

import java.sql.*;
import java.util.ArrayList;

public class UserDAOImplementation implements UserDAO {
    private static UserDAOImplementation instance;

    private UserDAOImplementation() throws SQLException {
        DriverManager.registerDriver(new Driver());
    }

    public static UserDAOImplementation getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserDAOImplementation();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=task_management_system", "postgres", "");
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement getTask = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
            getTask.setString(1, username);

            ResultSet resultSet = getTask.executeQuery();

            if (resultSet.next()) {
                Workspace workspace = WorkspaceDAOImplementation.getInstance().getById(resultSet.getInt(5));

                UserBuilder userBuilder = new UserBuilder();
                return userBuilder.setId(resultSet.getInt(1))
                        .setUserName(resultSet.getString(2))
                        .setPassword(resultSet.getString(3))
                        .setRole(resultSet.getString(4))
                        .setWorkspace(workspace)
                        .build();
            } else {
                return null;
            }

        }
    }

    @Override
    public User create(User user) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement insertTask = connection.prepareStatement("INSERT INTO users (name, password, role) VALUES (?, ?, ?) returning user_id");
            insertTask.setString(1, user.getUserName());
            insertTask.setString(2, user.getPassword());
            insertTask.setString(3, user.getRole());

            ResultSet resultSet = insertTask.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
        }

        return user;
    }

    @Override
    public ArrayList<User> getUsersWithoutWorkspace() throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement getTasks = connection.prepareStatement("SELECT * FROM users WHERE workspace_id IS NULL");
            ResultSet resultSet = getTasks.executeQuery();

            ArrayList<User> users = new ArrayList<>();

            while (resultSet.next()) {
                UserBuilder userBuilder = new UserBuilder();

                users.add(userBuilder.setId(resultSet.getInt(1))
                        .setUserName(resultSet.getString(2))
                        .setPassword(resultSet.getString(3))
                        .setRole(resultSet.getString(4))
                        .build());
            }

            return users;
        }
    }

    public ArrayList<User> getUsersForWorkspace(Workspace workspace) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement getTasks = connection.prepareStatement("SELECT * FROM users WHERE workspace_id = ?");
            getTasks.setInt(1, workspace.getId());
            ResultSet resultSet = getTasks.executeQuery();

            ArrayList<User> users = new ArrayList<>();

            while (resultSet.next()) {
                UserBuilder userBuilder = new UserBuilder();
                users.add(userBuilder.setId(resultSet.getInt(1))
                        .setUserName(resultSet.getString(2))
                        .setPassword(resultSet.getString(3))
                        .setRole(resultSet.getString(4))
                        .setWorkspace(workspace)
                        .build());
            }

            return users;
        }
    }

    @Override
    public User getById(int userId) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement getTasks = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            getTasks.setInt(1, userId);

            ResultSet resultSet = getTasks.executeQuery();

            if (resultSet.next()) {
                Workspace workspace = WorkspaceDAOImplementation.getInstance().getById(resultSet.getInt(5));

                UserBuilder userBuilder = new UserBuilder();

                return userBuilder.setId(resultSet.getInt(1))
                        .setUserName(resultSet.getString(2))
                        .setPassword(resultSet.getString(3))
                        .setRole(resultSet.getString(4))
                        .setWorkspace(workspace)
                        .build();

            } else {
                return null;
            }
        }
    }
}
