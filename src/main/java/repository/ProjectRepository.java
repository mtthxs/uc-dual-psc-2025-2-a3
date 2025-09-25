package repository;

import enums.ProjectStatus;
import model.Project;
import model.Team;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for performing CRUD operations on Project objects.
 * <p>
 * Interacts directly with the database using JDBC.
 */
public class ProjectRepository {

    /**
     * Counts the total number of projects in the database.
     *
     * @return total number of projects
     */
    public int countProjects() {
        String sql = "SELECT COUNT(*) AS total FROM projects";

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
     * Adds a new project to the database with a specified manager ID.
     * Sets the generated project ID on the Project object.
     *
     * @param project   Project object to add
     * @param managerId ID of the manager assigned to the project
     */
    public void addProjectWithManagerId(Project project, int managerId) {
        String sql = """
                INSERT INTO projects (name, description, start_date, expected_end_date, status, manager_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setObject(3, project.getStartDate() != null ? Date.valueOf(project.getStartDate()) : null);
            stmt.setObject(4, project.getPlannedEndDate() != null ? Date.valueOf(project.getPlannedEndDate()) : null);
            stmt.setString(5, project.getStatus() != null ? project.getStatus().name() : ProjectStatus.PLANNED.name());
            stmt.setInt(6, managerId);

            stmt.executeUpdate();

            // Capture the generated project ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all projects from the database along with their associated teams.
     *
     * @return List of all projects
     */
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        String sql = """
                SELECT p.id, p.name, p.description, p.start_date, p.expected_end_date, p.status,
                       u.full_name AS manager_name
                FROM projects p
                LEFT JOIN users u ON p.manager_id = u.id
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Project project = new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDate("start_date") != null ? rs.getDate("start_date").toString() : null,
                        rs.getDate("expected_end_date") != null ? rs.getDate("expected_end_date").toString() : null,
                        null,
                        rs.getString("status") != null ? ProjectStatus.valueOf(rs.getString("status")) : null,
                        rs.getString("manager_name"),
                        getTeamsForProject(rs.getInt("id")) // Load associated teams
                );
                projects.add(project);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    /**
     * Finds a project by its ID, including associated teams.
     *
     * @param id Project ID
     * @return Project object or null if not found
     */
    public Project findById(int id) {
        String sql = """
                SELECT p.id, p.name, p.description, p.start_date, p.expected_end_date, p.status,
                       u.full_name AS manager_name
                FROM projects p
                LEFT JOIN users u ON p.manager_id = u.id
                WHERE p.id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Project(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDate("start_date") != null ? rs.getDate("start_date").toString() : null,
                            rs.getDate("expected_end_date") != null ? rs.getDate("expected_end_date").toString() : null,
                            null,
                            rs.getString("status") != null ? ProjectStatus.valueOf(rs.getString("status")) : null,
                            rs.getString("manager_name"),
                            getTeamsForProject(rs.getInt("id"))
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves all teams associated with a specific project.
     *
     * @param projectId Project ID
     * @return List of associated teams
     */
    private List<Team> getTeamsForProject(int projectId) {
        List<Team> teams = new ArrayList<>();
        String sql = """
                SELECT t.id, t.name, t.description
                FROM project_teams pt
                INNER JOIN teams t ON pt.team_id = t.id
                WHERE pt.project_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Team team = new Team(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            new ArrayList<>(), // Members can be loaded separately if needed
                            new ArrayList<>()  // Projects can be loaded separately
                    );
                    teams.add(team);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    /**
     * Adds a new project without a manager ID.
     *
     * @param project Project object to add
     */
    public void addProject(Project project) {
        String sql = """
                INSERT INTO projects (name, description, start_date, expected_end_date, status, manager_id)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setObject(3, project.getStartDate() != null ? Date.valueOf(project.getStartDate()) : null);
            stmt.setObject(4, project.getPlannedEndDate() != null ? Date.valueOf(project.getPlannedEndDate()) : null);
            stmt.setString(5, project.getStatus() != null ? project.getStatus().name() : ProjectStatus.PLANNED.name());
            stmt.setNull(6, Types.INTEGER); // manager_id can be null

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    project.setId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing project in the database.
     *
     * @param project Project object with updated data
     */
    public void updateProject(Project project) {
        String sql = """
                UPDATE projects
                SET name = ?, description = ?, start_date = ?, expected_end_date = ?, status = ?, manager_id = ?
                WHERE id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, project.getName());
            stmt.setString(2, project.getDescription());
            stmt.setObject(3, project.getStartDate() != null ? Date.valueOf(project.getStartDate()) : null);
            stmt.setObject(4, project.getPlannedEndDate() != null ? Date.valueOf(project.getPlannedEndDate()) : null);
            stmt.setString(5, project.getStatus() != null ? project.getStatus().name() : ProjectStatus.PLANNED.name());
            stmt.setNull(6, Types.INTEGER); // manager_id can be null
            stmt.setInt(7, project.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a project from the database.
     *
     * @param project Project object to remove
     */
    public void removeProject(Project project) {
        String sql = "DELETE FROM projects WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, project.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
