import javax.swing.SwingUtilities;

import views.SwingCRDApp;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SwingCRDApp();
            }
        });
    }
}
