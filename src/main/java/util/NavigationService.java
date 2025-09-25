package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utility class for navigating between JavaFX screens.
 * Supports opening FXML files in new stages or reusing existing ones,
 * with options for modality and resizability.
 */
public class NavigationService {

    /**
     * Opens a JavaFX screen from the given FXML path.
     *
     * @param fxmlPath     the path to the FXML file
     * @param title        the title of the window
     * @param currentStage the current stage (can be reused if reuseStage is true)
     * @param reuseStage   whether to reuse the current stage or create a new one
     * @param resizable    whether the stage can be resized
     * @param modal        whether the stage should be modal
     */
    public void open(String fxmlPath, String title, Stage currentStage,
                     boolean reuseStage, boolean resizable, boolean modal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage;
            if (reuseStage && currentStage != null) {
                stage = currentStage;
                stage.setScene(scene);
            } else {
                stage = new Stage();
                stage.setScene(scene);
            }

            stage.setTitle(title);
            stage.setResizable(resizable);

            if (modal) {
                stage.initModality(Modality.APPLICATION_MODAL);
            }

            stage.show();

            if (!reuseStage && currentStage != null) {
                currentStage.close();
            }

            loader.getController();

        } catch (IOException e) {
            Logger.error("Failed to open FXML: " + e.getMessage());
        }
    }
}
