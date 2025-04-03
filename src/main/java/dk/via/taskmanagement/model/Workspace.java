package dk.via.taskmanagement.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Workspace implements Serializable {
    private final ArrayList<User> users = new ArrayList<>();
    private Integer id;
    private String name;

    public Workspace(String name) {
        this.id = null;
        this.name = name;
    }

    public Workspace() {
        this.id = null;
        this.name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
