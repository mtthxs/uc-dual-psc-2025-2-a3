package model;

import enums.ProjectStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class representing a Project.
 * <p>
 * A Project has an id, name, description, start date, planned and actual end dates,
 * a status, a manager, and a list of associated teams.
 * This class provides constructors, getters, setters, and helper methods to add/remove teams.
 */
public class Project {

    // Unique identifier for the project
    private int id;

    // Name of the project
    private String name;

    // Description of the project
    private String description;

    // Project start date (stored as String for simplicity)
    private String startDate;

    // Planned end date for the project
    private String plannedEndDate;

    // Actual end date of the project
    private String actualEndDate;

    // Current status of the project (PLANNED, IN_PROGRESS, COMPLETED, CANCELLED)
    private ProjectStatus status;

    // Name of the project manager
    private String manager;

    // List of teams associated with this project
    private List<Team> teams = new ArrayList<>();

    /**
     * Default constructor.
     * Initializes an empty teams list.
     */
    public Project() {
    }

    /**
     * Full constructor with all fields.
     * If the teams list is null, it is initialized as an empty list.
     *
     * @param id             Unique project identifier
     * @param name           Project name
     * @param description    Project description
     * @param startDate      Project start date
     * @param plannedEndDate Planned end date
     * @param actualEndDate  Actual end date
     * @param status         Project status
     * @param manager        Project manager
     * @param teams          List of associated teams
     */
    public Project(int id, String name, String description, String startDate, String plannedEndDate,
                   String actualEndDate, ProjectStatus status, String manager, List<Team> teams) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.plannedEndDate = plannedEndDate;
        this.actualEndDate = actualEndDate;
        this.status = status;
        this.manager = manager;
        if (teams != null) this.teams = teams; // Ensure teams list is not null
    }

    /**
     * Returns the project name for display purposes.
     * Useful in UI components like combo boxes or lists.
     *
     * @return Project name
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(String plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public String getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(String actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams != null ? teams : new ArrayList<>();
    }

    // ===========================
    // Helper Methods
    // ===========================

    /**
     * Adds a team to the project if it is not null and not already associated.
     *
     * @param team The team to add
     */
    public void addTeam(Team team) {
        if (team != null && !teams.contains(team)) {
            teams.add(team);
        }
    }

    /**
     * Removes a team from the project.
     *
     * @param team The team to remove
     */
    public void removeTeam(Team team) {
        teams.remove(team);
    }
}
