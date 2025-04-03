package dk.via.taskmanagement.model.builders;

import dk.via.taskmanagement.model.User;
import dk.via.taskmanagement.model.Workspace;

public class UserBuilder {
    private Integer id;
    private String userName;
    private String password;
    private String role;
    private Workspace workspace = null;

    public UserBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserBuilder setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setRole(String role) {
        this.role = role;
        return this;
    }

    public UserBuilder setWorkspace(Workspace workspace) {
        this.workspace = workspace;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(role);
        user.setId(id);
        user.setWorkspace(workspace);
        return user;
    }
}
