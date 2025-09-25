package factory.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presenter.project.ProjectAddPresenterImpl;
import repository.ProjectRepository;
import repository.UserRepository;
import view.interfaces.project.ProjectAddPresenter;
import view.ui.project.ProjectAddViewImpl;

import java.io.IOException;

// Factory class responsible for creating and displaying the "Add Project" screen
public class ProjectAddScreenFactory {

    // Repositories used to manage project and user data
    private static final ProjectRepository projectRepository = new ProjectRepository();
    private static final UserRepository userRepository = new UserRepository();

    // Method to show the "Add Project" screen
    public static void showProjectAddScreen() {
        try {
            // Load the FXML layout for the "Add Project" screen
            FXMLLoader loader = new FXMLLoader(ProjectAddScreenFactory.class.getResource("/view/layouts/project/ProjectAddView.fxml"));
            VBox addView = loader.load();

            // Get the controller associated with the FXML
            ProjectAddViewImpl controller = loader.getController();

            // Create the presenter and link it with the controller and repositories
            ProjectAddPresenter presenter = new ProjectAddPresenterImpl(controller, projectRepository, userRepository);

            // Set the presenter in the controller and initialize the view
            controller.setPresenter(presenter);
            controller.initView();

            // Create a new stage (window) for the "Add Project" screen
            Stage stage = new Stage();
            stage.setTitle("Add Project"); // Set window title
            stage.setScene(new Scene(addView)); // Set scene with the loaded view
            stage.initModality(Modality.APPLICATION_MODAL); // Make the window modal (blocks other windows)
            controller.setStage(stage); // Pass the stage reference to the controller

            // Display the window and wait until it is closed
            stage.showAndWait();

        } catch (IOException e) {
            // Print stack trace if FXML loading fails
            e.printStackTrace();
        }
    }
}
