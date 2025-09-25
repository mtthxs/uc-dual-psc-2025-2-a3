package repository;

import enums.UserRole;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import util.DBConnection;
import util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for performing CRUD operations on User objects.
 * Handles database interaction using JDBC and manages password hashing with BCrypt.
 */
public class UserRepository {

    /**
     * Counts the total number of users in the database.
     *
     * @return total number of users
     */
    public int countUsers() {
        String sql = "SELECT COUNT(*) AS total FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return list of all users
     */
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserRole role = UserRole.valueOf(rs.getString("role").toUpperCase());
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        role,
                        rs.getString("login"),
                        rs.getString("password")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Finds a user by their unique ID.
     *
     * @param id User ID
     * @return User object if found, otherwise null
     */
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserRole role = UserRole.valueOf(rs.getString("role").toUpperCase());
                    return new User(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            role,
                            rs.getString("login"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Finds a user by their login name.
     *
     * @param login Login string
     * @return User object if found, otherwise null
     */
    public User findByLogin(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    UserRole role = null;
                    try {
                        role = UserRole.valueOf(rs.getString("role").toUpperCase());
                    } catch (IllegalArgumentException e) {
                        Logger.error("Invalid role in DB for user " + login + ": " + rs.getString("role"));
                    }

                    return new User(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            role,
                            rs.getString("login"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            Logger.error("Database error while finding user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Checks whether the provided plain text password matches the stored hashed password.
     *
     * @param user          User object
     * @param plainPassword Plain text password
     * @return true if password matches, false otherwise
     */
    public boolean checkPassword(User user, String plainPassword) {
        if (user == null || plainPassword == null) return false;
        return BCrypt.checkpw(plainPassword, user.getPassword());
    }

    /**
     * Saves a new user in the database.
     * Hashes the password using BCrypt before saving.
     *
     * @param user User object to save
     * @return true if save was successful, false otherwise
     */
    public boolean saveUser(User user) {
        String sql = "INSERT INTO users (full_name, cpf, email, login, password, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getCpf());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getLogin());

            // Hash the password before saving
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
            stmt.setString(5, hashedPassword);

            // Default role is COLLABORATOR if null
            stmt.setString(6, user.getRole() != null ? user.getRole().name() : "COLLABORATOR");

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }

            // Retrieve generated user ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a user from the database by ID.
     *
     * @param userId User ID
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            int affected = stmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
