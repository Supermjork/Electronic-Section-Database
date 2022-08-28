package Mjork;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class storeRegisterWindow extends JPanel {
    public storeRegisterWindow() {
        // Creating frame
        JFrame registration = new JFrame("Registration Window");
        registration.setSize(650,350);

        // Labels to indicate needed information
        // Username and Password
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        // Delivery Address
        JLabel address_zipcode  = new JLabel("Zipcode: ");
        JLabel address_district = new JLabel("District: ");
        JLabel address_city     = new JLabel("City: ");

        // Text fields to collect user input
        JTextField usernameIn = new JTextField();
        JTextField passwordIn = new JTextField();

        JTextField address_cityIn       = new JTextField();
        JTextField address_districtIn   = new JTextField();
        JTextField address_zipcodeIn    = new JTextField();

        // Buttons to return/register
        JButton returnButton   = new JButton("Return");
        JButton registerButton = new JButton("Register");

        // Setting bounds for each component
        // All labels start their x at 50
            // y increments by 50 starting from 20
            // All widths are 200, heights 30
        usernameLabel.setBounds(50, 20, 200, 30);
        passwordLabel.setBounds(50, 70, 200, 30);

        address_city.setBounds(50, 120, 200, 30);
        address_district.setBounds(50, 170, 200, 30);
        address_zipcode.setBounds(50, 220, 200, 30);

        // All text fields start their x at 250
            // y increments by 50 starting from 20
            // All widths are 300, height 30
        usernameIn.setBounds(250, 20, 300, 30);
        passwordIn.setBounds(250, 70, 300, 30);

        address_cityIn.setBounds(250, 120, 300, 30);
        address_districtIn.setBounds(250, 170, 300, 30);
        address_zipcodeIn.setBounds(250, 220, 300, 30);

        // Buttons will be below fields/labels
        returnButton.setBounds(50, 270,100, 30);
        registerButton.setBounds(200, 270, 100, 30);

        // Adding components to frame
        registration.add(usernameLabel);
        registration.add(passwordLabel);

        registration.add(address_city);
        registration.add(address_district);
        registration.add(address_zipcode);

        registration.add(usernameIn);
        registration.add(passwordIn);

        registration.add(address_cityIn);
        registration.add(address_districtIn);
        registration.add(address_zipcodeIn);

        registration.add(returnButton);
        registration.add(registerButton);

        // Setting layout and screen position
        registration.setLayout(null);
        registration.setVisible(true);
        registration.setLocationRelativeTo(null);

        registration.addWindowListener(new WindowAdapter() {
            // closing on clicking "x" top right
            public void windowClosing(WindowEvent e) {
                registration.dispose();
            }
        });

        returnButton.addActionListener(e -> {
            // returning to main window
            registration.dispose();
            new storeMainWindow();
        });
    }
}
