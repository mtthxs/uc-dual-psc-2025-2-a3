# Project Management System (MVP)

Project management system developed in Java using the MVP (Model-View-Presenter) pattern with a JavaFX graphical interface. This project is a functional MVP and includes database scripts, as well as Docker configuration to facilitate execution.

# Technologies Used

* Java 17+
* JavaFX
* MySQL
* Docker / Docker Compose
* Maven (or Gradle, depending on project configuration)
* **MVP** architectural pattern

# Main Features

* Dashboard with counts of **users**, **projects**, and **teams**
* CRUD operations for **users**, **projects**, and **teams**
* Detailed views of projects and teams
* Login with user roles (**ADMINISTRATOR**, **MANAGER**, **COLLABORATOR**)
* Responsive interface using JavaFX and FXML

# Package Structure

The project is organized into the following packages for clarity and maintainability:

* **enums**: Contains enumeration classes such as `ProjectStatus` and `UserRole`.
* **factory**:
  - **project**: Includes `ProjectAddScreenFactory` and `ProjectDetailsScreenFactory` for creating project-related screens.
  - **team**: Includes `TeamAddScreenFactory` and `TeamDetailsScreenFactory` for team-related screens.
  - **user**: Includes `UserAddScreenFactory` and `UserDetailsScreenFactory` for user-related screens.
* **model**: Defines entity classes (`Project`, `Team`, `User`).
* **presenter**: Handles business logic intermediaries.
* **repository**: Manages data access with classes such as `ProjectRepository`, `TeamRepository`, and `UserRepository`.
* **util**: Utility classes including `DBConnection`, `Logger`, `NavigationService`, `Session`, and `Utils`.
* **view**:
  - **interfaces**: Defines view interfaces.

# Running the Project

## 1. Configure the Database

The project includes a MySQL dump (`sql/db/dump.sql`). You can load the dump manually or using Docker Compose.

## 2. Run with Docker

```bash
docker-compose up -d
```

This will start a MySQL container with the configured database.

### 3. Compile and Execute

If using Maven:

```bash
mvn clean install
mvn javafx:run
```

Alternatively, configure your IDE (IntelliJ/NetBeans/Eclipse) to run the main class:

```java
Main.java
```

## MVP (Model-View-Presenter)

* **Model:** Repositories that interact with the database (`UserRepository`, `ProjectRepository`, `TeamRepository`)
* **View:** Graphical interfaces (JavaFX FXML and controllers)
* **Presenter:** Intermediate class that processes business logic between the Model and View

## License

Study project / MVP, with no defined license.
