import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ListBarang {
    // Menyimpan daftar barang dalam bentuk ArrayList
    ArrayList<Barang> barang;

    /**
     * Konstruktor untuk membuat daftar barang kosong.
     */
    public ListBarang() {
        this.barang = new ArrayList<>();
    }

    /**
     * Menambahkan barang baru ke dalam daftar dan menyimpan perubahan ke file.
     *
     * @param newBarang Barang yang akan ditambahkan.
     */
    public void tambahBarang(Barang newBarang) {
        barang.add(newBarang);
        simpanKeFile();
    }

    /**
     * Menghapus barang dari daftar berdasarkan kode barang dan memperbarui file.
     *
     * @param kodeBarang Kode barang yang akan dihapus.
     * @return True jika barang berhasil dihapus, false jika tidak ditemukan.
     */
    public boolean hapusBarang(String kodeBarang) {
        Barang barangToDelete = null;
        // Mencari barang berdasarkan kode
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                barangToDelete = b;
                break;
            }
        }

        // Jika barang ditemukan, hapus dari daftar dan perbarui file
        if (barangToDelete != null) {
            barang.remove(barangToDelete);
            simpanKeFile();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Mengedit informasi barang dalam daftar dan memperbarui file.
     *
     * @param kodeBarang Kode barang yang akan diedit.
     * @param namaBaru   Nama baru barang.
     * @param hargaBaru  Harga baru barang.
     * @param stokBaru   Stok baru barang.
     * @return True jika barang berhasil diedit, false jika barang tidak ditemukan.
     */
    public boolean editBarang(String kodeBarang, String namaBaru, int hargaBaru, int stokBaru) {
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                // Mengubah informasi barang
                b.setNamaBarang(namaBaru);
                b.setHarga(hargaBaru);
                b.setStok(stokBaru);
                simpanKeFile();
                return true;
            }
        }
        // Jika barang tidak ditemukan
        System.out.println("\nKode barang " + kodeBarang + " tidak ditemukan.\n");
        return false;
    }

    /**
     * Menyimpan daftar barang ke file.
     */
    private void simpanKeFile() {
        String fileName = "Admin/Barang/ListBarang.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Menulis setiap barang ke dalam file
            for (Barang b : barang) {
                writer.println("Kode Barang: " + b.getKodeBarang());
                writer.println("Nama Barang: " + b.getNamaBarang());
                writer.println("Harga: " + b.getHarga());
                writer.println("Stok: " + b.getStok());
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Membaca data barang dari file dan mengisi daftar barang.
     *
     * @param fileName Nama file yang akan dibaca.
     */
    public void bacaDariFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            // Membaca data barang baris per baris
            while (scanner.hasNextLine()) {
                String kodeBarang = scanner.nextLine().split(": ")[1];
                String namaBarang = scanner.nextLine().split(": ")[1];
                int harga = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                int stok = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                barang.add(new Barang(namaBarang, kodeBarang, harga, stok));
                if (scanner.hasNextLine()) {
                    scanner.nextLine(); // Membaca baris kosong
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mengecek apakah barang dengan kode tertentu ada dalam daftar.
     *
     * @param kodeBarang Kode barang yang akan dicek.
     * @return True jika barang ditemukan, false jika tidak.
     */
    public boolean idValidator(String kodeBarang) {
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                // Menampilkan informasi barang jika ditemukan
                System.out.println("\nNama Barang: " + b.getNamaBarang());
                System.out.println("Harga: " + b.getHarga());
                System.out.println("Stok: " + b.getStok() + "\n");
                return true;
            }
        }
        // Jika barang tidak ditemukan
        System.out.println("\nKode barang " + kodeBarang + " tidak ditemukan.\n");
        return false;
    }
}
