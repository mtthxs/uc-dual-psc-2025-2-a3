package view.interfaces.project;

import model.Project;

/**
 * Interface representing the presenter for displaying project details.
 */
public interface ProjectDetailsPresenter {

    /**
     * Loads and prepares the details of a given project for display.
     *
     * @param project the Project object whose details are to be displayed
     */
    void loadProjectDetails(Project project);
}
