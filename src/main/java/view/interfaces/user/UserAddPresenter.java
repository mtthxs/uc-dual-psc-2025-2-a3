package view.interfaces.user;

import enums.UserRole;

import java.util.List;

/**
 * Interface representing the presenter for adding a new user.
 */
public interface UserAddPresenter {

    /**
     * Handles the save action when adding a new user.
     *
     * @param fullName the user's full name
     * @param cpf      the user's CPF
     * @param email    the user's email
     * @param login    the username/login
     * @param password the user's password
     * @param role     the role assigned to the user
     */
    void onSaveClicked(String fullName, String cpf, String email, String login, String password, UserRole role);

    /**
     * Returns a list of all available user roles.
     *
     * @return list of UserRole
     */
    List<UserRole> getAllRoles();
}
