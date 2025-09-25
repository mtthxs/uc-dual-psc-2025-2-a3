package view.ui.user;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import model.User;
import util.Session;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * JavaFX view for displaying the logged-in user's header information.
 * Shows the user's avatar, name, role, and a welcome message.
 */
public class UserHeaderViewImpl implements Initializable {

    /**
     * ImageView for displaying the user's avatar
     */
    @FXML
    private ImageView avatarImage;

    /**
     * Placeholder circle shown while the avatar is loading
     */
    @FXML
    private Circle avatarPlaceholder;

    /**
     * Label displaying a welcome message
     */
    @FXML
    private Label welcomeLabel;

    /**
     * Label displaying the user's name
     */
    @FXML
    private Label nameLabel;

    /**
     * Label displaying the user's role
     */
    @FXML
    private Label roleLabel;

    /**
     * Initializes the view and loads the current user's data.
     *
     * @param url            URL used to resolve relative paths for the root object
     * @param resourceBundle Resource bundle for localization (can be null)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserData();
    }

    /**
     * Loads data for the currently logged-in user from the session.
     * Sets the welcome message, name, role, and avatar image.
     */
    private void loadUserData() {
        User user = Session.getLoggedUser();
        if (user != null) {
            // Set welcome message and user details
            welcomeLabel.setText("Welcome,");
            nameLabel.setText(user.getName());
            roleLabel.setText(user.getRole() != null ? user.getRole().name() : "N/A");

            // Generate a dynamic avatar image URL based on the user's name
            String avatarUrl = "https://ui-avatars.com/api/?name="
                    + user.getName().replace(" ", "")
                    + "&format=png&rounded=true&background=random";

            // Load the avatar image asynchronously
            Image avatar = new Image(avatarUrl, true);
            avatarImage.setImage(avatar);

            // Hide the placeholder circle once the image is loaded
            avatarImage.imageProperty().addListener((obs, oldImg, newImg) -> {
                if (newImg != null) {
                    avatarPlaceholder.setVisible(false);
                }
            });
        }
    }
}
