package view.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import repository.ProjectRepository;
import repository.TeamRepository;
import repository.UserRepository;
import util.Logger;
import view.interfaces.MenuSelectionHandler;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX view for the home/dashboard screen.
 * Displays summary counts of users, projects, and teams, and provides quick navigation buttons.
 */
public class HomeViewImpl {

    /**
     * Buttons to navigate to respective views
     */
    @FXML
    private Button btnViewUsers, btnViewProjects, btnViewTeams;

    /**
     * Labels showing counts of users, projects, and teams
     */
    @FXML
    private Label lblUsersCount, lblProjectsCount, lblTeamsCount;

    /**
     * StackPane where content views are loaded dynamically
     */
    private StackPane contentArea;

    /**
     * Interface to communicate with the main menu for selection
     */
    private MenuSelectionHandler menuHandler;

    /**
     * Repositories to fetch data from database
     */
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private TeamRepository teamRepository;

    /**
     * Initializes the home view.
     * Disables focus traversal on buttons and initializes repositories.
     * Also updates the dashboard counts.
     */
    @FXML
    public void initialize() {
        // Disable focus traversal for cleaner UI
        btnViewUsers.setFocusTraversable(false);
        btnViewProjects.setFocusTraversable(false);
        btnViewTeams.setFocusTraversable(false);

        // Initialize repositories
        userRepository = new UserRepository();
        projectRepository = new ProjectRepository();
        teamRepository = new TeamRepository();

        // Populate initial counts
        updateDashboardCounts();
    }

    /**
     * Sets the content area StackPane and menu handler for navigation.
     *
     * @param contentArea StackPane where views are loaded
     * @param menuHandler MenuSelectionHandler to update menu state
     */
    public void setContentArea(StackPane contentArea, MenuSelectionHandler menuHandler) {
        this.contentArea = contentArea;
        this.menuHandler = menuHandler;
        setupButtonActions();
    }

    /**
     * Sets up the button click actions to navigate to respective views
     */
    private void setupButtonActions() {
        btnViewUsers.setOnAction(e -> loadPage("/view/layouts/user/UserView.fxml", "users"));
        btnViewProjects.setOnAction(e -> loadPage("/view/layouts/project/ProjectView.fxml", "projects"));
        btnViewTeams.setOnAction(e -> loadPage("/view/layouts/team/TeamView.fxml", "teams"));
    }

    /**
     * Loads an FXML page into the content area and updates the menu selection.
     *
     * @param fxmlPath Path to the FXML file
     * @param menuId   ID of the menu to select
     */
    private void loadPage(String fxmlPath, String menuId) {
        try {
            Parent fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            contentArea.getChildren().setAll(fxml);

            // Update menu selection if handler is set
            if (menuHandler != null) {
                menuHandler.selectMenu(menuId);
            }

        } catch (IOException ex) {
            Logger.error("Failed to load page: " + fxmlPath);
        }
    }

    /**
     * Updates the dashboard count labels with current values from the database.
     */
    public void updateDashboardCounts() {
        lblUsersCount.setText(String.valueOf(userRepository.countUsers()));
        lblProjectsCount.setText(String.valueOf(projectRepository.countProjects()));
        lblTeamsCount.setText(String.valueOf(teamRepository.countTeams()));
    }
}
