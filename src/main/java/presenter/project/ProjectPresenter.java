package presenter.project;

import model.Project;
import repository.ProjectRepository;

import java.util.List;

/**
 * Presenter class for managing projects.
 * <p>
 * Acts as a middle layer between the UI (view) and the data repository.
 * Handles CRUD operations (Create, Read, Update, Delete) for Project objects.
 */
public record ProjectPresenter(ProjectRepository projectRepository) {

    /**
     * Retrieves all projects from the repository.
     *
     * @return List of all projects
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Adds a new project to the repository.
     *
     * @param project Project object to add
     */
    public void addProject(Project project) {
        projectRepository.addProject(project);
    }

    /**
     * Updates an existing project in the repository.
     *
     * @param project Project object with updated data
     */
    public void updateProject(Project project) {
        projectRepository.updateProject(project);
    }

    /**
     * Deletes a project by its ID.
     * First checks if the project exists in the repository.
     *
     * @param projectId ID of the project to delete
     */
    public void deleteProject(int projectId) {
        Project project = projectRepository.findById(projectId);
        if (project != null) {
            projectRepository.removeProject(project);
        }
    }

    /**
     * Finds and returns a project by its ID.
     *
     * @param projectId ID of the project to find
     * @return Project object if found, otherwise null
     */
    public Project getProjectById(int projectId) {
        return projectRepository.findById(projectId);
    }
}
