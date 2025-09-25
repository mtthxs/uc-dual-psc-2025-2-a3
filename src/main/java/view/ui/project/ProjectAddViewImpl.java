package view.ui.project;

import enums.ProjectStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import view.interfaces.project.ProjectAddPresenter;
import view.interfaces.project.ProjectAddView;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the ProjectAddView interface.
 * Handles the UI logic for adding a new project, including manager selection, project details, and status.
 */
public class ProjectAddViewImpl implements ProjectAddView {

    /**
     * ComboBox for selecting the project manager
     */
    @FXML
    private ComboBox<String> managerComboBox;

    /**
     * Text field for the project name
     */
    @FXML
    private TextField nameField;

    /**
     * Text area for the project description
     */
    @FXML
    private TextArea descriptionField;

    /**
     * Date picker for project start date
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * Date picker for project end date
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * ComboBox for selecting project status
     */
    @FXML
    private ComboBox<ProjectStatus> statusComboBox;

    /**
     * Button to save the project
     */
    @FXML
    private Button saveButton;

    /**
     * Button to cancel adding a project
     */
    @FXML
    private Button cancelButton;

    /**
     * Reference to the current stage
     */
    private Stage stage;

    /**
     * Presenter that handles business logic
     */
    private ProjectAddPresenter presenter;

    /**
     * List of available managers
     */
    private List<User> managers;

    /**
     * Initializes the view.
     * Sets the action for the cancel button.
     */
    @FXML
    public void initialize() {
        cancelButton.setOnAction(e -> closeWindow());
    }

    /**
     * Initializes the ComboBoxes and sets up the save button action.
     * Loads manager options from the presenter.
     */
    public void initView() {
        managers = presenter.getAllManagers();
        managerComboBox.setItems(FXCollections.observableArrayList(
                managers.stream().map(User::getLogin).toList()
        ));

        statusComboBox.setItems(FXCollections.observableArrayList(ProjectStatus.values()));

        saveButton.setOnAction(e -> {
            User selectedManager = getSelectedManager();
            presenter.onSaveClicked(
                    getProjectName(),
                    getProjectDescription(),
                    selectedManager,
                    getStartDate(),
                    getEndDate(),
                    getStatus()
            );
        });
    }

    /**
     * Sets the presenter instance
     */
    public void setPresenter(ProjectAddPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Sets the stage instance
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the current stage
     */
    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Returns the selected manager as a User object.
     *
     * @return selected User or null if none selected
     */
    private User getSelectedManager() {
        String login = managerComboBox.getValue();
        if (login == null) return null;
        return managers.stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    /**
     * Shows an error message in an alert
     */
    @Override
    public void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    /**
     * Shows a success message in an alert and closes the window
     */
    @Override
    public void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
        closeWindow();
    }

    /**
     * Clears all input fields and selections
     */
    @Override
    public void clearForm() {
        nameField.clear();
        descriptionField.clear();
        managerComboBox.getSelectionModel().clearSelection();
        statusComboBox.getSelectionModel().clearSelection();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
    }

    /**
     * Sets the manager options for the ComboBox.
     *
     * @param users list of available User objects
     */
    @Override
    public void setManagerOptions(List<User> users) {
        managers = users;
        managerComboBox.setItems(FXCollections.observableArrayList(
                users.stream().map(User::getLogin).toList()
        ));
    }

    /**
     * Returns the text entered in the project name field
     */
    @Override
    public String getProjectName() {
        return nameField.getText();
    }

    /**
     * Returns the text entered in the project description field
     */
    @Override
    public String getProjectDescription() {
        return descriptionField.getText();
    }

    /**
     * Returns the selected start date
     */
    @Override
    public LocalDate getStartDate() {
        return startDatePicker.getValue();
    }

    /**
     * Returns the selected end date
     */
    @Override
    public LocalDate getEndDate() {
        return endDatePicker.getValue();
    }

    /**
     * Returns the selected project status
     */
    @Override
    public ProjectStatus getStatus() {
        return statusComboBox.getValue();
    }

    /**
     * Returns the login of the selected manager.
     *
     * @return manager login or null if none selected
     */
    @Override
    public String getManager() {
        User user = getSelectedManager();
        return user != null ? user.getLogin() : null;
    }
}
