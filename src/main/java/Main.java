import javafx.application.Application;
import javafx.stage.Stage;
import util.NavigationService;

/**
 * Main entry point for the Project Management System JavaFX application.
 * Launches the login screen and sets the global stylesheet.
 */
public class Main extends Application {

    /**
     * Service for handling navigation between screens
     */
    private final NavigationService navigationService = new NavigationService();

    /**
     * Starts the JavaFX application.
     * Sets the user-agent stylesheet and opens the login view.
     *
     * @param stage the primary stage provided by JavaFX
     */
    @Override
    public void start(Stage stage) {
        // Set the application-wide CSS theme
        Application.setUserAgentStylesheet("/css/primer-light.css");

        // Open the login view
        navigationService.open(
                "/view/layouts/LoginView.fxml",   // FXML file for login screen
                "Project Management System",      // Window title
                stage,                            // Primary stage to use
                false,                            // Do not reuse current stage
                false,                            // Stage not resizable
                false                             // Stage not modal
        );
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
