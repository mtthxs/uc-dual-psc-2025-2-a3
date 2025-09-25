package view.interfaces.team;

import model.Project;
import model.User;

import java.util.List;

/**
 * Interface representing the presenter for adding a new team.
 */
public interface TeamAddPresenter {

    /**
     * Handles the save action when creating a new team.
     *
     * @param name        the name of the team
     * @param description a brief description of the team
     * @param members     the list of users to be added as team members
     * @param projects    the list of projects associated with the team
     */
    void onSaveClicked(String name, String description, List<User> members, List<Project> projects);

    /**
     * Retrieves all available users that can be added as team members.
     *
     * @return a list of users
     */
    List<User> getAllMembers();

    /**
     * Retrieves all available projects that can be associated with the team.
     *
     * @return a list of projects
     */
    List<Project> getAllProjects();
}
