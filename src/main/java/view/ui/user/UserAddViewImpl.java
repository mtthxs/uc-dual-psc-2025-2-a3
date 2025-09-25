package view.ui.user;

import enums.UserRole;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import view.interfaces.user.UserAddPresenter;
import view.interfaces.user.UserAddView;

/**
 * JavaFX view for adding a new user.
 * Handles user input, form clearing, and displaying success/error messages.
 */
public class UserAddViewImpl implements UserAddView {

    /**
     * Text fields for user information
     */
    @FXML
    private TextField fullNameField, cpfField, emailField, loginField;

    /**
     * Password input field
     */
    @FXML
    private PasswordField passwordField;

    /**
     * ComboBox for selecting user role
     */
    @FXML
    private ComboBox<UserRole> roleComboBox;

    /**
     * Buttons to save or cancel
     */
    @FXML
    private Button saveButton, cancelButton;

    /**
     * Reference to the stage/window
     */
    private Stage stage;

    /**
     * Presenter handling business logic
     */
    private UserAddPresenter presenter;

    /**
     * Sets the presenter
     */
    public void setPresenter(UserAddPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Sets the stage for the view
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the view.
     * Populates the role ComboBox and sets up button actions.
     */
    public void initView() {
        roleComboBox.setItems(FXCollections.observableArrayList(presenter.getAllRoles()));

        // Clear form when cancel button is clicked
        cancelButton.setOnAction(e -> clearForm());

        // Save user when save button is clicked
        saveButton.setOnAction(e -> presenter.onSaveClicked(
                fullNameField.getText(),
                cpfField.getText(),
                emailField.getText(),
                loginField.getText(),
                passwordField.getText(),
                roleComboBox.getValue()
        ));

        // Close window when cancel button is clicked
        cancelButton.setOnAction(e -> closeWindow());
    }

    /**
     * Closes the current window
     */
    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Shows a success message and closes the window
     *
     * @param message Message to display
     */
    @Override
    public void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
        closeWindow();
    }

    /**
     * Shows an error message
     *
     * @param message Message to display
     */
    @Override
    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    /**
     * Clears all input fields in the form
     */
    @Override
    public void clearForm() {
        fullNameField.clear();
        cpfField.clear();
        emailField.clear();
        loginField.clear();
        passwordField.clear();
        roleComboBox.getSelectionModel().clearSelection();
    }

    /**
     * Sets the list of roles available in the ComboBox
     *
     * @param roles List of UserRole
     */
    @Override
    public void setRoleOptions(java.util.List<UserRole> roles) {
        roleComboBox.setItems(FXCollections.observableArrayList(roles));
    }
}
