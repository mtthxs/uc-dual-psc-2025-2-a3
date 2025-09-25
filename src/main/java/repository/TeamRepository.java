package repository;

import model.Project;
import model.Team;
import model.User;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for performing CRUD operations on Team objects.
 * <p>
 * Handles teams, their members (users), and associated projects.
 * Uses JDBC to interact with the database.
 */
public class TeamRepository {

    /**
     * Counts the total number of teams in the database.
     *
     * @return total number of teams
     */
    public int countTeams() {
        String sql = "SELECT COUNT(*) AS total FROM teams";

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
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        null,
                        rs.getString("login"),
                        rs.getString("password")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    /**
     * Retrieves all projects from the database.
     *
     * @return list of all projects
     */
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                projects.add(new Project(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("start_date"),
                        rs.getString("expected_end_date"),
                        rs.getString("status"),
                        null,
                        null,
                        new ArrayList<>()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    /**
     * Retrieves all teams from the database, including their members and projects.
     *
     * @return list of all teams
     */
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        String sql = "SELECT * FROM teams";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int teamId = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");

                // Load members and projects associated with the team
                List<User> members = getTeamMembers(teamId, conn);
                List<Project> projects = getTeamProjects(teamId, conn);

                teams.add(new Team(teamId, name, description, members, projects));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    /**
     * Retrieves all users that are members of a specific team.
     *
     * @param teamId Team ID
     * @param conn   Database connection
     * @return list of team members
     * @throws SQLException if a database error occurs
     */
    private List<User> getTeamMembers(int teamId, Connection conn) throws SQLException {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.* FROM users u " +
                "JOIN team_members tm ON u.id = tm.user_id " +
                "WHERE tm.team_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    members.add(new User(
                            rs.getInt("id"),
                            rs.getString("full_name"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            null,
                            rs.getString("login"),
                            rs.getString("password")
                    ));
                }
            }
        }
        return members;
    }

    /**
     * Retrieves all projects associated with a specific team.
     *
     * @param teamId Team ID
     * @param conn   Database connection
     * @return list of projects
     * @throws SQLException if a database error occurs
     */
    private List<Project> getTeamProjects(int teamId, Connection conn) throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.* FROM projects p " +
                "JOIN project_teams pt ON p.id = pt.project_id " +
                "WHERE pt.team_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projects.add(new Project(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("start_date"),
                            rs.getString("expected_end_date"),
                            rs.getString("status"),
                            null,
                            null,
                            new ArrayList<>()
                    ));
                }
            }
        }
        return projects;
    }

    /**
     * Adds a new team to the database, along with its members and projects.
     *
     * @param team Team object to add
     */
    public void addTeam(Team team) {
        String insertTeamSql = "INSERT INTO teams (name, description) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertTeamSql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, team.getName());
            stmt.setString(2, team.getDescription());
            stmt.executeUpdate();

            // Retrieve the generated team ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int teamId = generatedKeys.getInt(1);

                    // Insert team members
                    for (User user : team.getMembers()) {
                        try (PreparedStatement ps = conn.prepareStatement(
                                "INSERT INTO team_members (team_id, user_id) VALUES (?, ?)")) {
                            ps.setInt(1, teamId);
                            ps.setInt(2, user.getId());
                            ps.executeUpdate();
                        }
                    }

                    // Insert associated projects
                    for (Project project : team.getProjects()) {
                        try (PreparedStatement ps = conn.prepareStatement(
                                "INSERT INTO project_teams (team_id, project_id) VALUES (?, ?)")) {
                            ps.setInt(1, teamId);
                            ps.setInt(2, project.getId());
                            ps.executeUpdate();
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
