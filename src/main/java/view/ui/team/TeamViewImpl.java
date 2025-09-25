package view.ui.team;

import factory.team.TeamAddScreenFactory;
import factory.team.TeamDetailsScreenFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Team;
import presenter.team.TeamPresenter;
import repository.TeamRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for displaying the list of teams.
 * Allows adding new teams and viewing team details.
 */
public class TeamViewImpl implements Initializable {

    /**
     * Button to create a new team
     */
    @FXML
    private Button newTeamButton;

    /**
     * GridPane that holds all the team cards
     */
    @FXML
    private GridPane teamGrid;

    /**
     * Presenter handling team-related logic
     */
    private TeamPresenter presenter;

    /**
     * Initializes the view.
     * Sets up the new team button and loads existing teams.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        presenter = new TeamPresenter(new TeamRepository());

        // Open the team creation screen and refresh the grid
        newTeamButton.setOnAction(event -> {
            TeamAddScreenFactory.showTeamAddScreen();
            refreshTeams();
        });

        refreshTeams();
    }

    /**
     * Refreshes the team grid by fetching all teams from the presenter.
     */
    private void refreshTeams() {
        List<Team> teams = presenter.getAllTeams();
        populateTeamGrid(teams);
    }

    /**
     * Populates the GridPane with team cards.
     * If the list is empty, shows a placeholder message.
     *
     * @param teams List of teams to display
     */
    private void populateTeamGrid(List<Team> teams) {
        teamGrid.getChildren().clear();

        if (teams == null || teams.isEmpty()) {
            Label emptyLabel = new Label("No teams available");
            emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            teamGrid.add(emptyLabel, 0, 0);
            return;
        }

        int col = 0, row = 0;
        for (Team team : teams) {
            StackPane card = createTeamCard(team);
            GridPane.setColumnIndex(card, col);
            GridPane.setRowIndex(card, row);
            teamGrid.getChildren().add(card);

            col++;
            if (col >= 3) { // Max 3 cards per row
                col = 0;
                row++;
            }
        }
    }

    /**
     * Creates a visual card for a team.
     *
     * @param team Team to display
     * @return StackPane representing the team card
     */
    private StackPane createTeamCard(Team team) {
        StackPane card = new StackPane();
        card.setPrefSize(220, 140);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10;" +
                "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 15;");

        // Team name label
        Label nameLabel = new Label(team.getName());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Team description label
        Label descLabel = new Label(team.getDescription() != null ? team.getDescription() : "-");
        descLabel.setStyle("-fx-text-fill: gray;");

        // Button to view team details
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setMaxWidth(Double.MAX_VALUE);
        viewDetailsButton.setOnAction(e -> TeamDetailsScreenFactory.showTeamDetails(team));

        vbox.getChildren().addAll(nameLabel, descLabel, viewDetailsButton);
        card.getChildren().add(vbox);

        return card;
    }
}
