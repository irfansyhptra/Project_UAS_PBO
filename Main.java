import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public Akun akunAdmin = new Admin();
    public Akun akunCustomer = new Customer();
    public Driver driverAkun;
    Scanner input = new Scanner(System.in);
    private boolean isAdmin = false;

    public static void bersihkanConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
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
        authMenu();
    }

    public void sign_in() {
        String username;
        String password;

        System.out.println("\n" + "=".repeat(30) + " SIGN-IN " + "=".repeat(30));
        while (true) {
            try {
                System.out.print("Username: ");
                username = input.next();
                System.out.print("Password: ");
                password = input.next();

                if (isAdmin) {
                    if (!akunAdmin.validasiMasuk(username, password)) {
                        System.out.println("\n=> Username atau password tidak valid.\n");
                    } else {
                        break;
                    }
                } else {
                    if (!akunCustomer.validasiMasuk(username, password)) {
                        System.out.println("\n=> Username atau password tidak valid.\n");
                    } else {
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("\n=> Input tidak valid, silakan coba lagi.");
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

    public void sign_up() {
        String username;
        String password;

        System.out.println("\n" + "=".repeat(30) + " SIGN-UP " + "=".repeat(30));
        while (true) {
            try {
                System.out.print("Username: ");
                username = input.next();
                System.out.print("Password: ");
                password = input.next();

                if (isAdmin) {
                    int result = akunAdmin.validasiDaftar(username, password);
                    if (result == 0) {
                        System.out.println("\n=> Akun Admin sudah ada.\n");
                    } else if (result == 1) {
                        akunAdmin.simpanKeFileTeks(username, password);
                        System.out.println("\n=> Akun Admin berhasil dibuat.\n");
                        break;
                    }
                } else {
                    int result = akunCustomer.validasiDaftar(username, password);
                    if (result == 0) {
                        System.out.println("\n=> Akun sudah ada.\n");
                    } else if (result == 1) {
                        System.out.println("\n=> Username sudah digunakan. Silakan pilih username lain.\n");
                    } else if (result == 2) {
                        akunCustomer.simpanKeFileTeks(username, password);
                        System.out.println("\n=> Akun Customer berhasil dibuat.\n");
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("\n=> Input tidak valid, silakan coba lagi.");
                input.nextLine();
            }
        }
        bersihkanConsole();
        authMenu();
    }

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

    public void run() {
        pilihRole();
    }

    public static void main(String[] args) {
        Main program = new Main();
        program.run();
    }
}
