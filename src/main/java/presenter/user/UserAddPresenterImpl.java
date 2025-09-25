package presenter.user;

import enums.UserRole;
import model.User;
import repository.UserRepository;
import view.interfaces.user.UserAddPresenter;
import view.interfaces.user.UserAddView;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of the UserAddPresenter interface.
 * <p>
 * Handles the business logic for adding a new user in the MVP pattern.
 * Communicates between the UserAddView (UI) and the UserRepository.
 */
public record UserAddPresenterImpl(
        UserAddView view,           // Reference to the view interface for UI interaction
        UserRepository userRepository // Repository to manage user persistence
) implements UserAddPresenter {

    /**
     * Called when the user clicks the "Save" button in the Add User screen.
     * Validates the input, creates a User object, and persists it using the repository.
     *
     * @param fullName Full name of the user
     * @param cpf      CPF of the user
     * @param email    Email address of the user
     * @param login    Login username
     * @param password Login password
     * @param role     Role of the user (ADMINISTRATOR, MANAGER, COLLABORATOR)
     */
    @Override
    public void onSaveClicked(String fullName, String cpf, String email, String login, String password, UserRole role) {
        // Validate required fields
        if (fullName.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty() || role == null) {
            view.showErrorMessage("Please fill in all fields correctly.");
            return;
        }

        // Create a new User object and set its fields
        User newUser = new User(0, fullName, cpf, email, null, login, password);
        newUser.setRole(role);

        // Save the user using the repository
        boolean success = userRepository.saveUser(newUser);

        // Notify the view based on success or failure
        if (success) {
            view.showSuccessMessage("User saved successfully!");
            view.clearForm();
        } else {
            view.showErrorMessage("Error saving user.");
        }
    }

    /**
     * Returns a list of all possible user roles.
     *
     * @return List of UserRole enum values
     */
    @Override
    public List<UserRole> getAllRoles() {
        return Arrays.asList(UserRole.values());
    }
}
