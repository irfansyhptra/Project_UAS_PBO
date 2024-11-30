import java.io.IOException;

abstract public class Driver {

    // Kode ANSI untuk pewarnaan teks
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_HIJAU = "\u001B[32m";
    private static final String ANSI_MERAH = "\u001B[31m";
    private static final String ANSI_KUNING = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Membersihkan layar konsol.
     * Menggunakan perintah khusus sesuai platform untuk membersihkan konsol.
     * Menangani IOException dan InterruptedException.
     */
    public static void bersihkanConsole() {
        try {
            Process process = new ProcessBuilder("cmd", "/c", "cls", "clear").inheritIO().start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println(ANSI_MERAH + "Terjadi kesalahan saat membersihkan layar." + ANSI_RESET);
            e.printStackTrace();
        }
    }

    /**
     * Antarmuka dalam yang merepresentasikan fungsionalitas khusus admin.
     * Antarmuka AdminDriver menyediakan metode untuk mengelola pengguna, barang, dan transaksi.
     */
    interface AdminDriver {
        void hapusPengguna();
        void tambahBarang();
        boolean tampilkanBarang();
        void ubahBarang();
        void hapusBarang();
        void aturTransaksi();
    }

    /**
     * Antarmuka dalam yang merepresentasikan fungsionalitas khusus pelanggan.
     * Antarmuka CustomerDriver menyediakan metode untuk mengelola barang, keranjang, dan transaksi pelanggan.
     */
    interface CustomerDriver {
        void tampilkanBarang();
        void tambahKeKeranjang();
        boolean tampilkanKeranjang();
        void ubahKeranjang();
        void hapusKeranjang();
        void checkoutBarang();
        void cekFaktur();
    }

    /**
     * Metode abstrak yang harus diimplementasikan oleh subclass.
     * Merepresentasikan fungsionalitas utama yang akan dijalankan oleh driver.
     */
    public abstract void run();

    /**
     * Menampilkan pesan selamat datang dengan gaya khusus.
     */
    protected void tampilkanPesanSelamatDatang() {
        System.out.println(ANSI_CYAN + "===============================");
        System.out.println("  Selamat Datang di Marketplace ");
        System.out.println("===============================" + ANSI_RESET);
    }

    /**
     * Menampilkan pemisah untuk memperjelas tampilan.
     */
    protected void tampilkanPemisah() {
        System.out.println(ANSI_KUNING + "--------------------------------" + ANSI_RESET);
    }

    /**
     * Menampilkan pesan kesalahan.
     *
     * @param pesan Pesan kesalahan yang akan ditampilkan.
     */
    protected void tampilkanPesanKesalahan(String pesan) {
        System.out.println(ANSI_MERAH + "Kesalahan: " + pesan + ANSI_RESET);
    }

    /**
     * Menampilkan pesan sukses.
     *
     * @param pesan Pesan sukses yang akan ditampilkan.
     */
    protected void tampilkanPesanSukses(String pesan) {
        System.out.println(ANSI_HIJAU + "Berhasil: " + pesan + ANSI_RESET);
    }
}
