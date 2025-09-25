package enums;

// Enum representing the different roles a user can have in the system
public enum UserRole {
    ADMINISTRATOR,   // User has full system access and can manage all settings and users
    MANAGER,         // User can manage projects, teams, and resources but has limited system access
    COLLABORATOR     // User can work on assigned tasks and projects but cannot manage system settings
}
