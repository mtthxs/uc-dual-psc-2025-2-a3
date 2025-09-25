package factory.team;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Team;
import view.ui.team.TeamDetailsViewImpl;

import java.io.IOException;

// Factory class responsible for creating and displaying the "Team Details" screen
public class TeamDetailsScreenFactory {

    // Method to show the "Team Details" screen for a given team
    public static void showTeamDetails(Team team) {
        try {
            // Load the FXML layout for the "Team Details" screen
            FXMLLoader loader = new FXMLLoader(TeamDetailsScreenFactory.class.getResource("/view/layouts/team/TeamDetailsView.fxml"));
            VBox detailsView = loader.load();

            // Get the controller associated with the FXML
            TeamDetailsViewImpl controller = loader.getController();

            // Pass the team data to the controller so it can populate the view
            controller.setTeamData(team);

            // Create a new stage (window) for the "Team Details" screen
            Stage stage = new Stage();
            stage.setTitle("Team Details"); // Set window title
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
