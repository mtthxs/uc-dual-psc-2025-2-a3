package factory.user;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;
import view.ui.user.UserDetailsViewImpl;

import java.io.IOException;

// Factory class responsible for creating and displaying the "User Details" screen
public class UserDetailsScreenFactory {

    // Method to show the "User Details" screen for a given user
    // The refreshUsers Runnable is used to update the user list after editing or other actions
    public static void showUserDetails(User user, Runnable refreshUsers) {
        try {
            // Load the FXML layout for the "User Details" screen
            FXMLLoader loader = new FXMLLoader(UserDetailsScreenFactory.class.getResource("/view/layouts/user/UserDetailsView.fxml"));
            VBox detailsView = loader.load();

            // Get the controller associated with the FXML
            UserDetailsViewImpl controller = loader.getController();

            // Pass the user data and refresh callback to the controller
            controller.setUserData(user, refreshUsers);

            // Create a new stage (window) for the "User Details" screen
            Stage stage = new Stage();
            stage.setTitle("User Details"); // Set window title
            stage.setScene(new Scene(detailsView)); // Set scene with the loaded view
            stage.initModality(Modality.APPLICATION_MODAL); // Make the window modal (blocks other windows)

            // Display the window and wait until it is closed
            stage.showAndWait();

        } catch (IOException e) {
            // Show an error alert if FXML loading fails
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to open user details.");
            alert.showAndWait();
        }
    }
}
