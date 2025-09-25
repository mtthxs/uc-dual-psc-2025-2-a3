package presenter.team;

import model.Project;
import model.Team;
import model.User;
import repository.TeamRepository;

import java.util.List;

/**
 * Presenter class for managing teams.
 * <p>
 * Acts as a middle layer between the UI (view) and the data repository.
 * Handles retrieval of teams, users, projects, and adding new teams.
 */
public record TeamPresenter(TeamRepository teamRepository) {

    /**
     * Retrieves all teams from the repository.
     *
     * @return List of all teams
     */
    public List<Team> getAllTeams() {
        return teamRepository.getAllTeams();
    }

    /**
     * Retrieves all users from the repository.
     * Useful for populating team member selections.
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return teamRepository.getAllUsers();
    }

    /**
     * Retrieves all projects from the repository.
     * Useful for assigning projects to teams.
     *
     * @return List of all projects
     */
    public List<Project> getAllProjects() {
        return teamRepository.getAllProjects();
    }

    /**
     * Adds a new team to the repository.
     *
     * @param team Team object to add
     */
    public void addTeam(Team team) {
        teamRepository.addTeam(team);
    }
}
