package view.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.Logger;
import view.interfaces.MenuSelectionHandler;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * JavaFX view for the main dashboard.
 * Handles menu navigation and loads the corresponding FXML views into the content area.
 */
public class DashboardViewImpl implements Initializable, MenuSelectionHandler {

    /**
     * Container holding the menu items (VBox)
     */
    @FXML
    public VBox menuContainer;

    /**
     * StackPane where content views are loaded dynamically
     */
    @FXML
    private StackPane contentArea;

    /**
     * List of HBox menu items extracted from menuContainer
     */
    private List<HBox> menuItems;

    /**
     * Map linking menu IDs to their corresponding FXML file paths
     */
    private final Map<String, String> menuToFxml = Map.of(
            "dashboard", "/view/layouts/HomeView.fxml",
            "projects", "/view/layouts/project/ProjectView.fxml",
            "teams", "/view/layouts/team/TeamView.fxml",
            "users", "/view/layouts/user/UserView.fxml"
    );

    /**
     * Initializes the dashboard view.
     * Extracts menu items and loads the default "HomeView".
     *
     * @param url            The location used to resolve relative paths
     * @param resourceBundle The resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Extract HBox nodes as menu items
        menuItems = menuContainer.getChildren().stream()
                .filter(node -> node instanceof HBox)
                .map(node -> (HBox) node)
                .collect(Collectors.toList());

        // Load default home view
        try {
            loadView("/view/layouts/HomeView.fxml", (HBox) menuItems.get(0));
        } catch (IOException ex) {
            Logger.error("Failed to load HomeView: " + ex.getMessage());
        }
    }

    /**
     * Handles click events on menu items.
     * Loads the corresponding view into the content area.
     *
     * @param event MouseEvent triggered by clicking a menu item
     * @throws IOException if FXML cannot be loaded
     */
    @FXML
    private void loadViewFromMenu(javafx.scene.input.MouseEvent event) throws IOException {
        HBox clickedMenu = (HBox) event.getSource();
        String menuId = (String) clickedMenu.getUserData();

        String fxmlPath = menuToFxml.get(menuId);

        if (fxmlPath != null) {
            loadView(fxmlPath, clickedMenu);
        } else {
            Logger.error("Failed to load view for menu: " + menuId);
        }
    }

    /**
     * Sets the active menu visually by updating CSS styles.
     *
     * @param activeMenu The menu item to mark as active
     */
    private void setActiveMenu(HBox activeMenu) {
        menuItems.forEach(item -> item.getStyleClass().setAll("hover-menu"));
        activeMenu.getStyleClass().setAll("select-menu");
    }

    /**
     * Loads a view from FXML and sets it in the content area.
     * Marks the corresponding menu as active.
     *
     * @param fxmlPath   Path to the FXML file
     * @param activeMenu The menu item associated with the view
     * @throws IOException if FXML cannot be loaded
     */
    private void loadView(String fxmlPath, HBox activeMenu) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlPath)));
        Parent fxml = loader.load();

        // If loading HomeView, pass contentArea and MenuSelectionHandler
        Object controller = loader.getController();
        if (controller instanceof HomeViewImpl) {
            ((HomeViewImpl) controller).setContentArea(contentArea, this);
        }

        contentArea.getChildren().setAll(fxml);
        setActiveMenu(activeMenu);
    }

    /**
     * Programmatically selects a menu item by ID and updates the active style.
     *
     * @param menuId The ID of the menu to select
     */
    @Override
    public void selectMenu(String menuId) {
        for (HBox menuItem : menuItems) {
            String id = (String) menuItem.getUserData();
            if (id.equals(menuId)) {
                setActiveMenu(menuItem);
                break;
            }
        }
    }
}
