package view.ui.team;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Project;
import model.User;
import view.interfaces.team.TeamAddPresenter;
import view.interfaces.team.TeamAddView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the "Add Team" view.
 * Handles displaying team creation form, selecting members/projects, and saving the team.
 */
public class TeamAddViewImpl implements Initializable, TeamAddView {

    /**
     * Text field for the team name
     */
    @FXML
    private TextField teamNameField;

    /**
     * Text area for team description
     */
    @FXML
    private TextArea descriptionField;

    /**
     * List of selectable users to add as team members
     */
    @FXML
    private ListView<User> membersListView;

    /**
     * List of selectable projects to assign to the team
     */
    @FXML
    private ListView<Project> projectsListView;

    /**
     * Button to save the new team
     */
    @FXML
    private Button saveButton;

    /**
     * Button to cancel and close the form
     */
    @FXML
    private Button cancelButton;

    /**
     * Reference to the JavaFX stage
     */
    private Stage stage;

    /**
     * Presenter that handles business logic
     */
    private TeamAddPresenter presenter;

    /**
     * Initializes the controller after FXML is loaded.
     * Sets selection modes, list cell factories, and cancel button action.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Allow multiple selection in list views
        membersListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        projectsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Custom display for users in the members list
        membersListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty || user == null ? null : user.getName());
            }
        });

        // Custom display for projects in the projects list
        projectsListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Project project, boolean empty) {
                super.updateItem(project, empty);
                setText(empty || project == null ? null : project.getName());
            }
        });

        // Close window when cancel button is clicked
        cancelButton.setOnAction(e -> closeWindow());
    }

    /**
     * Initializes the view with data from the presenter.
     * Populates the members and projects lists and sets save button action.
     */
    public void initView() {
        membersListView.setItems(FXCollections.observableArrayList(presenter.getAllMembers()));
        projectsListView.setItems(FXCollections.observableArrayList(presenter.getAllProjects()));

        saveButton.setOnAction(e -> {
            presenter.onSaveClicked(
                    teamNameField.getText(),
                    descriptionField.getText(),
                    membersListView.getSelectionModel().getSelectedItems(),
                    projectsListView.getSelectionModel().getSelectedItems()
            );
        });
    }

    /**
     * Sets the presenter for this view.
     *
     * @param presenter TeamAddPresenter instance
     */
    public void setPresenter(TeamAddPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Sets the JavaFX stage for this view.
     *
     * @param stage Stage instance
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the current window
     */
    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Shows a success message and closes the window
     */
    @Override
    public void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
        closeWindow();
    }

    /**
     * Shows an error message
     */
    @Override
    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    /**
     * Clears all input fields and selections
     */
    @Override
    public void clearForm() {
        teamNameField.clear();
        descriptionField.clear();
        membersListView.getSelectionModel().clearSelection();
        projectsListView.getSelectionModel().clearSelection();
    }

    /**
     * Updates the members list options
     */
    @Override
    public void setMemberOptions(List<User> members) {
        membersListView.setItems(FXCollections.observableArrayList(members));
    }

    /**
     * Updates the projects list options
     */
    @Override
    public void setProjectOptions(List<Project> projects) {
        projectsListView.setItems(FXCollections.observableArrayList(projects));
    }
}
