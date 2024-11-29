import java.util.List;

abstract public class Akun {

    // Kode ANSI untuk pewarnaan teks
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_HIJAU = "\u001B[32m";
    private static final String ANSI_MERAH = "\u001B[31m";
    private static final String ANSI_KUNING = "\u001B[33m";

    /**
     * Menyimpan informasi akun pengguna ke dalam file teks.
     *
     * @param username Nama pengguna.
     * @param password Kata sandi pengguna.
     */
    public abstract void simpanKeFileTeks(String username, String password);

    /**
     * Membaca dan mengembalikan daftar akun pelanggan dari sumber data.
     *
     * @return Daftar informasi akun pelanggan.
     */
    public abstract List<String> bacaAkunPelanggan();

    /**
     * Memvalidasi kredensial masuk pengguna.
     *
     * @param username Nama pengguna yang dimasukkan.
     * @param password Kata sandi yang dimasukkan.
     * @return True jika kredensial valid, false jika tidak.
     */
    public abstract boolean validasiMasuk(String username, String password);

    /**
     * Memvalidasi kredensial pendaftaran pengguna baru.
     *
     * @param username Nama pengguna yang didaftarkan.
     * @param password Kata sandi yang didaftarkan.
     * @return Kode hasil validasi:
     *         - 0: Pendaftaran berhasil.
     *         - 1: Nama pengguna sudah ada.
     *         - 2: Kegagalan validasi lainnya.
     */
    public abstract int validasiDaftar(String username, String password);

    /**
     * Menampilkan pesan keberhasilan untuk operasi yang sukses.
     *
     * @param pesan Pesan keberhasilan yang akan ditampilkan.
     */
    protected void tampilkanPesanBerhasil(String pesan) {
        System.out.println(ANSI_HIJAU + "Berhasil: " + pesan + ANSI_RESET);
    }

    /**
     * Menampilkan pesan kesalahan untuk operasi yang gagal.
     *
     * @param pesan Pesan kesalahan yang akan ditampilkan.
     */
    protected void tampilkanPesanKesalahan(String pesan) {
        System.out.println(ANSI_MERAH + "Kesalahan: " + pesan + ANSI_RESET);
    }

    /**
     * Menampilkan pesan informasi.
     *
     * @param pesan Pesan informasi yang akan ditampilkan.
     */
    protected void tampilkanPesanInformasi(String pesan) {
        System.out.println(ANSI_KUNING + "Info: " + pesan + ANSI_RESET);
    }
}
