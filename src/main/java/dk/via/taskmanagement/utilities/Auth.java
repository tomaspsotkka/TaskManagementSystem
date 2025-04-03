package dk.via.taskmanagement.utilities;

import dk.via.taskmanagement.model.User;

public class Auth {
    private static Auth instance;
    private User currentUser;

    private Auth() {
    }

    public static Auth getInstance() {
        if (instance == null) {
            instance = new Auth();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
