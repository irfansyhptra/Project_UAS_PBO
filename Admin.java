import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Admin extends Akun {

    private String fileName = "Admin/Credentials/AkunAdmin.txt";

    @Override

    public void simpanKeFileTeks(String username, String password) {
        // Pastikan direktori ada
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Buat folder jika belum ada

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println("Username: " + username);
            writer.println("Password: " + password);
            writer.println(); // Baris kosong untuk memisahkan data akun
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> bacaAkunPelanggan() {
        List<String> accounts = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String username = scanner.nextLine().split(":")[1].trim();
                String password = scanner.nextLine().split(":")[1].trim();

                accounts.add(username);
                accounts.add(password);

                scanner.nextLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    /**
     * Validates the sign-in credentials for an Admin user.
     *
     * @param username The username entered for sign-in.
     * @param password The password entered for sign-in.
     * @return True if the provided credentials are valid, false otherwise.
     */
    @Override
    public boolean validasiMasuk(String username, String password) {
        List<String> accounts = bacaAkunPelanggan();

        for (int i = 0; i < accounts.size(); i += 2) {
            String uname = accounts.get(i);
            String pword = accounts.get(i + 1);

            if (username.equals(uname) && password.equals(pword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validates the sign-up credentials for an Admin user.
     * Admin accounts cannot be created through sign-up, so always returns 0.
     *
     * @param username The username entered for sign-up.
     * @param password The password entered for sign-up.
     * @return Always returns 0 as Admin accounts cannot be created through sign-up.
     */
@Override
public int validasiDaftar(String username, String password) {
    List<String> accounts = bacaAkunAdmin(); // Ganti ke metode pembaca Admin

    for (int i = 0; i < accounts.size(); i += 2) {
        String uname = accounts.get(i);

        if (username.equals(uname)) {
            return 0; // Username sudah digunakan
        }
    }
    return 1; // Username tersedia
}

// Tambahkan metode untuk membaca akun Admin
public List<String> bacaAkunAdmin() {
    List<String> accounts = new ArrayList<>();
    try (Scanner scanner = new Scanner(new File(fileName))) {
        while (scanner.hasNextLine()) {
            String username = scanner.nextLine().split(":")[1].trim();
            String password = scanner.nextLine().split(":")[1].trim();

            accounts.add(username);
            accounts.add(password);

            scanner.nextLine(); // Skip baris kosong
        }
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    return accounts;
}

}