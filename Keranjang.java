import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    private ArrayList<Barang> barang;
    private ListBarang listBarang;

    /**
     * Konstruktor untuk membuat objek keranjang belanja kosong.
     */
    public Keranjang() {
        this.barang = new ArrayList<>();
    }

    /**
     * Menambahkan jumlah tertentu dari suatu barang ke dalam keranjang belanja.
     *
     * @param kodeBarang Kode barang yang ingin ditambahkan.
     * @param fileName   Path file untuk menyimpan data keranjang.
     * @param jumlahBeli Jumlah barang yang ingin ditambahkan.
     * @return Status hasil operasi:
     *         - 0: Berhasil menambahkan barang.
     *         - 1: Jumlah melebihi stok.
     *         - 2: Kode barang tidak ditemukan.
     */
    public int tambahBarang(String kodeBarang, String fileName, int jumlahBeli) {
        listBarang = new ListBarang();
        listBarang.bacaDariFile(fileName); // Membaca data barang dari file.
        barang = listBarang.barang;

        // Membaca data barang dari sisi admin.
        ListBarang admin = new ListBarang();
        admin.bacaDariFile("Admin/Barang/ListBarang.txt");
        ArrayList<Barang> barangAdmin = admin.barang;

        int mark = 0; // Penanda status operasi.
        boolean exists = false;

        for (Barang b : barang) {
            for (Barang brgAdmin : barangAdmin) {
                // Memastikan kode barang sesuai.
                if (b.getKodeBarang().equals(brgAdmin.getKodeBarang()) && b.getKodeBarang().equals(kodeBarang)) {
                    // Memeriksa apakah jumlah yang diminta melebihi stok.
                    if (b.getHarga() + jumlahBeli > brgAdmin.getStok()) {
                        return 1; // Jumlah melebihi stok.
                    }
                    exists = true;
                    b.setHarga(b.getHarga() + jumlahBeli); // Menambahkan jumlah barang ke keranjang.
                    break;
                }
            }
        }

        // Jika barang ditemukan, tulis kembali data ke file.
        if (exists) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (Barang bar : barang) {
                    writer.println("Kode Barang: " + bar.getKodeBarang());
                    writer.println("Nama Barang: " + bar.getNamaBarang());
                    writer.println("Jumlah: " + bar.getHarga());
                    writer.println("Harga: " + bar.getStok());
                    writer.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0; // Berhasil menambahkan.
        }

        // Jika barang tidak ditemukan, periksa stok dari file admin dan tambahkan.
        for (Barang b : barangAdmin) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                if (jumlahBeli > b.getStok()) {
                    return 1; // Jumlah melebihi stok.
                }
                barang.add(new Barang(b.getNamaBarang(), kodeBarang, b.getHarga(), jumlahBeli));
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                    for (Barang bar : barang) {
                        writer.println("Kode Barang: " + bar.getKodeBarang());
                        writer.println("Nama Barang: " + bar.getNamaBarang());
                        writer.println("Jumlah: " + jumlahBeli);
                        writer.println("Harga: " + (b.getHarga() * jumlahBeli));
                        writer.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 0; // Berhasil menambahkan.
            }
        }

        return 2; // Kode barang tidak ditemukan.
    }

    /**
     * Menampilkan barang-barang di keranjang belanja.
     *
     * @param username Username pelanggan.
     * @return Jumlah barang di keranjang.
     */
    public int lihatKeranjang(String username) {
        int count = 0;

        // Membaca data barang dari file admin.
        ListBarang listBarangAdmin = new ListBarang();
        listBarangAdmin.bacaDariFile("Admin/Barang/ListBarang.txt");
        List<Barang> barangAdmin = listBarangAdmin.barang;

        // Membaca data barang dari keranjang pengguna.
        ListBarang listBarangKeranjang = new ListBarang();
        listBarangKeranjang.bacaDariFile("Customer/Cus" + username + "/Keranjang.txt");
        List<Barang> barangKeranjang = listBarangKeranjang.barang;

        System.out.println("\n" + "=".repeat(30) + " ITEM KERANJANG " + "=".repeat(30) + "\n");

        try (PrintWriter writer = new PrintWriter(new FileWriter("Customer/Cus" + username + "/Keranjang.txt"))) {
            for (Barang b : barangKeranjang) {
                boolean existsInAdmin = false;
                for (Barang ba : barangAdmin) {
                    if (b.getKodeBarang().equals(ba.getKodeBarang())) {
                        count++;
                        existsInAdmin = true;
                        break;
                    }
                }

                // Menulis barang ke file jika ditemukan di data admin.
                if (existsInAdmin) {
                    writer.println("Kode Barang: " + b.getKodeBarang());
                    writer.println("Nama Barang: " + b.getNamaBarang());
                    writer.println("Jumlah: " + b.getHarga());
                    writer.println("Harga: " + b.getStok());
                    writer.println();

                    System.out.println("Kode Barang: " + b.getKodeBarang());
                    System.out.println("Nama Barang: " + b.getNamaBarang());
                    System.out.println("Jumlah: " + b.getHarga());
                    System.out.println("Harga: " + b.getStok() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (count == 0) {
            System.out.println("=> Keranjang kosong.\n");
        }

        System.out.println("=".repeat(72) + "\n");
        return count;
    }

}
