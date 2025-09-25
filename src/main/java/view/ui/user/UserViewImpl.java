package view.ui.user;

import factory.user.UserAddScreenFactory;
import factory.user.UserDetailsScreenFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.User;
import presenter.user.UserPresenter;
import repository.UserRepository;

import java.util.List;

/**
 * JavaFX view for displaying a grid of users.
 * Each user is represented as a card showing name, email, role, and a "View Details" button.
 * Allows adding new users and refreshing the grid after changes.
 */
public class UserViewImpl {

    /**
     * GridPane that holds user cards
     */
    @FXML
    private GridPane userGrid;

    /**
     * Button to open the "Add User" screen
     */
    @FXML
    private Button newUserButton;

    /**
     * Presenter handling user-related logic
     */
    private UserPresenter presenter;

    /**
     * Initializes the view and sets up event handlers.
     * Loads the initial list of users into the grid.
     */
    @FXML
    public void initialize() {
        presenter = new UserPresenter(new UserRepository());

        // Open Add User screen and refresh grid after adding
        newUserButton.setOnAction(event -> {
            UserAddScreenFactory.showUserAddScreen();
            refreshUsers();
        });

        refreshUsers();
    }

    /**
     * Fetches the list of users from the presenter and populates the grid.
     */
    private void refreshUsers() {
        List<User> users = presenter.getAllUsers();
        populateUserGrid(users);
    }

    /**
     * Populates the user grid with user cards.
     * Arranges cards in a 3-column layout.
     *
     * @param users List of users to display
     */
    private void populateUserGrid(List<User> users) {
        userGrid.getChildren().clear();
        int col = 0, row = 0;

        for (User user : users) {
            StackPane card = createUserCard(user);
            GridPane.setColumnIndex(card, col);
            GridPane.setRowIndex(card, row);
            userGrid.getChildren().add(card);

            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Creates a visual card for a user, including name, email, role, and a "View Details" button.
     *
     * @param user The user to create a card for
     * @return StackPane representing the user card
     */
    private StackPane createUserCard(User user) {
        StackPane card = new StackPane();
        card.setPrefSize(220, 140);
        card.setStyle("-fx-background-color: white; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 15;");

        // User name
        Label nameLabel = new Label(user.getName());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // User email
        Label emailLabel = new Label(user.getEmail());
        emailLabel.setStyle("-fx-text-fill: gray;");

        // User role (default to COLLABORATOR if null)
        Label roleLabel = new Label(user.getRole() != null ? user.getRole().name() : "COLLABORATOR");
        roleLabel.setStyle("-fx-text-fill: darkgray; -fx-font-size: 12px;");

        // Button to open user details screen
        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.setMaxWidth(Double.MAX_VALUE);
        viewDetailsButton.setOnAction(e ->
                UserDetailsScreenFactory.showUserDetails(user, this::refreshUsers)
        );

        vbox.getChildren().addAll(nameLabel, emailLabel, roleLabel, viewDetailsButton);
        card.getChildren().add(vbox);

        return card;
    }
}
