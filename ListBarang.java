import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Kelompok 4
 * @version 1.0
 * @since 2023-12-01
 */
public class ListBarang {
    ArrayList<Barang> barang;

    /**
     * Constructs an empty list of items.
     */
    public ListBarang() {
        this.barang = new ArrayList<>();
    }

    /**
     * Adds a new item to the list and saves the changes to the file.
     *
     * @param newBarang The item to be added.
     */
    public void tambahBarang(Barang newBarang) {
        barang.add(newBarang);  
        simpanKeFile();
    }

    /**
     * Removes an item from the list and updates the file.
     *
     * @param kodeBarang The code of the item to be removed.
     * @return True if the item is successfully removed; false otherwise.
     */
    public boolean hapusBarang(String kodeBarang) {
        Barang barangToDelete = null;
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                barangToDelete = b;
                break;
            }
        }
        
        if (barangToDelete != null) {
            barang.remove(barangToDelete);
            String fileName = "Admin/Barang/ListBarang.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
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
            return true;
        } else {
            return false;
        }
    }

    /**
     * Edits the details of an item in the list and updates the file.
     *
     * @param kodeBarang The code of the item to be edited.
     * @param namaBaru   The new name of the item.
     * @param hargaBaru  The new price of the item.
     * @param stokBaru   The new stock of the item.
     * @return True if the item is successfully edited; false otherwise.
     */
    public boolean editBarang(String kodeBarang, String namaBaru, int hargaBaru, int stokBaru) {
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                b.setNamaBarang(namaBaru);
                b.setHarga(hargaBaru);
                b.setStok(stokBaru);
                String fileName = "Admin/Barang/ListBarang.txt";
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (Barang bar : barang) {
                        writer.println("Kode Barang: " + bar.getKodeBarang());
                        writer.println("Nama Barang: " + bar.getNamaBarang());
                        writer.println("Harga: " + bar.getHarga());
                        writer.println("Stok: " + bar.getStok());
                        writer.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }

        System.out.println("\nItem's code " + kodeBarang + " is not found.\n");
        return false;
    }

    /**
     * Saves the list of items to a file.
     */
    private void simpanKeFile() {
        String fileName = "Admin/Barang/ListBarang.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for(int i = 0; i < barang.size(); i++){
                writer.println("Kode Barang: " + barang.get(i).getKodeBarang());
                writer.println("Nama Barang: " + barang.get(i).getNamaBarang());
                writer.println("Harga: " + barang.get(i).getHarga());
                writer.println("Stok: " + barang.get(i).getStok());
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads item data from a file and populates the list.
     *
     * @param fileName The file path to read item data from.
     */
    public void bacaDariFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String kodeBarang = scanner.nextLine().split(": ")[1];
                String namaBarang = scanner.nextLine().split(": ")[1];
                int harga = Integer.parseInt(scanner.nextLine().split(": ")[1]);
                int stok = Integer.parseInt(scanner.nextLine().split(": ")[1]);

                barang.add(new Barang(namaBarang, kodeBarang, harga, stok));
                scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if an item with the specified code exists in the list.
     *
     * @param kodeBarang The code of the item to be checked.
     * @return True if the item exists; false otherwise.
     */
    public boolean idValidator(String kodeBarang) {
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                System.out.println("\nItem's name: " + b.getNamaBarang());
                System.out.println("Price: " + b.getHarga());
                System.out.println("Stock: " + b.getStok() + "\n");
                return true;
            }
        }
        System.out.println("\nItem's code " + kodeBarang + " is not found.\n");
        return false;
    }

}
