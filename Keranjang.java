import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kelompok 4
 * @version 1.0
 * @since 2023-12-01
 */
public class Keranjang {
    ArrayList<Barang> barang;
    ListBarang listBarang;

    /**
     * Constructs an empty shopping cart.
     */
    public Keranjang() {
        this.barang = new ArrayList<>();
    }

    /**
     * Adds a specified quantity of an item to the shopping cart.
     *
     * @param kodeBarang  The code of the item to be added.
     * @param fileName    The file path for storing the cart data.
     * @param jumlahBeli  The quantity of the item to be added.
     * @return An integer mark indicating the result of the addition:
     *         - 0: Addition successful.
     *         - 1: Quantity exceeds stock.
     *         - 2: Item code not found.
     */
    public int tambahBarang(String kodeBarang, String fileName, int jumlahBeli) {
        listBarang = new ListBarang();
        listBarang.bacaDariFile(fileName);
        barang = listBarang.barang;

        ListBarang admin = new ListBarang();
        admin.bacaDariFile("Admin/Barang/ListBarang.txt");
        ArrayList<Barang> barangAdmin = new ArrayList<>();
        barangAdmin = admin.barang;

        int jumlahLama = 0;
        int mark = 0;

        boolean exists = false;
        for (Barang b : barang) {
            for(Barang brg : barangAdmin){
                if (b.getKodeBarang().equals(brg.getKodeBarang()) && b.getKodeBarang().equals(kodeBarang)) {
                    jumlahLama = b.getHarga();
                    
                    if((jumlahLama + jumlahBeli) > brg.getStok()){
                        mark = 1;
                        break;
                    }
                    exists = true;
                    b.setHarga(b.getHarga() + jumlahBeli);
                    break;
                }
            }
        }

        if(mark == 1){
            return 1;
        }

        if (exists) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (int i = 0; i < barang.size(); i++) {
                    Barang bar = barang.get(i);
                    writer.println("Kode Barang: " + bar.getKodeBarang());
                    writer.println("Nama Barang: " + bar.getNamaBarang());
                    writer.println("Jumlah: " + bar.getHarga());
                    if (bar.getKodeBarang().equals(kodeBarang)) {
                        writer.println("Harga: " + (bar.getStok() / jumlahLama) * bar.getHarga());
                    } else {
                        writer.println("Harga: " + bar.getStok());
                    }
                    writer.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                
                listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
                barang = listBarang.barang;
                for (Barang b : barang) {
                    if (b.getKodeBarang().equals(kodeBarang)) {
                        barang.clear();
                        Barang newBarang = new Barang(b.getNamaBarang(), kodeBarang, b.getHarga(), jumlahBeli);
                        barang.add(newBarang);
                        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                            for (Barang bar : barang) {
                                if(jumlahBeli > b.getStok()){
                                    mark = 2;
                                    break;
                                }
                                else{
                                    writer.println("Kode Barang: " + bar.getKodeBarang());
                                    writer.println("Nama Barang: " + bar.getNamaBarang());
                                    writer.println("Jumlah: " + bar.getStok());
                                    writer.println("Harga: " + (bar.getHarga() * jumlahBeli));
                                    writer.println();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(mark == 2){
                        break;
                    }
                }
            } catch (Exception e) {

            }
        }

        return mark;
    }

    /**
     * Displays the items in the shopping cart.
     *
     * @param username The username of the customer.
     * @return The count of items in the cart.
     */
    public int lihatKeranjang(String username) {
        int count = 0;

        ListBarang listBarangAdmin = new ListBarang();
        listBarangAdmin.bacaDariFile("Admin/Barang/ListBarang.txt");
        List<Barang> barangAdmin = listBarangAdmin.barang;

        ListBarang listBarangKeranjang = new ListBarang();
        listBarangKeranjang.bacaDariFile("Customer/Cus" + username + "/Keranjang.txt");
        List<Barang> barangKeranjang = listBarangKeranjang.barang;

        System.out.println("\n" + "=".repeat(30) + " CART ITEMS " + "=".repeat(30) + "\n");

        try (PrintWriter writer = new PrintWriter(new FileWriter("Customer/Cus" + username + "/Keranjang.txt"))) {
            for (Barang b : barangKeranjang) {
                boolean existsInAdmin = false;
                for (int i = 0; i < barangAdmin.size(); i++) {
                    Barang ba = barangAdmin.get(i);
                    if (b.getKodeBarang().equals(ba.getKodeBarang())) {
                        count++;
                        existsInAdmin = true;
                        break;
                    }
                }

                if (existsInAdmin) {
                    writer.println("Kode Barang: " + b.getKodeBarang());
                    writer.println("Nama Barang: " + b.getNamaBarang());
                    writer.println("Jumlah: " + b.getHarga());
                    writer.println("Harga: " + b.getStok());
                    writer.println();

                    System.out.println("Item's code: " + b.getKodeBarang());
                    System.out.println("Item's name: " + b.getNamaBarang());
                    System.out.println("Quantity: " + b.getHarga());
                    System.out.println("Price: " + b.getStok() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(count == 0){
            System.out.println("=> Cart is empty.\n");
        }
        
        System.out.println("=".repeat(72) + "\n");
        return count;
    }

    /**
     * Edits the quantity of an item in the shopping cart.
     *
     * @param kodeBarang The code of the item to be edited.
     * @param quantity   The new quantity of the item.
     * @param username   The username of the customer.
     * @return An integer mark indicating the result of the edit:
     *         - 0: Edit successful.
     *         - 1: Quantity exceeds stock.
     *         - 2: Item code not found.
     */
    public int editBarang(String kodeBarang, int quantity, String username) {
        ListBarang admin = new ListBarang();
        admin.bacaDariFile("Admin/Barang/ListBarang.txt");
        ArrayList<Barang> barangAdmin = new ArrayList<>();
        barangAdmin = admin.barang;

        listBarang.bacaDariFile("Customer/Cus" + username + "/Keranjang.txt");
        barang = listBarang.barang;
        int jumlahLama = 0;
        int mark = 0;

        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                jumlahLama = b.getHarga();
                for(Barang brg : barangAdmin){
                    if(brg.getKodeBarang().equals(kodeBarang)){
                        if(brg.getStok() < quantity){
                            mark = 1;
                            return mark;
                        }
                    }
                }
                String fileName = "Customer/Cus" + username + "/Keranjang.txt";
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    for (int i = 0; i < barang.size() / 2; i++) {
                        Barang bar = barang.get(i);
                        writer.println("Kode Barang: " + bar.getKodeBarang());
                        writer.println("Nama Barang: " + bar.getNamaBarang());
                        if (bar.getKodeBarang().equals(kodeBarang)) {
                            writer.println("Jumlah: " + quantity);
                            writer.println("Harga: " + (bar.getStok() / jumlahLama) * quantity);
                        } else {
                            writer.println("Jumlah: " + bar.getHarga());
                            writer.println("Harga: " + bar.getStok());
                        }
                        writer.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return 2;
            }
        }

        System.out.println("\nItem's code " + kodeBarang + " is not found.\n");
        return mark;
    }

    /**
     * Checks if an item with the specified code exists in the cart.
     *
     * @param kodeBarang The code of the item to be checked.
     * @param username   The username of the customer.
     * @return True if the item exists; false otherwise.
     */
    public boolean idValidator(String kodeBarang, String username) {
        listBarang = new ListBarang();
        listBarang.bacaDariFile("Customer/Cus" + username + "/Keranjang.txt");
        barang = listBarang.barang;
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                System.out.println("\nItem's name: " + b.getNamaBarang());
                System.out.println("Quantity: " + b.getHarga());
                System.out.println("Price: " + b.getStok());
                return true;
            }
        }
        System.out.println("\nItem's code " + kodeBarang + " is not found.\n");
        return false;
    }

    /**
     * Removes an item from the shopping cart.
     *
     * @param kodeBarang The code of the item to be removed.
     * @param username   The username of the customer.
     * @return True if the item is successfully removed; false otherwise.
     */
    public boolean hapusBarang(String kodeBarang, String username) {
        listBarang = new ListBarang();
        listBarang.bacaDariFile("Customer/Cus" + username + "/Keranjang.txt");
        barang = listBarang.barang;
        Barang barangToDelete = null;
        for (Barang b : barang) {
            if (b.getKodeBarang().equals(kodeBarang)) {
                barangToDelete = b;
                break;
            }
        }
        if (barangToDelete != null) {
            barang.remove(barangToDelete);
            String fileName = "Customer/Cus" + username + "/Keranjang.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                for (Barang b : barang) {
                    writer.println("Kode Barang: " + b.getKodeBarang());
                    writer.println("Nama Barang: " + b.getNamaBarang());
                    writer.println("Jumlah: " + b.getHarga());
                    writer.println("Harga: " + b.getStok());
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

}
