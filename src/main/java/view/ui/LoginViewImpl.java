package view.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presenter.LoginPresenter;
import repository.UserRepository;
import util.NavigationService;
import view.interfaces.LoginView;

/**
 * JavaFX view for the login screen.
 * Handles user input for login credentials and communicates with the LoginPresenter.
 */
public class LoginViewImpl implements LoginView {

    /**
     * Text field for entering the login/username
     */
    @FXML
    private TextField loginField;

    /**
     * Password field for entering the user's password
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Presenter that handles the login logic
     */
    private LoginPresenter presenter;

    /**
     * Service to navigate between screens
     */
    private final NavigationService navigationService = new NavigationService();

    /**
     * Initializes the view.
     * Creates the presenter with a UserRepository.
     */
    @FXML
    public void initialize() {
        UserRepository repo = new UserRepository();
        presenter = new LoginPresenter(this, repo);
    }

    /**
     * Handles the login button click by delegating to the presenter.
     */
    @FXML
    private void handleLogin() {
        presenter.handleLogin();
    }

    /**
     * Gets the login input from the user.
     *
     * @return login/username entered by the user
     */
    @Override
    public String getLogin() {
        return loginField.getText();
    }

    /**
     * Gets the password input from the user.
     *
     * @return password entered by the user
     */
    @Override
    public String getPassword() {
        return passwordField.getText();
    }

    /**
     * Shows an error alert with the given message.
     *
     * @param message the error message to display
     */
    @Override
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Login Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a success alert with the given message.
     *
     * @param message the success message to display
     */
    @Override
    public void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Login Successful");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Opens the main dashboard view after a successful login.
     * Closes the current login stage.
     */
    @Override
    public void openUserView() {
        Stage currentStage = (Stage) loginField.getScene().getWindow();
        navigationService.open(
                "/view/layouts/DashboardView.fxml", // FXML file for the dashboard
                "Dashboard",                        // Window title
                currentStage,                        // Current stage to reuse or close
                false,                               // Do not reuse current stage
                false,                               // Not resizable
                false                                // Not modal
        );
    }
}
