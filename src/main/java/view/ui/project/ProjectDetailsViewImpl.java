package view.ui.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import model.Project;
import model.Team;

/**
 * Controller class for the Project Details view.
 * Displays information about a selected project, including teams, manager, dates, status, and progress.
 */
public class ProjectDetailsViewImpl {

    /**
     * ListView displaying the teams associated with the project
     */
    @FXML
    private ListView<Team> teamsListView;

    /**
     * Label displaying the project name
     */
    @FXML
    private Label nameLabel;

    /**
     * Label displaying the project description
     */
    @FXML
    private Label descriptionLabel;

    /**
     * Label displaying the project start date
     */
    @FXML
    private Label startDateLabel;

    /**
     * Label displaying the project end date
     */
    @FXML
    private Label endDateLabel;

    /**
     * Label displaying the project status
     */
    @FXML
    private Label statusLabel;

    /**
     * Label displaying the project manager
     */
    @FXML
    private Label managerLabel;

    /**
     * ProgressBar showing project progress
     */
    @FXML
    private ProgressBar progressBar;

    /**
     * Button to close the project details window
     */
    @FXML
    private Button closeButton;

    /**
     * Initializes the view.
     * Sets up the close button action.
     */
    @FXML
    public void initialize() {
        closeButton.setOnAction(e -> closeWindow());
    }

    /**
     * Populates the view with the project's details.
     *
     * @param project the Project object containing the data to display
     */
    public void setProjectData(Project project) {
        nameLabel.setText(project.getName());
        descriptionLabel.setText(project.getDescription());
        startDateLabel.setText(project.getStartDate() != null ? project.getStartDate() : "-");
        endDateLabel.setText(project.getActualEndDate() != null ? project.getActualEndDate() : "-");
        statusLabel.setText(project.getStatus() != null ? project.getStatus().name() : "N/A");
        managerLabel.setText(project.getManager() != null ? project.getManager() : "N/A");

        // TODO: Replace hardcoded progress with actual calculation
        progressBar.setProgress(0.6);

        teamsListView.getItems().setAll(project.getTeams());
    }

    /**
     * Closes the current stage/window.
     */
    private void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
