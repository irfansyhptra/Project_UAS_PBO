import java.io.IOException;

/**
 * Abstract class representing a generic driver in the marketplace system.
 * Contains common methods and inner interfaces for admin and customer functionalities.
 *
 * @author Kelompok 4
 * @version 1.0
 * @since 2023-12-01
 */
abstract public class Driver {

    // ANSI escape codes for coloring
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Clears the console screen.
     * Uses platform-specific commands for clearing the console.
     * Handles IOException and InterruptedException.
     */
    public static void bersihkanConsole() {
        try {
            Process process = new ProcessBuilder("cmd", "/c", "cls", "clear").inheritIO().start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(ANSI_RED + "Error clearing the console." + ANSI_RESET);
            e.printStackTrace();
        }
    }

    /**
     * Inner interface representing admin-specific functionalities.
     * AdminDriver interface provides methods for managing users, items, and transactions.
     */
    interface AdminDriver {
        void deleteUser();
        void addBarang();
        boolean showBarang();
        void editBarang();
        void deleteBarang();
        void mengaturTransaksi();
    }

    /**
     * Inner interface representing customer-specific functionalities.
     * CustomerDriver interface provides methods for managing items, cart, and transactions for customers.
     */
    interface CustomerDriver {
        void showBarang();
        void masukKeranjang();
        boolean showCart();
        void editCart();
        void deleteCart();
        void checkoutBarang();
        void checkInvoice();
    }

    /**
     * Abstract method to be implemented by subclasses.
     * Represents the main functionality to be executed by the driver.
     */
    public abstract void run();

    /**
     * Prints a welcome message with styling.
     */
    protected void printWelcomeMessage() {
        System.out.println(ANSI_CYAN + "===============================");
        System.out.println("    Welcome to the Marketplace  ");
        System.out.println("===============================" + ANSI_RESET);
    }

    /**
     * Prints a separator for better readability.
     */
    protected void printSeparator() {
        System.out.println(ANSI_YELLOW + "--------------------------------" + ANSI_RESET);
    }

    /**
     * Prints an error message.
     *
     * @param message The error message to display.
     */
    protected void printErrorMessage(String message) {
        System.out.println(ANSI_RED + "Error: " + message + ANSI_RESET);
    }

    /**
     * Prints a success message.
     *
     * @param message The success message to display.
     */
    protected void printSuccessMessage(String message) {
        System.out.println(ANSI_GREEN + "Success: " + message + ANSI_RESET);
    }
}