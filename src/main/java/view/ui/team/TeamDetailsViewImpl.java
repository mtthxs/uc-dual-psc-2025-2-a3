package view.ui.team;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Project;
import model.Team;
import model.User;

/**
 * Controller for displaying team details.
 * Shows team name, description, members, and associated projects.
 */
public class TeamDetailsViewImpl {

    /**
     * Label to display the team's name
     */
    @FXML
    private Label teamNameLabel;

    /**
     * Label to display the team's description
     */
    @FXML
    private Label descriptionLabel;

    /**
     * List view showing the names of team members
     */
    @FXML
    private ListView<String> membersListView;

    /**
     * List view showing the names of projects associated with the team
     */
    @FXML
    private ListView<String> projectsListView;

    /**
     * Button to close the details window
     */
    @FXML
    private Button closeButton;

    /**
     * Initializes the controller.
     * Sets the close button to hide the window when clicked.
     */
    @FXML
    public void initialize() {
        closeButton.setOnAction(event -> closeButton.getScene().getWindow().hide());
    }

    /**
     * Populates the UI with the provided team's data.
     *
     * @param team Team object containing details to display
     */
    public void setTeamData(Team team) {
        // Set labels
        teamNameLabel.setText(team.getName());
        descriptionLabel.setText(team.getDescription());

        // Populate members list view
        membersListView.getItems().clear();
        if (team.getMembers() != null) {
            membersListView.getItems().addAll(
                    team.getMembers().stream()
                            .map(User::getName) // Convert User objects to their names
                            .toList()
            );
        }

        // Populate projects list view
        projectsListView.getItems().clear();
        if (team.getProjects() != null) {
            projectsListView.getItems().addAll(
                    team.getProjects().stream()
                            .map(Project::getName) // Convert Project objects to their names
                            .toList()
            );
        }
    }
}
