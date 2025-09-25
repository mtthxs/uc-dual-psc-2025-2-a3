package view.interfaces.project;

import enums.ProjectStatus;
import model.User;

import java.time.LocalDate;
import java.util.List;

/**
 * Presenter interface for handling the logic of adding a new project.
 */
public interface ProjectAddPresenter {

    /**
     * Called when the save button is clicked to create or update a project.
     *
     * @param name        the name of the project
     * @param description the description of the project
     * @param manager     the user assigned as the project manager
     * @param startDate   the start date of the project
     * @param endDate     the expected end date of the project
     * @param status      the current status of the project
     */
    void onSaveClicked(String name, String description, User manager,
                       LocalDate startDate, LocalDate endDate,
                       ProjectStatus status);

    /**
     * Retrieves a list of all users who can be assigned as project managers.
     *
     * @return list of users eligible to be managers
     */
    List<User> getAllManagers();
}
