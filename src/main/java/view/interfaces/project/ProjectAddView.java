package view.interfaces.project;

import enums.ProjectStatus;
import model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface representing the view for adding a new project.
 * Provides methods to display messages, retrieve input, and set options.
 */
public interface ProjectAddView {

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display
     */
    void showErrorMessage(String message);

    /**
     * Displays a success message to the user.
     *
     * @param message the success message to display
     */
    void showSuccessMessage(String message);

    /**
     * Clears all input fields in the form.
     */
    void clearForm();

    /**
     * Sets the available options for selecting a project manager.
     *
     * @param users list of users who can be assigned as managers
     */
    void setManagerOptions(List<User> users);

    /**
     * Retrieves the project name entered by the user.
     *
     * @return the project name as a String
     */
    String getProjectName();

    /**
     * Retrieves the project description entered by the user.
     *
     * @return the project description as a String
     */
    String getProjectDescription();

    /**
     * Retrieves the start date selected by the user.
     *
     * @return the start date as a LocalDate
     */
    LocalDate getStartDate();

    /**
     * Retrieves the expected end date selected by the user.
     *
     * @return the end date as a LocalDate
     */
    LocalDate getEndDate();

    /**
     * Retrieves the project status selected by the user.
     *
     * @return the selected ProjectStatus
     */
    ProjectStatus getStatus();

    /**
     * Retrieves the login of the selected project manager.
     *
     * @return the manager's login as a String
     */
    String getManager();
}
