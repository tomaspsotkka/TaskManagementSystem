package dk.via.taskmanagement.model;

import java.io.Serializable;

public class User implements Serializable {
    private Workspace workspace;
    private Integer id;
    private String userName;
    private String password;
    private String role;

    public User(String userName, String password, String role, Workspace workspace) {
        this.id = null;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.workspace = workspace;
    }

    public User() {
        this.id = null;
        this.userName = null;
        this.password = null;
        this.role = null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    @Override
    public String toString() {
        return userName;
    }
}
