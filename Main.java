import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public Akun akunAdmin = new Admin();
    public Akun akunCustomer = new Customer();
    public Driver driverAkun;
    Scanner input = new Scanner(System.in);
    private boolean isAdmin = false; // Menentukan apakah pengguna adalah Admin atau Customer

    public static void bersihkanConsole() {
        try {
            Process process = new ProcessBuilder("cmd", "/c", "cls", "clear").inheritIO().start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void pilihRole() {
        System.out.println("\n" + "=".repeat(30) + " PILIH ROLE " + "=".repeat(30) + "\n");
        while (true) {
            try {
                System.out.println("1. Admin");
                System.out.println("2. Customer");
                System.out.print("Input: ");
                int pilihan = input.nextInt();

                if (pilihan == 1) {
                    isAdmin = true;
                    break;
                } else if (pilihan == 2) {
                    isAdmin = false;
                    break;
                } else {
                    System.out.println("\n=> Pilihan tidak valid. Silakan pilih antara 1 atau 2.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n=> Input harus berupa angka.\n");
                input.nextLine();
            }
        }
        bersihkanConsole();
        authMenu(); // Menu autentikasi setelah memilih role
    }

    /**
     * Handles the sign-in process for Admin or Customer based on the selected role.
     */
    public void sign_in() {
        String username;
        String password;

        System.out.println("\n" + "=".repeat(30) + " SIGN-IN " + "=".repeat(30));
        System.out.println("\nSilakan login ke akun Anda\n");

        while (true) {
            try {
                System.out.print("Username: ");
                username = input.next();
                System.out.print("Password: ");
                password = input.next();

                if (isAdmin) {
                    if (!akunAdmin.validateSignIn(username, password)) {
                        System.out.println("\n=> Username atau password tidak valid.\n");
                    } else {
                        break;
                    }
                } else {
                    if (!akunCustomer.validateSignIn(username, password)) {
                        System.out.println("\n=> Username atau password tidak valid.\n");
                    } else {
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("=> Input tidak valid, silakan masukkan karakter yang benar.");
                input.nextLine();
            }
        }

        System.out.println("\n=> Login berhasil.\n");
        bersihkanConsole();
        if (isAdmin) {
            driverAkun = new AdminDriver();
        } else {
            CustomerDriver driver = new CustomerDriver((Customer) akunCustomer);
            driver.run();
            return;
        }
        driverAkun.run();
    }

    /**
     * Handles the sign-up process for creating a new Admin or Customer account.
     */
   public void sign_up() {
    String username;
    String password;

    System.out.println("\n" + "=".repeat(30) + " SIGN-UP " + "=".repeat(30));
    System.out.println("\nSilakan masukkan data untuk mendaftar\n");

    while (true) {
        try {
            System.out.print("Username: ");
            username = input.next();
            System.out.print("Password: ");
            password = input.next();

            if (isAdmin) {
                int result = akunAdmin.validateSignUp(username, password);
                if (result == 0) {
                    System.out.println("\n=> Username sudah digunakan oleh Admin. Silakan pilih username lain.\n");
                } else if (result == 1) {
                    akunAdmin.saveToTextFile(username, password);
                    System.out.println("\n=> Akun Admin berhasil dibuat.\n");
                    break;
                }
            } else {
                int result = akunCustomer.validateSignUp(username, password);
                if (result == 0) {
                    System.out.println("\n=> Anda sudah memiliki akun sebelumnya.\n");
                } else if (result == 1) {
                    System.out.println("\n=> Username sudah digunakan oleh Customer. Silakan pilih username lain.\n");
                } else if (result == 2) {
                    akunCustomer.saveToTextFile(username, password);
                    System.out.println("\n=> Akun Customer berhasil dibuat.\n");
                    break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("=> Input tidak valid, silakan masukkan karakter yang benar.");
            input.nextLine();
        }
    }

    bersihkanConsole();
    authMenu();
}



    /**
     * Displays the authentication menu, allowing users to sign in, sign up, or exit.
     */
    public void authMenu() {
        int inputAuth;
        System.out.println("\n" + "=".repeat(30) + " AUTENTIKASI " + "=".repeat(30) + "\n");
        while (true) {
            try {
                System.out.println("1. Sign in");
                System.out.println("2. Sign up");
                System.out.println("0. Exit");
                System.out.print("Input: ");
                inputAuth = input.nextInt();

                if (inputAuth >= 0 && inputAuth <= 2) {
                    break;
                } else {
                    System.out.println("\n=> Silakan pilih opsi yang tersedia.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n=> Input harus berupa angka.\n");
                input.nextLine();
            }
        }

        bersihkanConsole();
        if (inputAuth == 1) {
            sign_in();
        } else if (inputAuth == 2) {
            sign_up();
        }
    }

    /**
     * Initiates the program by displaying the role selection menu.
     */
    public void run() {
        pilihRole();
    }

    /**
     * The main method to start the program.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        Main program = new Main();
        program.run();
    }
}
