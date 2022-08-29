package Mjork;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

/**
 * Customer-only window made to register onto the database, Admins will be hardcoded,
 * Sellers will only be added through requests to admins (Won't implement due to time limitations).
 * Customers will enter their main info (Select username, password, address split into 3 fields) checked for nullity,
 * Customer ID will be generated serially as they get added into the database.
 * @author Supermjork
 */

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

        // All text fields start their x at (label + 200)
            // y = labelY
            // All widths are 300, height = labelHeight
        usernameIn.setBounds(usernameLabel.getX() + 200, usernameLabel.getY(), 300, usernameLabel.getHeight());
        passwordIn.setBounds(passwordLabel.getX() + 200, passwordLabel.getY(), 300, passwordLabel.getHeight());

        address_cityIn.setBounds(address_city.getX() + 200, address_city.getY(), 300, address_city.getHeight());
        address_districtIn.setBounds(address_district.getX() + 200, address_district.getY(), 300, address_district.getHeight());
        address_zipcodeIn.setBounds(address_zipcode.getX() + 200, address_zipcode.getY(), 300, address_zipcode.getHeight());

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
            // Closing on clicking "x" top right
            public void windowClosing(WindowEvent e) {
                registration.dispose();
            }
        });

        returnButton.addActionListener(e -> {
            // Returning to main window
            registration.dispose();
            new storeMainWindow();
        });

        registerButton.addActionListener(e -> {
            // Code to first check nullity of the data fields (No field is empty (grab from my library project)),
            // after checking, the pulled data will be put into an SQL INSERT INTO method
            // and pushed into the "customers" table (after successful insertion, auto swap windows to main)
            // of course after checking that the user does not already exist.
            if(usernameIn.getText().equals("") || passwordIn.getText().equals("") || address_cityIn.getText().equals("") || address_districtIn.getText().equals("") || address_zipcodeIn.getText().equals("")) {
                JOptionPane.showMessageDialog(registration, "Please fill in the fields");
            } else {
                String username = usernameIn.getText();
                String userPass = passwordIn.getText();

                String userAddress_city = address_cityIn.getText();
                String userAddress_district = address_districtIn.getText();
                String userAddress_zipcode = address_zipcodeIn.getText();

                // [Done]Try-catch block for db insertion, if successful JOption pane for Success, changes windows,
                //       if unsuccessful, JOption pane for failure, stays on window.

                try {
                    PreparedStatement checkExist = storeMainWindow.glob_connect.prepareStatement("SELECT * FROM customer WHERE username = '" + username + "'");
                    ResultSet resSet = checkExist.executeQuery();

                    if(resSet.isBeforeFirst()) {
                        System.out.println("Error signing up: Username already exists.");
                        JOptionPane.showMessageDialog(registration, "Username already exists, Select another username.");
                    } else {
                        PreparedStatement insertUser = storeMainWindow.glob_connect.prepareStatement(
                                "INSERT INTO customer VALUES (DEFAULT, '" + username + "','"
                                + userPass + "','" + userAddress_city + "','" + userAddress_district + "','"
                                + userAddress_zipcode + "')");
                        insertUser.executeUpdate();

                        JOptionPane.showMessageDialog(registration, "Successful Registration");
                        registration.dispose();
                        new storeMainWindow();
                    }
                } catch (SQLException ex) {
                    System.out.println("Registration Error:\n" +ex);
                    JOptionPane.showMessageDialog(registration, "Unsuccessful Registration");
                }
            }
        });
    }
}
