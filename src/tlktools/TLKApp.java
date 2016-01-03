package tlktools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EDDY
 */
public class TLKApp {

    static String password;

    static private JFrame f;
    static private JPanel p1;
    static private JPanel p2;
    static private JButton b1;
    //static private JButton b2;
    static private JLabel lab1;
    //static private JLabel lab2;
    static private String[] items = {"mTLK", "aTLK", "rTLK"};
    static String uName;
    static String pWord;
    static private JComboBox comboBox1 = new JComboBox(items);

    public TLKApp() {
        gui();
    }

    public static void frame() {
        f = new JFrame("TLKTools");
        f.setVisible(true);
        f.setSize(600, 400);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel();
        loginPanel();
        f.setJMenuBar(addMenuBar());
        //f.add(p1, BorderLayout.EAST);
        f.add(p2);
    }

    /**
     * Returns a default menu bar
     *
     * @return
     */
    public static JMenuBar addMenuBar() {
        JMenuBar mb = new JMenuBar();
        // Define Menu buttons
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu view = new JMenu("View");
        JMenu tools = new JMenu("Tools");
        JMenu help = new JMenu("Help");

        // Define Actions
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add Actions to Menu buttons
        file.add(exit);
//        file.add(exit);
//        file.add(exit);
//        file.add(exit);
//        file.add(exit);

        mb.add(file);
        mb.add(edit);
        mb.add(view);
        mb.add(tools);
        mb.add(help);

        return mb;
    }

    public static void panel() {
        p1 = new JPanel(new GridBagLayout());
//        p.setBackground(Color.YELLOW);
        b1 = new JButton("Print");
//        b2 = new JButton("Print");
        lab1 = new JLabel("Display Combo");
//        lab2 = new JLabel("Test Label 2");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = comboBox1.getSelectedItem().toString();
                lab1.setText(s);
            }
        });

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 2;
        c.gridy = 0;
        p1.add(b1, c);
        c.gridx = 1;
        c.gridy = 0;
        p1.add(lab1, c);
        c.gridx = 0;
        c.gridy = 0;
        p1.add(comboBox1, c);
    }

    public static void loginPanel() {
        p2 = new JPanel(new GridBagLayout());
        //set up components
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel successLabel = new JLabel("");
        JTextField usernameTextField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uName = usernameTextField.getText();
                pWord = passwordField.getText();
                System.out.println("Username: " +uName+"\n Password: "+pWord);
                boolean loggedIn = Login.login(uName, pWord);
                if (loggedIn) {
                    successLabel.setText("Login Successful");
                } else {
                    successLabel.setText("Incorrect! Please Retry");
                    loginPanel();
                }
                
            }
        });
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 10, 0, 10);
        c.gridx = -2;
        p2.add(usernameLabel, c);
        c.gridx = 1;
        p2.add(passwordLabel, c);
        c.gridx = -1;
        p2.add(usernameTextField, c);
        c.gridx = 2;
        p2.add(passwordField, c);
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 1;
        c.gridy = 3;
        p2.add(loginButton, c);
        c.insets = new Insets(5, 5, 5, 5);
        c.gridx = 2;
        c.gridy = 3;
        p2.add(successLabel, c );

        //change the echo character if desired
        passwordField.setEchoChar('*');

    }

    public static void gui() {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
        frame();
    }

    public static void main(String[] args) {
        new TLKApp();
    }
}
