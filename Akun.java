import java.util.List;

abstract public class Akun {

    // Kode ANSI untuk pewarnaan teks
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_HIJAU = "\u001B[32m";
    private static final String ANSI_MERAH = "\u001B[31m";
    private static final String ANSI_KUNING = "\u001B[33m";

    public abstract void simpanKeFileTeks(String username, String password);

    public abstract List<String> bacaAkunPelanggan();

    public abstract boolean validasiMasuk(String username, String password);

    public abstract int validasiDaftar(String username, String password);

    protected void tampilkanPesanBerhasil(String pesan) {
        System.out.println(ANSI_HIJAU + "Berhasil: " + pesan + ANSI_RESET);
    }

    protected void tampilkanPesanKesalahan(String pesan) {
        System.out.println(ANSI_MERAH + "Kesalahan: " + pesan + ANSI_RESET);
    }

    protected void tampilkanPesanInformasi(String pesan) {
        System.out.println(ANSI_KUNING + "Info: " + pesan + ANSI_RESET);
    }
}
