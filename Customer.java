import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Kelompok 4
 * @version 1.0
 * @since 2023-12-01
 */

public class Customer extends Akun {
    public Keranjang keranjang;
    public ArrayList<Invoice> invoiceSelesai;
    private String fileName = "Customer/Credentials/AkunCustomer.txt";
    private int id_Customer = 0;
    private String username;

    /**
     * Sets the username for the current customer instance.
     *
     * @param username The username of the customer.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Saves customer account information to a text file and creates necessary folders and files.
     *
     * @param username The username of the customer.
     * @param password The password of the customer.
     */
    public void saveToTextFile(String username, String password) {
        // Menentukan nama file untuk menyimpan data pengguna
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            int id = id_Customer += 1;
            
            writer.println("id: " + id);
            writer.println("Username: " + username);
            writer.println("Password: " + password);
            writer.println(); // Tambahkan baris kosong antara setiap entri pengguna
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String folderName = "Cus" + username;

        File directory = null;
        File keranjangFile = null;
        File invoiceFile = null;
        File transaksiFile = null;

        try {
            // Create the Customer folder if it doesn't exist
            directory = new File("Customer", folderName);
            directory.mkdirs();
                
            // Create Keranjang.txt file
            keranjangFile = new File(directory, "Keranjang.txt");
            keranjangFile.createNewFile();
                
            // Create Invoice.txt file
            invoiceFile = new File(directory, "Invoice.txt");
            invoiceFile.createNewFile();
                
            // Create Transaksi.txt file
            transaksiFile = new File(directory, "Transaksi.txt");
            transaksiFile.createNewFile();
                
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat mencoba membuat file.");
            e.printStackTrace();
        }
        
    }
    
    
    /**
     * Reads customer accounts from the text file.
     *
     * @return A list containing customer account information.
     */
    @Override
    public List<String> readCustomerAccounts() {
        List<String> accounts = new ArrayList<>();
        
        
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String id = scanner.nextLine().split(":")[1].trim();
                String username = scanner.nextLine().split(":")[1].trim();
                String password = scanner.nextLine().split(":")[1].trim();
                
                id_Customer = Integer.parseInt(id);
                accounts.add(username);
                accounts.add(password);
                
                scanner.nextLine();
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan");;
        }
        
        return accounts;
    }
    
    /**
     * Validates customer sign-in credentials.
     *
     * @param username The entered username for sign-in.
     * @param password The entered password for sign-in.
     * @return True if the provided credentials are valid, false otherwise.
     */
    @Override
    public boolean validateSignIn(String username, String password) {
        
        List<String> accounts = readCustomerAccounts();
        
        for (int i = 0; i < accounts.size(); i += 2) {
            String uname = accounts.get(i);
            String pword = accounts.get(i + 1);
            
            if (username.equals(uname) && password.equals(pword)) {
                this.setUsername(username);
                return true;
            }
        }
        return false;
        
    }
    
    /**
     * Validates customer sign-up credentials.
     *
     * @param username The entered username for sign-up.
     * @param password The entered password for sign-up.
     * @return An integer code indicating the result of the validation:
     *         - 0: Account already exists.
     *         - 1: Username already exists.
     *         - 2: Sign-up successful.
     */
    public int validateSignUp(String username, String password) {
        List<String> accounts = readCustomerAccounts();
        
        
        for (int i = 0; i < accounts.size(); i += 2) {
            String uname = accounts.get(i);
            String pword = accounts.get(i + 1);
            
            if (username.equals(uname) && password.equals(pword)) {
                System.out.println("\n=> You already have an account earlier\n");
                return 0;
            }
            if (username.equals(uname)) {
                return 1;
            }
        }
        return 2;
        
    }
    
    /** 
     * Gets the username of the current customer instance.
     *
     * @return The username of the customer.
     */
    public String getUsername() {
        return this.username;
    }
}
