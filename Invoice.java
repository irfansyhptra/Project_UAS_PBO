import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Invoice {

    /**
     * Referensi ke objek Transaksi untuk menangani transaksi.
     */
    public Transaksi transaksi;

    /**
     * Referensi ke objek Pembayaran untuk menangani pembayaran.
     */
    public Pembayaran pembayaran;

    /**
     * Menampilkan data invoice untuk pelanggan tertentu.
     *
     * @param username Username pelanggan.
     */
    public void tampilData(String username) {
        String invoiceFilePath = "Customer/Cus" + username + "/Invoice.txt";

        try (Scanner scanner = new Scanner(new File(invoiceFilePath))) {
            System.out.println("\n" + "=".repeat(30) + " DATA INVOICE " + "=".repeat(30) + "\n");
            while (scanner.hasNextLine()) {
                String baris = scanner.nextLine();
                if (baris.contains("Dibuat pada")) {
                    System.out.println(baris + "\n");
                    System.out.println("-".repeat(50));
                } else {
                    System.out.println(baris);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mengelola invoice pelanggan tertentu, memperbarui file invoice berdasarkan status transaksi.
     *
     * @param username Username pelanggan.
     * @return Penanda status pengelolaan invoice:
     *         - 0: Tidak ada perubahan pada invoice.
     *         - 1: Invoice diperbarui dengan transaksi diterima.
     *         - 2: Invoice diperbarui dengan transaksi ditolak dan pengembalian dana diproses.
     */
    public int kelolaInvoice(String username) {
        int penanda = 0;
        transaksi = new Transaksi();
        transaksi.bacaDariFile("Admin/Transaksi/Transaksi.txt");
        List<String> daftarKodeTransaksi = transaksi.getDaftarKodeTransaksiUnik();

        Transaksi invoice = new Transaksi();
        invoice.bacaDariFile("Customer/Cus" + username + "/Invoice.txt");
        List<String> daftarKodeInvoice = invoice.getDaftarKodeTransaksiUnik();

        for (String kodeTransaksi : daftarKodeTransaksi) {
            for (int i = 0; i < daftarKodeInvoice.size(); i++) {
                String kodeInvoice = daftarKodeInvoice.get(i);
                if (kodeInvoice.contains("MENUNGGU")) {
                    if (kodeTransaksi.equals(kodeInvoice.replace("MENUNGGU", "DITERIMA"))) {
                        daftarKodeInvoice.set(i, kodeTransaksi);
                        perbaruiFileInvoice(username, kodeInvoice, kodeTransaksi);
                        penanda = 1;

                    } else if (kodeTransaksi.equals(kodeInvoice.replace("MENUNGGU", "DITOLAK"))) {
                        daftarKodeInvoice.set(i, kodeTransaksi + " - Pengembalian Dana Berhasil Diproses!");
                        perbaruiFileInvoice(username, kodeInvoice, kodeTransaksi + " - Pengembalian Dana Berhasil Diproses!");
                        penanda = 2;
                    }
                }
            }
        }
        tampilData(username);

        return penanda;
    }

    /**
     * Memperbarui isi file invoice dengan kode transaksi baru.
     *
     * @param username Username pelanggan.
     * @param kodeLama Kode transaksi lama dalam file invoice.
     * @param kodeBaru Kode transaksi baru untuk menggantikan kode lama.
     */
    private void perbaruiFileInvoice(String username, String kodeLama, String kodeBaru) {
        String invoiceFilePath = "Customer/Cus" + username + "/Invoice.txt";

        try (Scanner scanner = new Scanner(new File(invoiceFilePath));
             PrintWriter penulis = new PrintWriter("Customer/Cus" + username + "/Invoice_temp.txt")) {

            while (scanner.hasNextLine()) {
                String baris = scanner.nextLine();

                if (baris.contains(kodeLama)) {
                    baris = baris.replace(kodeLama, kodeBaru);
                }

                penulis.println(baris);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ganti file sementara dengan file asli
        File fileLama = new File(invoiceFilePath);
        File fileBaru = new File("Customer/Cus" + username + "/Invoice_temp.txt");
        fileLama.delete();
        fileBaru.renameTo(fileLama);
    }
}
