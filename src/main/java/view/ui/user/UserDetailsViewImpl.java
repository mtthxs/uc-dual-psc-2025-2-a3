package view.ui.user;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;
import presenter.user.UserPresenter;

/**
 * JavaFX view for displaying the details of a single user.
 * Provides functionality to view user info and delete the user.
 */
public class UserDetailsViewImpl {

    /**
     * Button to delete the current user
     */
    @FXML
    private Button deleteButton;

    /**
     * Labels to display user details
     */
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label roleLabel;

    /**
     * Button to close the user details window
     */
    @FXML
    private Button closeButton;

    /**
     * Currently displayed user
     */
    private User currentUser;

    /**
     * Presenter to handle business logic
     */
    private UserPresenter presenter;

    /**
     * Callback to refresh the user list after deletion
     */
    private Runnable refreshUsersCallback;

    /**
     * Initializes the view.
     * Sets up button actions for closing the window and deleting the user.
     */
    @FXML
    public void initialize() {
        // Initialize the presenter
        presenter = new UserPresenter(new repository.UserRepository());

        // Close window when close button is clicked
        closeButton.setOnAction(event ->
                ((Stage) closeButton.getScene().getWindow()).close()
        );

        // Delete user when delete button is clicked
        deleteButton.setOnAction(event -> {
            if (currentUser != null) {
                boolean deleted = presenter.deleteUser(currentUser.getId());
                if (deleted) {
                    // Refresh user list if callback is provided
                    if (refreshUsersCallback != null) {
                        refreshUsersCallback.run();
                    }
                    // Close the window after deletion
                    ((Stage) deleteButton.getScene().getWindow()).close();
                }
            }
        });
    }

    /**
     * Sets the current user data to display in the view.
     *
     * @param user         The User object whose details are displayed
     * @param refreshUsers Runnable callback to refresh the user list after actions
     */
    public void setUserData(User user, Runnable refreshUsers) {
        this.currentUser = user;
        this.refreshUsersCallback = refreshUsers;

        // Populate labels with user info
        fullNameLabel.setText(user.getName());
        emailLabel.setText(user.getEmail());
        roleLabel.setText(user.getRole().name());
    }
}
