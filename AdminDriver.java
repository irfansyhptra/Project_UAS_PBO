import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.InputMismatchException;
import java.util.List;

public class AdminDriver extends Driver {
    Admin akun;
    ListBarang listBarang;
    Keranjang keranjang;
    Transaksi transaksi;
    ArrayList<Transaksi> listTransaksi;

    AdminImpl adminImpl = new AdminImpl();

    Scanner input = new Scanner(System.in);

    private static boolean deleteFolder(File folder) {
        if (!folder.exists()) {
            System.out.println("Folder tidak ditemukan.");
            return false;
        }

        if (!folder.isDirectory()) {
            System.out.println("Objek bukan direktori.");
            return false;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }

        return folder.delete();
    }

    /**
     * The nested {@code AdminImpl} class implements the {@code AdminDriver}
     * interface
     * and provides specific methods for managing users, items, and transactions by
     * the admin.
     */
    private class AdminImpl implements AdminDriver {

        /**
         * Deletes a customer user account based on the provided customer ID.
         */
        @Override
        public void hapusPengguna() {

            Path path = Paths.get("Customer/Credentials/AkunCustomer.txt");
            try {
                byte[] fileContent = Files.readAllBytes(path);

                if (fileContent.length == 0) {
                    System.out.println("\n=> There is no customer.");
                } else {

                    try (Scanner scanner = new Scanner(new File("Customer/Credentials/AkunCustomer.txt"))) {
                        System.out.println("\n" + "=".repeat(30) + " CUSTOMERS " + "=".repeat(30) + "\n");
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            System.out.println(line);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int idCustomer;
                    System.out.println("\n" + "=".repeat(30) + " DELETE CUSTOMER " + "=".repeat(30) + "\n");
                    System.out.print("ID Customer : ");
                    idCustomer = input.nextInt();

                    try {
                        File inputFile = new File("Customer/Credentials/AkunCustomer.txt");
                        File tempFile = new File("Customer/Credentials/AkunCustomer_temp.txt");

                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

                        String line;
                        boolean userFound = false;
                        String deletedUsername = null;

                        while ((line = reader.readLine()) != null) {
                            if (line.trim().startsWith("id:")) {
                                int idFromFile = Integer.parseInt(line.split(":")[1].trim());

                                if (idFromFile == idCustomer) {
                                    userFound = true;
                                    deletedUsername = reader.readLine().split(":")[1].trim();
                                    for (int i = 0; i < 2; i++) {
                                        reader.readLine();
                                    }
                                } else {
                                    writer.println(line);
                                }
                            } else {
                                writer.println(line);
                            }
                        }

                        reader.close();
                        writer.close();

                        File folder = new File("Customer/Cus" + deletedUsername);
                        if (!userFound) {
                            System.out.println("Customer ID not found.");
                        } else {
                            if (deleteFolder(folder)) {
                                System.out.println("\n=> User " + deletedUsername + " successfully deleted.\n");
                            } else {
                                System.out.println("Gagal menghapus folder.");
                            }
                            inputFile.delete();
                            tempFile.renameTo(inputFile);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i <= 8000; i++) {
                        if (i / 2000 == 0) {
                            continue;
                        }
                        System.out.print("\rRedirecting ... " + i / 2000);
                    }
                    bersihkanConsole();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An error occurred while checking the file.");
            }
        }

        /**
         * Adds a new item to the list of available items.
         */
        @Override
        public void tambahBarang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            Random random = new Random();
            Scanner input = new Scanner(System.in);
            LocalDate currentDate = LocalDate.now();
            int lastTwoDigitsOfYear = currentDate.getYear() % 100;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
            int randomNumber = random.nextInt(900) + 100;

            System.out.println("\n" + "=".repeat(30) + " ADD/CREATE ITEM " + "=".repeat(30) + "\n");

            String kodeBarang = "BRG" + currentDate.format(formatter).toString() + randomNumber;

            System.out.print("Item's name: ");
            String namaBarang = input.next();

            int harga;
            while (true) {
                try {
                    System.out.print("Price: ");
                    harga = input.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Price must be number\n");
                    input.nextLine();
                }
            }

            int stok;
            while (true) {
                try {
                    System.out.print("Stock: ");
                    stok = input.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Stock must be number\n");
                    input.nextLine();
                }
            }

            Barang newBarang = new Barang(namaBarang, kodeBarang, harga, stok);

            listBarang.tambahBarang(newBarang);

            System.out.println("\n=> Item added successfully.\n");
            for (int i = 0; i <= 8000; i++) {
                if (i / 2000 == 0) {
                    continue;
                }
                System.out.print("\rRedirecting ... " + i / 2000);
            }
            bersihkanConsole();
        }

        /**
         * Displays the list of available items.
         */
        @Override
        public boolean tampilkanBarang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            ArrayList<Barang> barang = listBarang.barang;
            System.out.println("\n" + "=".repeat(30) + " ITEMS LIST " + "=".repeat(30) + "\n");
            if (barang.isEmpty()) {
                System.out.println("=> item not found\n");
                return false;
            } else {
                for (Barang b : barang) {
                    System.out.println("Item's code: " + b.getKodeBarang());
                    System.out.println("Item's name: " + b.getNamaBarang());
                    System.out.println("Price: " + b.getHarga());
                    System.out.println("Stock: " + b.getStok());
                    System.out.println();
                }
            }
            System.out.println("=".repeat(72) + "\n");
            return true;
        }

        /**
         * Edits the details of an existing item.
         */
        @Override
        public void ubahBarang() {
            String kodeBarang;
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");

            if (tampilkanBarang()) {
                System.out.println("\n" + "=".repeat(30) + " EDIT ITEM " + "=".repeat(31) + "\n");
                while (true) {
                    System.out.print("Input the Item's code that you want to edit: ");
                    kodeBarang = input.next();
                    if (listBarang.idValidator(kodeBarang)) {
                        break;
                    }
                    System.out.println("\n=> Item's code is not found.");
                }

                System.out.print("New item's name: ");
                String namaBaru = input.next();

                int hargaBaru;
                while (true) {
                    try {
                        System.out.print("New price: ");
                        hargaBaru = input.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("\n=> New price must be number\n");
                        input.nextLine();
                    }
                }

                int stokBaru;
                while (true) {
                    try {
                        System.out.print("New stock: ");
                        stokBaru = input.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("\n=>New stock must be number\n");
                        input.nextLine();
                    }
                }

                listBarang.editBarang(kodeBarang, namaBaru, hargaBaru, stokBaru);
                System.out.println("\n=> Item edited successfully.\n");
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rRedirecting ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }

        /**
         * Deletes an item from the list of available items.
         */
        @Override
        public void hapusBarang() {
            String kodeBarang;
            int validation = 0;
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");

            if (tampilkanBarang()) {
                System.out.println("\n" + "=".repeat(30) + " DELETE ITEM " + "=".repeat(30) + "\n");

                while (true) {
                    System.out.print("Input the Item's code that you want to delete: ");
                    kodeBarang = input.next();
                    if (listBarang.idValidator(kodeBarang)) {
                        while (true) {
                            try {
                                System.out.println("1. Delete");
                                System.out.println("2. Cancel");
                                System.out.print("\nAre you sure want to delete this item? : ");
                                validation = input.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("\n=> Input the available options");
                                input.nextLine();
                            }
                            if (validation == 1) {
                                break;
                            } else if (validation == 2) {
                                System.out.println("\n=> Cancelled.\n");
                                for (int i = 0; i <= 8000; i++) {
                                    if (i / 2000 == 0) {
                                        continue;
                                    }
                                    System.out.print("\rRedirecting ... " + i / 2000);
                                }
                                return;
                            }
                        }
                    }
                    listBarang.hapusBarang(kodeBarang);
                    break;
                }

                System.out.println("\n=> Item deleted successfully.\n");
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rRedirecting ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }

        /**
         * Manages transactions by accepting or declining them based on admin input.
         */
        @Override
        public void aturTransaksi() {
            String kodeTransaksi;
            String kodeBaru = null;
            String alert = null;
            int manageOption = 0;
            String updatedStatus = null;
            transaksi = new Transaksi();
            transaksi.bacaDariFile("Admin/Transaksi/Transaksi.txt");
            List<String> daftarKodeTransaksi = transaksi.getDaftarKodeTransaksiUnik();

            System.out.println("\n" + "=".repeat(30) + " INVOICES MANAGER " + "=".repeat(30) + "\n");
            System.out.println("");
            try (Scanner scanner = new Scanner(new File("Admin/Transaksi/Transaksi.txt"))) {
                while (scanner.hasNextLine()) {
                    String baris = scanner.nextLine();
                    if (baris.contains("Created at")) {
                        System.out.println(baris + "\n");
                        System.out.println("-".repeat(50));
                    } else {
                        System.out.println(baris);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            while (true) {
                System.out.print("\nInput the transaction's code that you want to manage: ");
                kodeTransaksi = input.next();
                if (daftarKodeTransaksi.contains(kodeTransaksi + " - WAITING")) {
                    break;
                }
                System.out.println("\n=> Transaction's code not found");
            }

            while (true) {
                System.out.println("\n1. Accept");
                System.out.println("2. Decline");
                System.out.print("\nChoose the options: ");
                manageOption = input.nextInt();
                if (manageOption < 1 || manageOption > 2) {
                    System.out.println("\n=> Option is not available, please input an available option!");
                } else {
                    break;
                }
            }

            for (int i = 0; i < daftarKodeTransaksi.size(); i++) {
                if ((kodeTransaksi + " - WAITING").equals(daftarKodeTransaksi.get(i))) {
                    if (manageOption == 1) {
                        alert = "\n=> Transaction's accepted successfully";
                        updatedStatus = " - ACCEPTED";
                    } else {
                        alert = "\n=> Transaction's declined successfully";
                        updatedStatus = " - DECLINED";
                    }
                    kodeBaru = kodeTransaksi + updatedStatus;
                }
            }

            String username = getPrefixBeforeTRS(kodeTransaksi);

            if (manageOption == 1) {
                ListBarang listBarangAdmin = new ListBarang();
                listBarangAdmin.bacaDariFile("Admin/Barang/ListBarang.txt");
                ArrayList<Barang> barangAdmin = listBarangAdmin.barang;

                ListBarang listBarangKeranjang = new ListBarang();
                listBarangKeranjang.bacaDariFile("Customer/Cus" + username + "/Transaksi.txt");
                ArrayList<Barang> barangKeranjang = listBarangKeranjang.barang;

                for (Barang b : barangAdmin) {
                    for (Barang bar : barangKeranjang) {
                        if (b.getKodeBarang().equals(bar.getKodeBarang())) {
                            b.setStok(b.getStok() - bar.getHarga());
                        }

                        try (PrintWriter writer = new PrintWriter(new FileWriter("Admin/Barang/ListBarang.txt"))) {
                            for (Barang brg : barangAdmin) {
                                writer.println("Kode Barang: " + brg.getKodeBarang());
                                writer.println("Nama Barang: " + brg.getNamaBarang());
                                writer.println("Harga: " + brg.getHarga());
                                writer.println("Stok: " + brg.getStok());
                                writer.println();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            try (Scanner scanner = new Scanner(new File("Admin/Transaksi/Transaksi.txt"));
                    PrintWriter writer = new PrintWriter("Admin/Transaksi/Transaksi_temp.txt")) {

                while (scanner.hasNextLine()) {
                    String baris = scanner.nextLine();

                    if (baris.startsWith("Kode Transaksi: ")
                            && baris.equals("Kode Transaksi: " + kodeTransaksi + " - WAITING")) {
                        writer.println("Kode Transaksi: " + kodeBaru);
                    } else {
                        writer.println(baris);
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Ganti file asli dengan file sementara
            File fileLama = new File("Admin/Transaksi/Transaksi.txt");
            File fileBaru = new File("Admin/Transaksi/Transaksi_temp.txt");
            fileLama.delete(); // Menghapus file lama
            fileBaru.renameTo(fileLama);
            System.out.println(alert + "\n");
            for (int i = 0; i <= 8000; i++) {
                if (i / 2000 == 0) {
                    continue;
                }
                System.out.print("\rRedirecting ... " + i / 2000);
            }
            bersihkanConsole();
        }

    }

    /**
     * Retrieves the prefix of a transaction code before the "TRS" identifier.
     *
     * @param code The transaction code.
     * @return The prefix before "TRS" in the transaction code.
     */
    private static String getPrefixBeforeTRS(String code) {
        int indexTRS = code.indexOf("TRS");
        if (indexTRS != -1) {
            return code.substring(0, indexTRS);
        } else {
            return code;
        }
    }

    /**
     * Runs the admin dashboard, allowing the admin to choose various options for
     * managing the system.
     */
    public void run() {

        int adminInput = 10;

        while (true) {

            System.out.println("\n" + "=".repeat(30) + " ADMIN DASHBOARD " + "=".repeat(30) + "\n");
            System.out.println("Menu");
            System.out.println("1. Hapus Akun Pengguna");
            System.out.println("2. Tampilkan Barang");
            System.out.println("3. Tambah Barang");
            System.out.println("4. Edit Barang");
            System.out.println("5. Hapus Barang");
            System.out.println("6. Kelola Transaksi");
            System.out.println("0. Logout");

            while (true) {
                try {
                    System.out.print("\nInput : ");
                    adminInput = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Input harus berupa angka!");
                    input.nextLine();
                }
                if (adminInput < 0 || adminInput > 6) {
                    System.out.println("\n=> Tolong masukkan opsi yang tersedia");
                } else if (adminInput == 2) {
                    bersihkanConsole();
                    adminImpl.tampilkanBarang();
                    System.out.println("Menu");
                    System.out.println("1. Hapus Akun Pengguna");
                    System.out.println("2. Tampilkan Barang");
                    System.out.println("3. Tambah Barang");
                    System.out.println("4. Edit Barang");
                    System.out.println("5. Hapus Barang");
                    System.out.println("6. Kelola Transaksi");
                    System.out.println("0. Logout");
                } else {
                    break;
                }
            }

            if (adminInput == 1) {
                bersihkanConsole();
                adminImpl.hapusPengguna();
            } else if (adminInput == 3) {
                bersihkanConsole();
                adminImpl.tambahBarang();
            } else if (adminInput == 4) {
                bersihkanConsole();
                adminImpl.ubahBarang();
            } else if (adminInput == 5) {
                bersihkanConsole();
                adminImpl.hapusBarang();
            } else if (adminInput == 6) {
                bersihkanConsole();
                adminImpl.aturTransaksi();
            } else if (adminInput == 0) {
                bersihkanConsole();
                break;
            }
        }
        System.out.println("\n=> Berhasil Log Out, Terimakasih..\n");

    }
}