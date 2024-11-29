import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerDriver extends Driver {
    Customer akun;
    Transaksi transaksi;
    Invoice invoice;
    ListBarang listBarang;
    Keranjang keranjang;
    Scanner input = new Scanner(System.in);
    CustomerImpl customerImpl = new CustomerImpl();

    public CustomerDriver(Customer akun) {
        this.akun = akun;
    }

    /**
     * Inner class providing implementations for customer-related operations.
     * Corrected to avoid interface-related errors.
     */
    private class CustomerImpl {
        public void masukKeranjang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            keranjang = new Keranjang();
            String kodeBarang;
            int jumlahBeli = 0;

            showBarang();
            while (true) {
                System.out.print("Masukkan kode barang yang ingin ditambahkan ke keranjang: ");
                kodeBarang = input.next();
                if (listBarang.idValidator(kodeBarang)) {
                    break;
                }
            }
            while (true) {
                while (true) {
                    try {
                        System.out.print("Jumlah: ");
                        jumlahBeli = input.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("\n=> Jenis input jumlah tidak valid!\n");
                        input.nextLine();
                    }
                    if (jumlahBeli > 0) {
                        break;
                    } else {
                        System.out.println("\n=> Jumlah harus lebih dari 0\n");
                    }
                }
                if (keranjang.tambahBarang(kodeBarang, "Customer/Cus" + akun.getUsername() + "/Keranjang.txt", jumlahBeli) > 0) {
                    System.out.println("\n=> Barang ini di keranjang Anda memiliki jumlah yang melebihi stok\n");
                } else {
                    break;
                }
            }

            System.out.println("\n=> Barang berhasil ditambahkan ke keranjang\n");
            for (int i = 0; i <= 8000; i++) {
                if (i / 2000 == 0) {
                    continue;
                }
                System.out.print("\rMengalihkan ... " + i / 2000);
            }
            bersihkanConsole();
        }

        public void checkoutBarang() {
            transaksi = new Transaksi();
            Path path = Paths.get("Customer/Cus" + akun.getUsername() + "/Transaksi.txt");
            try {
                byte[] fileContent = Files.readAllBytes(path);

                if (fileContent.length == 0) {
                    if (tampilKeranjang()) {
                        transaksi.buatTransaksi(akun.getUsername());
                    } else {
                        System.out.println("\n=> Anda tidak memiliki barang untuk checkout, keranjang tidak boleh kosong!\n");
                    }
                } else {
                    System.out.println("\n\n=> Anda masih memiliki transaksi dalam antrean");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Terjadi kesalahan saat memeriksa file.");
            }
        }

        public void checkInvoice() {
            transaksi = new Transaksi();
            Path path = Paths.get("Customer/Cus" + akun.getUsername() + "/Invoice.txt");
            try {
                byte[] fileContent = Files.readAllBytes(path);

                if (fileContent.length == 0) {
                    System.out.println("\n=> Anda belum memiliki invoice");
                } else {
                    transaksi.cekTransaksi(akun.getUsername());
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Terjadi kesalahan saat memeriksa file.");
            }
        }

        public void showBarang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            ArrayList<Barang> barang = listBarang.barang;
            System.out.println("\n" + "=".repeat(30) + " DAFTAR BARANG " + "=".repeat(30) + "\n");
            if (barang.isEmpty()) {
                System.out.println("=> Barang tidak ditemukan\n");
            } else {
                for (Barang b : barang) {
                    if (b.getStok() > 0) {
                        System.out.println("Kode Barang: " + b.getKodeBarang());
                        System.out.println("Nama Barang: " + b.getNamaBarang());
                        System.out.println("Harga: " + b.getHarga());
                        System.out.println("Stok: " + b.getStok());
                        System.out.println();
                    }
                }
            }
            System.out.println("=".repeat(72) + "\n");
        }

        public boolean tampilKeranjang() {
            keranjang = new Keranjang();
            return keranjang.lihatKeranjang(akun.getUsername()) != 0;
        }

        public void editCart() {
            keranjang = new Keranjang();
            String kodeBarang;

            if (!tampilKeranjang()) {
                System.out.println("=> Anda tidak memiliki barang di keranjang.");
            } else {
                System.out.println("\n" + "=".repeat(30) + " EDIT BARANG DI KERANJANG " + "=".repeat(30) + "\n");
                while (true) {
                    System.out.print("Masukkan kode barang yang ingin diubah: ");
                    kodeBarang = input.next();
                    if (keranjang.idValidator(kodeBarang, akun.getUsername())) {
                        while (true) {
                            int quantity;
                            while (true) {
                                System.out.print("\nJumlah Baru: ");
                                quantity = input.nextInt();
                                if (quantity > 0) {
                                    break;
                                } else {
                                    System.out.println("\n=> Jumlah harus lebih dari 0");
                                }
                            }
                            int count = keranjang.editBarang(kodeBarang, quantity, akun.getUsername());
                            if (count == 1) {
                                System.out.println("\n=> Jumlah melebihi stok");
                            } else {
                                break;
                            }
                        }

                        System.out.println("\n=> Jumlah barang berhasil diubah\n");
                        break;
                    }
                }
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rMengalihkan ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }

        public void deleteCart() {
            keranjang = new Keranjang();
            String kodeBarang;

            if (!tampilKeranjang()) {
                System.out.println("=> Anda tidak memiliki barang di keranjang.");
            } else {
                System.out.println("\n" + "=".repeat(30) + " HAPUS BARANG DI KERANJANG " + "=".repeat(30) + "\n");
                while (true) {
                    System.out.print("Masukkan kode barang yang ingin dihapus: ");
                    kodeBarang = input.next();

                    if (keranjang.hapusBarang(kodeBarang, akun.getUsername())) {
                        System.out.println("\n=> Barang berhasil dihapus.\n");
                        break;
                    }
                    System.out.println("\n=> Kode barang tidak ditemukan.\n");
                }

                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rMengalihkan ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }
    }

    public void run() {
        int adminInput;

        while (true) {
            System.out.println("\n" + "=".repeat(30) + " DASHBOARD PELANGGAN " + "=".repeat(30) + "\n");
            System.out.println("Pilih opsi");
            System.out.println("1. Tampilkan Barang");
            System.out.println("2. Tampilkan Keranjang");
            System.out.println("3. Tambah Barang");
            System.out.println("4. Ubah Barang");
            System.out.println("5. Hapus Barang");
            System.out.println("6. Permintaan Checkout");
            System.out.println("7. Invoice");
            System.out.println("0. Keluar");

            while (true) {
                try {
                    System.out.print("\nMasukkan : ");
                    adminInput = input.nextInt();
                    if (adminInput >= 0 && adminInput <= 7) break;
                    System.out.println("\n=> Harap masukkan opsi nomor yang tersedia");
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Jenis input harus berupa angka!");
                    input.nextLine();
                }
            }

            bersihkanConsole();

            switch (adminInput) {
                case 1 -> customerImpl.showBarang();
                case 2 -> customerImpl.tampilKeranjang();
                case 3 -> customerImpl.masukKeranjang();
                case 4 -> customerImpl.editCart();
                case 5 -> customerImpl.deleteCart();
                case 6 -> customerImpl.checkoutBarang();
                case 7 -> customerImpl.checkInvoice();
                case 0 -> {
                    System.out.println("\n=> Berhasil Keluar, Terima kasih...\n");
                    return;
                }
            }
        }
    }
}
