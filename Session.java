package service;

import defaults.User;

public class Session {
    private static User currentUser;

    public static void setUser(User user) {
        Session.currentUser = user;
    }

    public static User getUser() {
        return currentUser;
    }

    public static int getUserId() {
        return currentUser != null ? currentUser.getId() : -1;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
} 
