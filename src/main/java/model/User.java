package model;

import enums.UserRole;

/**
 * Model class representing a User.
 * <p>
 * A User has an id, name, CPF, email, role, login, and password.
 * This class provides constructors, getters, setters, and a toString method
 * for display purposes.
 */
public class User {

    // Unique identifier for the user
    private int id;

    // Full name of the user
    private String name;

    // CPF (Cadastro de Pessoas Físicas) of the user, unique personal identifier in Brazil
    private String cpf;

    // Email address of the user
    private String email;

    // Role of the user (ADMINISTRATOR, MANAGER, COLLABORATOR)
    private UserRole role;

    // Login username for authentication
    private String login;

    // Password for authentication
    private String password;

    /**
     * Full constructor with all fields.
     *
     * @param id       Unique user identifier
     * @param name     User's full name
     * @param cpf      User's CPF
     * @param email    User's email address
     * @param role     User's role in the system
     * @param login    Login username
     * @param password Login password
     */
    public User(int id, String name, String cpf, String email, UserRole role, String login, String password) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.role = role;
        this.login = login;
        this.password = password;
    }

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Returns the user's name when the object is printed or used in a string context.
     * Useful for displaying in UI components like combo boxes or lists.
     *
     * @return User's full name
     */
    @Override
    public String toString() {
        return name;
    }

    // ===========================
    // Getters and Setters
    // ===========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
