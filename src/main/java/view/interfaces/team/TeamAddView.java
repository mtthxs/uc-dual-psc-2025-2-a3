package view.interfaces.team;

import model.Project;
import model.User;

import java.util.List;

/**
 * Interface representing the view for adding a new team.
 */
public interface TeamAddView {

    /**
     * Displays a success message to the user.
     *
     * @param message the message to display
     */
    void showSuccessMessage(String message);

    /**
     * Displays an error message to the user.
     *
     * @param message the message to display
     */
    void showErrorMessage(String message);

    /**
     * Clears all input fields in the form.
     */
    void clearForm();

    /**
     * Sets the available users that can be added as team members.
     *
     * @param members the list of users
     */
    void setMemberOptions(List<User> members);

    /**
     * Sets the available projects that can be associated with the team.
     *
     * @param projects the list of projects
     */
    void setProjectOptions(List<Project> projects);
}
