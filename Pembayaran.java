import java.io.IOException;
import java.util.Scanner;

abstract public class Pembayaran {

    Scanner input = new Scanner(System.in);

    abstract public void prosesPembayaran(String username, int totalHarga);

    public static void bersihkanConsole() {
        try {
            Process process = new ProcessBuilder("cmd", "/c", "cls").inheritIO().start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
