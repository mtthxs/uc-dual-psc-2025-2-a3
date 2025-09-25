package view.ui.project;

import factory.project.ProjectAddScreenFactory;
import factory.project.ProjectDetailsScreenFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Project;
import presenter.project.ProjectPresenter;
import repository.ProjectRepository;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the Project view.
 * Handles displaying all projects in a grid and opening screens for adding or viewing project details.
 */
public class ProjectViewImpl implements Initializable {

    /**
     * Button to create a new project
     */
    @FXML
    private Button newProjectButton;

    /**
     * GridPane to hold project cards
     */
    @FXML
    private GridPane projectGrid;

    /**
     * Presenter to handle project-related logic
     */
    private ProjectPresenter presenter;

    /**
     * Initializes the controller after FXML is loaded.
     * Sets up presenter and button actions, and refreshes the project grid.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        presenter = new ProjectPresenter(new ProjectRepository());

        // Open the add project screen when the button is clicked
        newProjectButton.setOnAction(event -> {
            ProjectAddScreenFactory.showProjectAddScreen();
            refreshProjects();
        });

        refreshProjects();
    }

    /**
     * Fetches all projects from the presenter and populates the grid.
     */
    private void refreshProjects() {
        List<Project> projects = presenter.getAllProjects();
        populateProjectGrid(projects);
    }

    /**
     * Populates the project grid with cards for each project.
     * Shows a message if the list is empty.
     *
     * @param projects List of projects to display
     */
    private void populateProjectGrid(List<Project> projects) {
        projectGrid.getChildren().clear();

        if (projects == null || projects.isEmpty()) {
            Label emptyLabel = new Label("No projects available");
            emptyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
            projectGrid.add(emptyLabel, 0, 0);
            return;
        }

        int col = 0, row = 0;
        for (Project project : projects) {
            StackPane card = createProjectCard(project);
            projectGrid.add(card, col, row);

            col++;
            if (col >= 3) { // Move to next row after 3 columns
                col = 0;
                row++;
            }
        }
    }

    /**
     * Creates a card UI element for a single project.
     *
     * @param project Project object to display
     * @return StackPane representing the project card
     */
    private StackPane createProjectCard(Project project) {
        StackPane card = new StackPane();
        card.setPrefSize(220, 180);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        );

        VBox vbox = new VBox(5);
        vbox.setStyle("-fx-padding: 10;");

        Label nameLabel = new Label(project.getName());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label descLabel = new Label(project.getDescription() != null ? project.getDescription() : "");
        descLabel.setStyle("-fx-text-fill: gray;");

        Label statusLabel = new Label("Status: " +
                (project.getStatus() != null ? project.getStatus().name() : "N/A"));

        Label managerLabel = new Label("Manager: " +
                (project.getManager() != null ? project.getManager() : "N/A"));

        // TODO: Replace hardcoded progress with actual project progress
        ProgressBar progressBar = new ProgressBar(0.6);
        progressBar.setPrefWidth(200);

        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setMaxWidth(Double.MAX_VALUE);
        viewDetailsButton.setOnAction(e -> ProjectDetailsScreenFactory.showProjectDetails(project));

        vbox.getChildren().addAll(nameLabel, descLabel, statusLabel, managerLabel, progressBar, viewDetailsButton);
        card.getChildren().add(vbox);

        return card;
    }
}
