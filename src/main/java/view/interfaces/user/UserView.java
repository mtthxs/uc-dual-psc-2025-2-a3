package view.interfaces.user;

import model.User;

import java.util.List;

/**
 * Interface representing the view for displaying users.
 */
public interface UserView {

    /**
     * Displays a list of users in the view.
     *
     * @param users the list of users to display
     */
    void showUsers(List<User> users);

    /**
     * Displays detailed information about a single user.
     *
     * @param user the user whose details are to be displayed
     */
    void showUserDetails(User user);

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display
     */
    void showError(String message);

    /**
     * Displays a success message to the user.
     *
     * @param message the success message to display
     */
    void showSuccess(String message);
}
