package Mjork;

import javax.swing.*;
import java.awt.event.*;

/**
 * This class will be used to create the main window to login/register on the Electronics Store,
 * will contain the frame, two labels, two text fields, and two buttons (Login OR register)
 * @author Supermjork
 */

public class storeMainWindow extends JPanel {
    // First step is to write the components of the window in its constructor
    public storeMainWindow() {
        // Creating frame to hold the components
        JFrame login = new JFrame("Store Login Window");

        // Creating labels
        JLabel usernameLabel = new JLabel("User Email: ");
        JLabel passwordLabel = new JLabel("Password: ");

        // Creating text input fields
        JTextField usernameIn = new JTextField();
        JTextField passwordIn = new JTextField();

        // Creating buttons
        JButton logInButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Setting window size
        login.setSize(300, 300);

        // Setting positions for components
        usernameLabel.setBounds(30, 80, 80, 30);
        passwordLabel.setBounds(30, 120, 80, 30);

        usernameIn.setBounds(100, 80, 150, 30);
        passwordIn.setBounds(100, 120, 150, 30);

        logInButton.setBounds(30, 200, 80, 30);
        registerButton.setBounds(120, 200, 120, 30);

        // Adding components to frame
        login.add(usernameLabel);
        login.add(passwordLabel);

        login.add(usernameIn);
        login.add(passwordIn);

        login.add(logInButton);
        login.add(registerButton);

        // Setting layout and starting position
        login.setLayout(null);             // Manually setting x, y for the components
        login.setLocationRelativeTo(null); // Always starts up centred in the screen

        // Having the window show up and have it exit
        login.setVisible(true);

        login.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                login.dispose();
            }
        });
    }

    public static void main( String[] args ) {
        storeMainWindow window = new storeMainWindow();
    }
}
