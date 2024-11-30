/**
 * @author Kelompok 4
 * @version 1.0
 * @since 2024-11-29
 * 
 * Kelas ini mengatur metode pembayaran Cash On Delivery (COD).
 */
public class COD extends Pembayaran {
    /**
     * Memproses pembayaran menggunakan metode COD.
     *
     * @param username   Nama pengguna di marketplace.
     * @param totalHarga Total harga yang harus dibayar.
     */
    @Override
    public void prosesPembayaran(String username, int totalHarga) {
        System.out.println("\n=> Memproses metode pembayaran Cash On Delivery . . .\n");

        // Simulasi proses pengalihan
        for (int i = 0; i <= 100000; i++) {
            if (i % 20000 == 0) {
                System.out.print("\rMengalihkan ... " + i / 20000 + "%");
            }
        }
        bersihkanConsole();

        // Informasi pembayaran
        System.out.println("\n" + "=".repeat(10) + " Cash On Delivery " + "=".repeat(10));
        System.out.println("\nInformasi Pembayaran\n");
        System.out.println("Nama Pengguna: " + username);
        System.out.printf("Total Pembayaran: Rp %,d%n", totalHarga);
        System.out.println("\n" + "=".repeat(40));
        System.out.println("\n=> Pembayaran Cash On Delivery berhasil diminta");
    }
}
