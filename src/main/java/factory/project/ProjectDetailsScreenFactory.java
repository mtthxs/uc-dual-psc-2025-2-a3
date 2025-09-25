package factory.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Project;
import presenter.project.ProjectDetailsPresenterImpl;
import view.interfaces.project.ProjectDetailsPresenter;
import view.ui.project.ProjectDetailsViewImpl;

import java.io.IOException;

// Factory class responsible for creating and displaying the "Project Details" screen
public class ProjectDetailsScreenFactory {

    // Method to show the "Project Details" screen for a given project
    public static void showProjectDetails(Project project) {
        try {
            // Load the FXML layout for the "Project Details" screen
            FXMLLoader loader = new FXMLLoader(ProjectDetailsScreenFactory.class.getResource("/view/layouts/project/ProjectDetailsView.fxml"));
            VBox detailsView = loader.load();

            // Get the controller associated with the FXML
            ProjectDetailsViewImpl controller = loader.getController();

            // Create the presenter and link it with the controller
            ProjectDetailsPresenter presenter = new ProjectDetailsPresenterImpl(controller);

            // Load the project details into the view via the presenter
            presenter.loadProjectDetails(project);

            // Create a new stage (window) for the "Project Details" screen
            Stage stage = new Stage();
            stage.setTitle("Project Details"); // Set window title
            stage.setScene(new Scene(detailsView)); // Set scene with the loaded view
            stage.initModality(Modality.APPLICATION_MODAL); // Make the window modal (blocks other windows)

            // Display the window and wait until it is closed
            stage.showAndWait();

        } catch (IOException e) {
            // Print stack trace if FXML loading fails
            e.printStackTrace();
        }
    }
}
