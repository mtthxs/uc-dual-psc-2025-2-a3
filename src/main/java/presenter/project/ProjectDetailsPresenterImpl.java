package presenter.project;

import model.Project;
import view.interfaces.project.ProjectDetailsPresenter;
import view.ui.project.ProjectDetailsViewImpl;

/**
 * Implementation of the ProjectDetailsPresenter interface.
 * <p>
 * Handles the business logic for displaying project details in the MVP pattern.
 * Responsible for passing a Project object to the view to populate the UI.
 */
public record ProjectDetailsPresenterImpl(
        ProjectDetailsViewImpl view // Reference to the concrete view implementation
) implements ProjectDetailsPresenter {

    /**
     * Loads the details of a project into the view.
     *
     * @param project The project whose details should be displayed
     */
    @Override
    public void loadProjectDetails(Project project) {
        // Pass the project data to the view to update the UI
        view.setProjectData(project);
    }
}
