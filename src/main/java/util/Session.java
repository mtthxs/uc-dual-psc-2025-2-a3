package util;

import model.User;

/**
 * Utility class for managing the currently logged-in user session.
 * Provides methods to set, retrieve, and clear the logged-in user.
 */
public class Session {

    /**
     * The currently logged-in user.
     */
    private static User loggedUser;

    /**
     * Sets the currently logged-in user.
     *
     * @param user the user to set as logged-in
     */
    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    /**
     * Retrieves the currently logged-in user.
     *
     * @return the logged-in user, or null if no user is logged in
     */
    public static User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Clears the current session, removing any logged-in user.
     */
    public static void clear() {
        loggedUser = null;
    }
}
