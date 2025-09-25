package presenter.user;

import model.User;
import repository.UserRepository;

import java.util.List;

/**
 * Presenter class for managing users.
 * <p>
 * Acts as a middle layer between the UI (view) and the UserRepository.
 * Handles retrieval and deletion of users.
 */
public record UserPresenter(UserRepository repo) {

    /**
     * Retrieves all users from the repository.
     * Useful for populating user lists in the UI.
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    /**
     * Deletes a user by their ID.
     *
     * @param userId ID of the user to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        return repo.deleteUser(userId);
    }
}
