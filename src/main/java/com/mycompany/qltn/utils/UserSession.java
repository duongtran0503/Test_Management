package com.mycompany.qltn.utils;


public class UserSession {
    private static int loggedInUserId = -1;
    private static String email;
    private static String role;

    public static void setUserSession(int userId, String userEmail, String userRole) {
        loggedInUserId = userId;
        email = userEmail;
        role = userRole;
    }

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    public static String getEmail() {
        return email;
    }

    public static String getRole() {
        return role;
    }

    public static void clearSession() {
        loggedInUserId = -1;
        email = null;
        role = null;
    }
}
