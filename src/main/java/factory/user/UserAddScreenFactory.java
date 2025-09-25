package factory.user;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presenter.user.UserAddPresenterImpl;
import repository.UserRepository;
import view.interfaces.user.UserAddPresenter;
import view.ui.user.UserAddViewImpl;

import java.io.IOException;

// Factory class responsible for creating and displaying the "Add User" screen
public class UserAddScreenFactory {

    // Repository used to manage user data
    private static final UserRepository userRepository = new UserRepository();

    // Method to show the "Add User" screen
    public static void showUserAddScreen() {
        try {
            // Load the FXML layout for the "Add User" screen
            FXMLLoader loader = new FXMLLoader(UserAddScreenFactory.class.getResource("/view/layouts/user/UserAddView.fxml"));
            VBox root = loader.load();

            // Get the controller associated with the FXML
            UserAddViewImpl controller = loader.getController();

            // Create the presenter and link it with the controller and repository
            UserAddPresenter presenter = new UserAddPresenterImpl(controller, userRepository);

            // Set the presenter in the controller and initialize the view
            controller.setPresenter(presenter);
            controller.initView();

            // Create a new stage (window) for the "Add User" screen
            Stage stage = new Stage();
            stage.setTitle("Add User"); // Set window title
            stage.setScene(new Scene(root)); // Set scene with the loaded view
            stage.initModality(Modality.APPLICATION_MODAL); // Make the window modal (blocks other windows)
            controller.setStage(stage); // Pass the stage reference to the controller

            // Display the window and wait until it is closed
            stage.showAndWait();

        } catch (IOException e) {
            // Show an error alert if FXML loading fails
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load Add User screen.");
            alert.showAndWait();
        }
    }
}
