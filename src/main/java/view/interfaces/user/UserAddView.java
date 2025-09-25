package view.interfaces.user;

import enums.UserRole;

import java.util.List;

/**
 * Interface representing the view for adding a new user.
 */
public interface UserAddView {

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
     * Sets the available role options in the view.
     *
     * @param roles list of UserRole options
     */
    void setRoleOptions(List<UserRole> roles);
}
