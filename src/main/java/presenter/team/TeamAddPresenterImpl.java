package presenter.team;

import model.Project;
import model.Team;
import model.User;
import repository.TeamRepository;
import view.interfaces.team.TeamAddPresenter;
import view.interfaces.team.TeamAddView;

import java.util.List;

/**
 * Implementation of the TeamAddPresenter interface.
 * <p>
 * Handles the business logic for adding a new team in the MVP pattern.
 * Communicates between the TeamAddView (UI) and the TeamRepository.
 */
public record TeamAddPresenterImpl(
        TeamAddView view,            // Reference to the view interface for UI interaction
        TeamRepository teamRepository // Repository to manage team persistence
) implements TeamAddPresenter {

    /**
     * Called when the user clicks the "Save" button in the Add Team screen.
     * Validates the input, creates a Team object, and persists it using the repository.
     *
     * @param name        Name of the team
     * @param description Description of the team
     * @param members     List of team members
     * @param projects    List of projects associated with the team
     */
    @Override
    public void onSaveClicked(String name, String description, List<User> members, List<Project> projects) {
        // Validate team name
        if (name == null || name.isBlank()) {
            view.showErrorMessage("Team name is required.");
            return;
        }

        // Validate that at least one member is assigned to the team
        if (members == null || members.isEmpty()) {
            view.showErrorMessage("The team must have at least one member.");
            return;
        }

        // Create a new Team object and set its fields
        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setDescription(description);
        newTeam.setMembers(members);
        newTeam.setProjects(projects);

        // Save the new team using the repository
        teamRepository.addTeam(newTeam);

        // Notify the view and clear the form
        view.showSuccessMessage("Team created successfully!");
        view.clearForm();
    }

    /**
     * Returns a list of all users available to assign as team members.
     *
     * @return List of all users
     */
    @Override
    public List<User> getAllMembers() {
        return teamRepository.getAllUsers();
    }

    /**
     * Returns a list of all projects available to assign to the team.
     *
     * @return List of all projects
     */
    @Override
    public List<Project> getAllProjects() {
        return teamRepository.getAllProjects();
    }
}
