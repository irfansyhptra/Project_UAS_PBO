/**
 * @author Kelompok 4
 * @version 1.0
 * @since 2024-11-29
 * 
 * Kelas ini merepresentasikan barang dengan atribut kode, nama, harga, dan stok.
 */
public class Barang {
    private final String kodeBarang;
    private String namaBarang;
    private int harga;
    private int stok;

    /**
     * Konstruktor untuk membuat objek Barang baru.
     *
     * @param namaBarang Nama barang.
     * @param kodeBarang Kode barang.
     * @param harga      Harga barang.
     * @param stok       Jumlah stok barang.
     */
    public Barang(String namaBarang, String kodeBarang, int harga, int stok) {
        this.namaBarang = namaBarang;
        this.kodeBarang = kodeBarang;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter dan Setter dengan penamaan yang jelas
    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
