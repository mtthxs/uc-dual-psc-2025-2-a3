package presenter.project;

import enums.ProjectStatus;
import model.Project;
import model.User;
import repository.ProjectRepository;
import repository.UserRepository;
import view.interfaces.project.ProjectAddPresenter;
import view.interfaces.project.ProjectAddView;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the ProjectAddPresenter interface.
 * <p>
 * Handles the business logic for adding a new project in the MVP pattern.
 * Communicates between the ProjectAddView (UI) and the repositories.
 */
public record ProjectAddPresenterImpl(
        ProjectAddView view,             // Reference to the view interface for UI interaction
        ProjectRepository projectRepository, // Repository to manage project persistence
        UserRepository userRepository        // Repository to fetch users (managers)
) implements ProjectAddPresenter {

    /**
     * Called when the user clicks the "Save" button in the Add Project screen.
     * Validates the input, creates a Project object, and persists it using the repository.
     *
     * @param name        Name of the project
     * @param description Description of the project
     * @param manager     Manager assigned to the project
     * @param startDate   Project start date
     * @param endDate     Project planned end date
     * @param status      Project status (PLANNED, IN_PROGRESS, etc.)
     */
    @Override
    public void onSaveClicked(String name, String description, User manager,
                              LocalDate startDate, LocalDate endDate,
                              ProjectStatus status) {

        // Validate project name
        if (name == null || name.isBlank()) {
            view.showErrorMessage("Project name is required.");
            return;
        }

        // Validate project manager
        if (manager == null) {
            view.showErrorMessage("Project manager is required.");
            return;
        }

        // Create a new Project object and set its fields
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setManager(manager.getLogin()); // Used for display only

        // Convert LocalDate to String if provided
        if (startDate != null) {
            project.setStartDate(startDate.toString());
        }

        if (endDate != null) {
            project.setPlannedEndDate(endDate.toString());
        }

        // Set status; default to PLANNED if null
        project.setStatus(status != null ? status : ProjectStatus.PLANNED);

        // Save project using repository; passes manager ID for persistence
        projectRepository.addProjectWithManagerId(project, manager.getId());

        // Notify the view and clear the form
        view.showSuccessMessage("Project created successfully!");
        view.clearForm();
    }

    /**
     * Returns a list of all users who can be assigned as project managers.
     *
     * @return List of all users
     */
    @Override
    public List<User> getAllManagers() {
        return userRepository.findAll();
    }
}
