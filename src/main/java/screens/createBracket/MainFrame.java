package screens.createBracket;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTextField tfFirstName;
    private JTextField tfLastName;
    private JButton btnOK;
    private JButton btnClear;
    private JPanel mainPanel;

    public MainFrame() {
        setContentPane(mainPanel);
        setTitle("Welcome");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();

    }

}
