package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    /**
     * JDBC URL for connecting to the MySQL database.
     * Includes parameters to disable SSL, set server timezone, and allow public key retrieval.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/systemgp?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    /**
     * Username for the database connection
     */
    private static final String USER = "user";

    /**
     * Password for the database connection
     */
    private static final String PASSWORD = "password";

    /**
     * Static initializer block executed once when the class is loaded.
     * Loads the MySQL JDBC driver.
     * Throws RuntimeException if the driver class is not found.
     */
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Logger.info("MySQL JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            Logger.error("Failed to load MySQL JDBC Driver: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtains a new connection to the database using the configured URL, user, and password.
     *
     * @return Connection object to the MySQL database
     * @throws RuntimeException if a SQL exception occurs while connecting
     */
    public static Connection getConnection() {
        try {
            Logger.info("Attempting to connect to database: " + URL);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Logger.info("Database connection established successfully!");
            return conn;
        } catch (SQLException e) {
            Logger.error("Failed to connect to database: " + e.getMessage());
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
