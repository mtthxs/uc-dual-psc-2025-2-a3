package factory.team;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import presenter.team.TeamAddPresenterImpl;
import repository.TeamRepository;
import view.interfaces.team.TeamAddPresenter;
import view.ui.team.TeamAddViewImpl;

import java.io.IOException;

// Factory class responsible for creating and displaying the "Add Team" screen
public class TeamAddScreenFactory {

    // Repository used to manage team data
    private static final TeamRepository teamRepository = new TeamRepository();

    // Method to show the "Add Team" screen
    public static void showTeamAddScreen() {
        try {
            // Load the FXML layout for the "Add Team" screen
            FXMLLoader loader = new FXMLLoader(TeamAddScreenFactory.class.getResource("/view/layouts/team/TeamAddView.fxml"));
            VBox addView = loader.load();

            // Get the controller associated with the FXML
            TeamAddViewImpl controller = loader.getController();

            // Create the presenter and link it with the controller and repository
            TeamAddPresenter presenter = new TeamAddPresenterImpl(controller, teamRepository);

            // Set the presenter in the controller and initialize the view
            controller.setPresenter(presenter);
            controller.initView();

            // Create a new stage (window) for the "Add Team" screen
            Stage stage = new Stage();
            stage.setTitle("Add Team"); // Set window title
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
