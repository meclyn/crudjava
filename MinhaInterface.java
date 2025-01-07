import com.formdev.flatlaf.FlatDarkLaf;

public class MinhaInterface {
    public static void main(String[] args) {
        try {
            // config do tema dark
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.out.println("Ñao foi possivel aplicar o tema escuro.");
        }

        // Crie
    }
}
