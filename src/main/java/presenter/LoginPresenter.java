package presenter;

import model.User;
import repository.UserRepository;
import util.Logger;
import util.Session;
import view.interfaces.LoginView;

/**
 * Presenter class responsible for handling user login logic.
 * <p>
 * Part of the MVP pattern:
 * - Communicates with the LoginView to get user input and show messages.
 * - Interacts with UserRepository to verify credentials.
 * - Uses Session utility to store logged-in user.
 * - Logs login events using Logger.
 */
public record LoginPresenter(LoginView view, UserRepository repo) {

    /**
     * Handles the login process when the user submits their credentials.
     * Validates input, checks password, updates session, and interacts with the view.
     */
    public void handleLogin() {
        // Retrieve login and password from the view
        String loginInput = view.getLogin();
        String passwordInput = view.getPassword();

        // Find the user in the repository by login
        User user = repo.findByLogin(loginInput);

        // Validate credentials
        if (user != null && repo.checkPassword(user, passwordInput)) {
            // Log successful login
            Logger.info("Login successful: " + loginInput);

            // Store the logged-in user in session
            Session.setLoggedUser(user);

            // Notify the view and navigate to the user interface
            view.showSuccess("Welcome, " + user.getName() + "!");
            view.openUserView();
        } else {
            // Log failed login attempt
            Logger.warn("Invalid login attempt: " + loginInput);

            // Notify the view about invalid credentials
            view.showError("Invalid username or password.");
        }
    }
}
