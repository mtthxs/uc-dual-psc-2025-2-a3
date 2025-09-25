package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class representing a Team.
 * <p>
 * A Team has an id, name, description, a list of members (users),
 * and a list of associated projects.
 * This class provides constructors, getters, setters, and methods
 * to add/remove members.
 */
public class Team {

    // Unique identifier for the team
    private int id;

    // Name of the team
    private String name;

    // Description or additional info about the team
    private String description;

    // List of users who are members of the team
    private List<User> members = new ArrayList<>();

    // List of projects that the team is assigned to
    private List<Project> projects = new ArrayList<>();

    /**
     * Default constructor.
     * Initializes empty lists for members and projects.
     */
    public Team() {
    }

    /**
     * Full constructor with all fields.
     * If members or projects lists are null, they are initialized as empty lists.
     *
     * @param id          Unique team identifier
     * @param name        Team name
     * @param description Team description
     * @param members     List of team members
     * @param projects    List of associated projects
     */
    public Team(int id, String name, String description, List<User> members, List<Project> projects) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.members = members != null ? members : new ArrayList<>(); // Ensure members list is not null
        this.projects = projects != null ? projects : new ArrayList<>(); // Ensure projects list is not null
    }

    /**
     * Returns the team name when the object is printed or used in a string context.
     * Useful for displaying in UI components like combo boxes or lists.
     *
     * @return Team name
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members != null ? members : new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects != null ? projects : new ArrayList<>();
    }

    // ===========================
    // Helper Methods
    // ===========================

    /**
     * Adds a user to the team if the user is not null and not already in the team.
     *
     * @param user The user to be added
     */
    public void addMember(User user) {
        if (user != null && !members.contains(user)) {
            members.add(user);
        }
    }

    /**
     * Removes a user from the team if they exist in the members list.
     *
     * @param user The user to be removed
     */
    public void removeMember(User user) {
        members.remove(user);
    }
}
