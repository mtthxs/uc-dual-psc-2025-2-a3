package view.interfaces;

/**
 * Interface representing the login view in the application.
 */
public interface LoginView {

    /**
     * Gets the login input entered by the user.
     *
     * @return the login/username
     */
    String getLogin();

    /**
     * Gets the password input entered by the user.
     *
     * @return the password
     */
    String getPassword();

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

    /**
     * Opens the main user view after a successful login.
     */
    void openUserView();
}
