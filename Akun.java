import java.util.List;

/**
 * @author Kelompok4
 * @version 1.0
 * @since 2023-12-01
 */
abstract public class Akun {

    // ANSI escape codes for coloring
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Saves the user account information to a text file.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public abstract void saveToTextFile(String username, String password);

    /**
     * Reads and returns a list of customer accounts from a data source.
     *
     * @return A list containing customer account information.
     */
    public abstract List<String> readCustomerAccounts();

    /**
     * Validates the sign-in credentials for a user.
     *
     * @param username The username entered for sign-in.
     * @param password The password entered for sign-in.
     * @return True if the provided credentials are valid, false otherwise.
     */
    public abstract boolean validateSignIn(String username, String password);

    /**
     * Validates the sign-up credentials for a new user.
     *
     * @param username The username entered for sign-up.
     * @param password The password entered for sign-up.
     * @return An integer code indicating the result of the validation:
     *         - 0: Sign-up successful.
     *         - 1: Username already exists.
     *         - 2: Other validation failure.
     */
    public abstract int validateSignUp(String username, String password);

    /**
     * Prints a success message for successful operations.
     *
     * @param message The success message to display.
     */
    protected void printSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + "Success: " + message + ANSI_RESET);
    }

    /**
     * Prints an error message for failed operations.
     *
     * @param message The error message to display.
     */
    protected void printErrorMessage(String message) {
        System.out.println(ANSI_RED + "Error: " + message + ANSI_RESET);
    }

    /**
     * Prints an informational message.
     *
     * @param message The informational message to display.
     */
    protected void printInfoMessage(String message) {
        System.out.println(ANSI_YELLOW + "Info: " + message + ANSI_RESET);
    }
}