import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Customer extends Akun {
    public Keranjang keranjang;
    public ArrayList<Invoice> invoiceSelesai;
    private final String namaFile = "Customer/Credentials/AkunCustomer.txt";
    private int id_Customer = 0;
    private String username;

    /**
     * Mengatur username untuk instance customer saat ini.
     *
     * @param username Username dari customer.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Menyimpan informasi akun customer ke file teks dan membuat folder serta file yang diperlukan.
     *
     * @param username Username dari customer.
     * @param password Password dari customer.
     */
    public void simpanKeFileTeks(String username, String password) {
        // Pastikan ID selalu bertambah
        id_Customer = bacaIDTerakhir() + 1;

        try (PrintWriter writer = new PrintWriter(new FileWriter(namaFile, true))) {
            writer.println("id: " + id_Customer);
            writer.println("Username: " + username);
            writer.println("Password: " + password);
            writer.println(); // Tambahkan baris kosong antar akun

        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menyimpan akun: " + e.getMessage());
        }

        buatFolderCustomer(username);
    }

    /**
     * Membuat folder dan file default untuk customer.
     *
     * @param username Username dari customer.
     */
    private void buatFolderCustomer(String username) {
        String namaFolder = "Cus" + username;
        File directory = new File("Customer", namaFolder);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            new File(directory, "Keranjang.txt").createNewFile();
            new File(directory, "Invoice.txt").createNewFile();
            new File(directory, "Transaksi.txt").createNewFile();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membuat file untuk customer: " + e.getMessage());
        }
    }

    /**
     * Membaca ID terakhir dari file akun customer untuk memastikan ID tetap unik.
     *
     * @return ID terakhir di file akun customer.
     */
    private int bacaIDTerakhir() {
        int lastID = 0;
        try (Scanner scanner = new Scanner(new File(namaFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("id:")) {
                    lastID = Integer.parseInt(line.split(":")[1].trim());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File akun customer tidak ditemukan. ID akan dimulai dari 0.");
        }
        return lastID;
    }

    /**
     * Membaca semua akun customer dari file teks.
     *
     * @return Daftar berisi data akun customer.
     */
    @Override
    public List<String> bacaAkunPelanggan() {
        List<String> akun = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(namaFile))) {
            while (scanner.hasNextLine()) {
                try {
                    String id = scanner.nextLine().split(":")[1].trim();
                    String username = scanner.nextLine().split(":")[1].trim();
                    String password = scanner.nextLine().split(":")[1].trim();
                    scanner.nextLine(); // Lewati baris kosong

                    id_Customer = Integer.parseInt(id);
                    akun.add(username);
                    akun.add(password);
                } catch (Exception e) {
                    System.out.println("Kesalahan saat membaca data akun: Format file tidak sesuai.");
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File akun customer tidak ditemukan. Pastikan file sudah dibuat.");
        }
        return akun;
    }

    /**
     * Memvalidasi login customer berdasarkan username dan password.
     *
     * @param username Username yang diinput.
     * @param password Password yang diinput.
     * @return True jika valid, false jika tidak.
     */
    @Override
    public boolean validasiMasuk(String username, String password) {
        List<String> akun = bacaAkunPelanggan();
        for (int i = 0; i < akun.size(); i += 2) {
            if (username.equals(akun.get(i)) && password.equals(akun.get(i + 1))) {
                this.setUsername(username);
                return true;
            }
        }
        return false;
    }

    /**
     * Memvalidasi pendaftaran akun customer baru.
     *
     * @param username Username yang diinput.
     * @param password Password yang diinput.
     * @return Kode hasil validasi:
     *         - 0: Akun sudah ada.
     *         - 1: Username sudah digunakan.
     *         - 2: Pendaftaran berhasil.
     */
    public int validasiDaftar(String username, String password) {
        List<String> akun = bacaAkunPelanggan();
        for (int i = 0; i < akun.size(); i += 2) {
            if (username.equals(akun.get(i))) {
                if (password.equals(akun.get(i + 1))) {
                    return 0; // Akun sudah ada
                }
                return 1; // Username sudah digunakan
            }
        }
        return 2; // Akun baru
    }

    /**
     * Mendapatkan username dari instance customer saat ini.
     *
     * @return Username dari customer.
     */
    public String getUsername() {
        return this.username;
    }
}
