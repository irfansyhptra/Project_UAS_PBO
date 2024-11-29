import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Kelompok 4
 * @version 1.0
 * @since 2023-12-01
 */
public class CustomerDriver extends Driver {
    Customer akun;
    Transaksi transaksi;
    Invoice invoice;
    ListBarang listBarang;
    Keranjang keranjang;
    Scanner input = new Scanner(System.in);
    CustomerImpl customerImpl = new CustomerImpl();


    /**
     * Constructs a CustomerDriver instance with the specified customer account.
     *
     * @param akun The customer account associated with the driver.
     */
    public CustomerDriver(Customer akun) {
        this.akun = akun;
    }
    
     /**
     * Inner class implementing the CustomerDriver interface.
     * Provides concrete implementations for customer-related operations.
     */
    private class CustomerImpl implements CustomerDriver {
        
        @Override
        public void masukKeranjang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            keranjang = new Keranjang();
            String kodeBarang;
            int jumlahBeli = 0;
            // int count = 0;
            
            showBarang();
            while (true) {
                System.out.print("Input item's code that you want to add to cart: ");
                kodeBarang = input.next();
                if (listBarang.idValidator(kodeBarang)) {
                    break;
                }
            }
            while (true) {
                while (true) {
                    try {
                        System.out.print("Quantity: ");
                        jumlahBeli = input.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("\n=> Quantity input type is not valid!\n");
                        input.nextLine();
                    }
                    if(jumlahBeli > 0){
                        break;
                    }else{
                        System.out.println("\n=> Quantity have to more than 0\n");
                    }
                }
                if(keranjang.tambahBarang(kodeBarang, "Customer/Cus" + akun.getUsername() + "/Keranjang.txt", jumlahBeli) > 0){
                    System.out.println("\n=> This item's on your cart has quantity over the stock\n");
                }
                else{
                    break;
                }
            }
            
            System.out.println("\n=> Items added to cart successfully\n");
            for (int i = 0; i <= 8000; i++) {
                if (i / 2000 == 0) {
                    continue;
                }
                System.out.print("\rRedirecting ... " + i / 2000);
            }
            bersihkanConsole();
            
            
        }
        
        @Override
        public void checkoutBarang() {
            transaksi = new Transaksi();

            Path path = Paths.get("Customer/Cus" + akun.getUsername() + "/Transaksi.txt");
            try {
                byte[] fileContent = Files.readAllBytes(path);

                if (fileContent.length == 0) {
                    if(showCart()){
                        transaksi.buatTransaksi(akun.getUsername());
                        // bersihkanConsole(); 
                    }
                    else{
                        System.out.println("\n=> You have nothing to checkout, cart cannot be empty!\n");
                    }
                } else {
                    System.out.println("\n\n=> You still have a transaction on queue");
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An error occurred while checking the file.");
            }
        }
        
        @Override
        public void checkInvoice() {
            transaksi = new Transaksi();
            
            Path path = Paths.get("Customer/Cus" + akun.getUsername() + "/Invoice.txt");
            try {
                byte[] fileContent = Files.readAllBytes(path);

                if (fileContent.length == 0) {
                    System.out.println("\n=> You have not invoice yet");
                } else {
                    transaksi.cekTransaksi(akun.getUsername());
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An error occurred while checking the file.");
            }
        }
        
        @Override
        public void showBarang() {
            listBarang = new ListBarang();
            listBarang.bacaDariFile("Admin/Barang/ListBarang.txt");
            ArrayList<Barang> barang = listBarang.barang;
            System.out.println("\n" + "=".repeat(30) + " ITEMS LIST " + "=".repeat(30) + "\n");
            if (barang.isEmpty()) {
                System.out.println("=> item not found\n");
            } else {
                for (Barang b : barang) {
                    if(b.getStok() > 0){
                        System.out.println("Item's code: " + b.getKodeBarang());
                        System.out.println("Item's name: " + b.getNamaBarang());
                        System.out.println("Price: " + b.getHarga());
                        System.out.println("Stock: " + b.getStok());
                        System.out.println();
                    }
                }
            }
            System.out.println("=".repeat(72) + "\n");
        }
        
        @Override
        public boolean showCart() {
            keranjang = new Keranjang();
            if(keranjang.lihatKeranjang(akun.getUsername()) == 0){
                return false;
            }
            return true;
        }
        
        @Override
        public void editCart() {
            keranjang = new Keranjang();
            String kodeBarang;
            
            if(!showCart()){
                System.out.println("=> You have no items in your cart.");   
            }
            else{
                System.out.println("\n" + "=".repeat(30) + " EDIT ITEM ON CART " + "=".repeat(30) + "\n");
                while (true) {
                    System.out.print("Input the Item's code that you want to edit: ");
                    kodeBarang = input.next();
                    if (keranjang.idValidator(kodeBarang, akun.getUsername())) {
                        while (true){
                            int quantity;
                            while (true) {   
                                System.out.print("\nNew Quantity: ");
                                quantity = input.nextInt();
                                if(quantity > 0){
                                    break;
                                }
                                else{
                                    System.out.println("\n=> Quantity have to more than 0");
                                }
                            }
                            int count = keranjang.editBarang(kodeBarang, quantity, akun.getUsername());
                            if(count == 1){
                                System.out.println("\n=> Quantity over the stock");
                            }
                            else{
                                break;
                            }
                        }

                        System.out.println("\n=> Item's quantity edited successfully\n");
                        break;
                    }
                }
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rRedirecting ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }
        
        @Override
        public void deleteCart() {
            keranjang = new Keranjang();
            String kodeBarang;
            
            if(!showCart()){
                System.out.println("=> You have no items in your cart.");   
            }
            else{

                System.out.println("\n" + "=".repeat(30) + " DELETE ITEM ON CART " + "=".repeat(30) + "\n");
                while (true) {
                    System.out.print("Input the Item's code that you want to delete: ");
                    kodeBarang = input.next();
                    
                    if (keranjang.hapusBarang(kodeBarang, akun.getUsername())) {
                        System.out.println("\n=> Item's deleted successfully.\n");
                        break;
                    }
                    System.out.println("\n=> Item's code not found.\n");
                }
                
                for (int i = 0; i <= 8000; i++) {
                    if (i / 2000 == 0) {
                        continue;
                    }
                    System.out.print("\rRedirecting ... " + i / 2000);
                }
                bersihkanConsole();
            }
        }
    }
    
    /**
     * Runs the customer interface with various options for managing items, cart, and transactions.
     */
    public void run() {
        
        int adminInput = 10;
        
        while (true) {

            System.out.println("\n" + "=".repeat(30) + " CUSTOMER DASHBOARD " + "=".repeat(30) + "\n");
            System.out.println("Choice an option");
            System.out.println("1. Show Items");
            System.out.println("2. Show Cart");
            System.out.println("3. Add Item");
            System.out.println("4. Edit Item");
            System.out.println("5. Delete Item");
            System.out.println("6. Request Checkout");
            System.out.println("7. Invoice");
            System.out.println("0. Logout");

            while (true) {
                try {
                    System.out.print("\nInput : ");
                    adminInput = input.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("\n=> Input type must be a number!");
                    input.nextLine();
                }
                if (adminInput < 0 || adminInput > 7) {
                    System.out.println("\n=> Please enter the available number options");
                } else if (adminInput == 1) {
                    bersihkanConsole();
                    customerImpl.showBarang();
                    System.out.println("Choice an option");
                    System.out.println("1. Show Items");
                    System.out.println("2. Show Cart");
                    System.out.println("3. Add Item");
                    System.out.println("4. Edit Item");
                    System.out.println("5. Delete Item");
                    System.out.println("6. Request Checkout");
                    System.out.println("7. Invoice");
                    System.out.println("0. Logout");
                } else {
                    break;
                }
            }

            if (adminInput == 2) {
                bersihkanConsole();
                customerImpl.showCart();
            } else if (adminInput == 3) {
                bersihkanConsole();
                customerImpl.masukKeranjang();
            } else if (adminInput == 4) {
                bersihkanConsole();
                customerImpl.editCart();
            } else if (adminInput == 5) {
                bersihkanConsole();
                customerImpl.deleteCart();
            } else if (adminInput == 6) {
                bersihkanConsole();
                customerImpl.checkoutBarang();
            } else if (adminInput == 7) {
                bersihkanConsole();
                customerImpl.checkInvoice();
            } else if (adminInput == 0) {
                bersihkanConsole();
                break;
            }
        }
        System.out.println("\n=> Logout Successfully, Thank youu...\n");
    }
}
