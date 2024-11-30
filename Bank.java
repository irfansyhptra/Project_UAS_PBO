import java.util.InputMismatchException;

/**
 * @author Muhammad Hizqil Alfi
 * @version 1.0
 * @since 2023-12-01
 * 
 * Kelas ini mengatur metode pembayaran melalui Bank. 
 * Memungkinkan pengguna memilih bank, memasukkan PIN, dan menampilkan informasi pembayaran.
 */
public class Bank extends Pembayaran {
    /**
     * Memproses pembayaran menggunakan akun bank.
     *
     * @param username   Nama pengguna di marketplace.
     * @param totalHarga Total harga yang harus dibayar.
     */
    @Override
    public void prosesPembayaran(String username, int totalHarga) {
        String namaBank;
        int bankOption;

        // Menampilkan daftar bank
        System.out.println("\n=== Pilihan Bank ===");
        System.out.println("1. BSI");
        System.out.println("2. BCA");
        System.out.println("3. BNI");
        System.out.print("\nPilih bank Anda: ");
        bankOption = input.nextInt();
        namaBank = (bankOption == 1) ? "BSI" : (bankOption == 2) ? "BCA" : "BNI";

        // Memasukkan PIN dengan validasi
        while (true) {
            try {
                System.out.print("\nMasukkan PIN " + namaBank + ": ");
                input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("\n=> PIN tidak valid. Coba lagi.");
                input.nextLine();
            }
        }

        // Membersihkan layar dan menampilkan informasi pembayaran
        bersihkanConsole();
        System.out.println("\n" + "=".repeat(10) + " " + namaBank + " " + "=".repeat(10));
        System.out.println("\nInformasi Pembayaran\n");
        System.out.println("Nama Pengguna: " + username);
        System.out.printf("Total Pembayaran: Rp %,d%n", totalHarga);
        System.out.println("\n" + "=".repeat(30));
    }
}
