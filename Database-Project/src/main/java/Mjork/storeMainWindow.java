package Mjork;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class will be used to create the main window to login/register on the Electronics Store,
 * will contain the frame, two labels, two text fields, and two buttons (Login OR register),
 * the login sequence will go through the radio button's respective table, to check for credentials
 * will require the user to fill both fields.
 * After which, will dispose of the current window and pop up another of the user's clearance.
 * @author Supermjork
 */

public class storeMainWindow extends JPanel {
    // Defining database connection as global variable for later use
    protected static Connection glob_connect;

    // Writing the components of the window in its constructor
    public storeMainWindow() {
        // Creating frame to hold the components
        JFrame login = new JFrame("Store Login Window");
        // Setting window size
        login.setSize(300, 300);

        // Creating labels
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        JLabel radioLabelCus = new JLabel("Customer");
        JLabel radioLabelSel = new JLabel("Seller");
        JLabel radioLabelAdm = new JLabel("Admin");

        // Creating text input fields
        JTextField usernameIn = new JTextField();
        JTextField passwordIn = new JTextField();

        // Creating radio buttons
        JRadioButton isCustomer = new JRadioButton();
        JRadioButton isSeller   = new JRadioButton();
        JRadioButton isAdmin    = new JRadioButton();

        // Creating radio button group (To select only one)
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(isCustomer);
        radioGroup.add(isSeller);
        radioGroup.add(isAdmin);

        // Creating buttons
        JButton logInButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Setting positions for components
        usernameLabel.setBounds(30, 80, 80, 30);
        passwordLabel.setBounds(30, 120, 80, 30);

        radioLabelCus.setBounds(50,150, 60, 30);
        isCustomer.setBounds(radioLabelCus.getX() + radioLabelCus.getWidth() + 5,
                             radioLabelCus.getY(), 20, radioLabelCus.getHeight());

        radioLabelSel.setBounds(isCustomer.getX() + isCustomer.getWidth() + 5,
                                radioLabelCus.getY(), 35, 30);
        isSeller.setBounds(radioLabelSel.getX() + radioLabelSel.getWidth() + 5,
                           radioLabelSel.getY(), 20, radioLabelSel.getHeight());

        radioLabelAdm.setBounds(((radioLabelCus.getX() + isSeller.getX() + radioLabelSel.getWidth()) / 3),
                             180, 60, 30);
        isAdmin.setBounds(radioLabelAdm.getX() + radioLabelAdm.getWidth() + 5,
                          radioLabelAdm.getY(), 20, radioLabelAdm.getHeight());

        usernameIn.setBounds(usernameLabel.getX() + 70, usernameLabel.getY(), 150, 30);
        passwordIn.setBounds(passwordLabel.getX() + 70, passwordLabel.getY(), 150, 30);

        logInButton.setBounds(30, 220, 100, 30);
        registerButton.setBounds(logInButton.getX() + 110, logInButton.getY(), 110, 30);

        // Adding components to frame
        login.add(usernameLabel);
        login.add(passwordLabel);

        login.add(radioLabelCus);
        login.add(radioLabelSel);
        login.add(radioLabelAdm);

        login.add(usernameIn);
        login.add(passwordIn);

        login.add(isCustomer);
        login.add(isSeller);
        login.add(isAdmin);

        login.add(logInButton);
        login.add(registerButton);

        // Setting layout and starting position
        login.setLayout(null);                  // Manually setting x, y for the components
        login.setLocationRelativeTo(null);      // Always starts up centred in the screen

        // Having the window show up and have it exit
        login.setVisible(true);

        login.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                login.dispose();
            }
        });

        registerButton.addActionListener(e -> {
            login.dispose();
            new storeRegisterWindow();
        });

        // [ ]Code to check which user type (Customer, Seller, Admin) is logging in goes here,
        //   still don't know how it will know which table to search through, maybe radio button to indicate?
        //   after successful login, dispose of current window and instantiate a window object of
        //   the respective user type (new {AdminWindow, CustomerWindow, SellerWindow})
        // [ ]Also need to add a registration window, for customers only, i.e. output goes into customers' table
        // [ ]Dunno where to put the connection, if it's only on this window or do we connect on each window?
    }

    public static void main(String[] args) {
        // main method is needed to first instantiate the main window, rest follows through dispose and instantiation
        // !disabled for the purpose of testing connection!
        //new storeMainWindow();

        // Write code to establish db connection here, (after merging with main i.e. after addition of JDBC jar)
        // Need to instantiate a Connection object, then store URL, Username, Password in variables (Security flaw).
        Connection connection;

        // url format: "jdbc:<db-type*>://<hostname>:<port>/<db-name>
        // db-type*: full name for database type as postgresql, etc.
        String dbURL    = "";
        String username = "";
        String password = "";

        try {
            connection = DriverManager.getConnection(dbURL, username, password);
            if(connection != null) {
                System.out.println("Connection Established.");
                glob_connect = connection;
            } else {
                System.out.println("Connection Failed.");
            }
        } catch (Exception e) {
            System.out.println("Connection Failed due to:\n" + e);
        }
    }
}
