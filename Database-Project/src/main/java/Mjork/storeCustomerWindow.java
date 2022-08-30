package Mjork;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Customer window will show the customer their search results, may display inventory items randomly(?)
 * will have the ability to place an order, and view his orders
 * @author Supermjork
 */

public class storeCustomerWindow extends JPanel {
    public storeCustomerWindow() {
        // Frame to hold components (As in all other windows)
        JFrame UI_customer = new JFrame("Customer Window");

        // Setting components
            //Buttons for user to search devices, order, and exit
        JButton searchDeviceButton = new JButton("Search");
        JTextField searchDeviceField = new JTextField();

        JButton orderDeviceButton = new JButton("Order (Enter Full ID)");
        JTextField orderDeviceField = new JTextField();

        JButton exitSession = new JButton("Log out");

            //Text area to display the results of query (By taking in from text fields and inserting into SELECT query)
        JTextArea displayDevices = new JTextArea();
        displayDevices.setEditable(false);

        JScrollPane displayContainer = new JScrollPane(displayDevices);
        displayContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

            // Setting boundaries
        displayContainer.setBounds(10, 10, 715, 200);
        displayDevices.setBounds(10, 10, 715, 200);

        searchDeviceButton.setBounds(10, 230, 175, 30);
        searchDeviceField.setBounds(10, 290, 175, 30);

        orderDeviceButton.setBounds(549, 230, 175, 30);
        orderDeviceField.setBounds(549, 290, 175, 30);

        exitSession.setBounds(750 / 3, (3 * 750) / 4, 750 / 3, 70);

            // Adding elements to Frame
        UI_customer.add(searchDeviceButton);
        UI_customer.add(searchDeviceField);

        UI_customer.add(orderDeviceButton);
        UI_customer.add(orderDeviceField);

        UI_customer.add(displayContainer);
        UI_customer.add(displayDevices);

        UI_customer.add(exitSession);

        UI_customer.setSize(750, 750);
        UI_customer.setLayout(null);
        UI_customer.setLocationRelativeTo(null);
        UI_customer.setVisible(true);

        UI_customer.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                UI_customer.dispose();
            }
        });

        searchDeviceButton.addActionListener(e -> {
            String searchDeviceStr = searchDeviceField.getText();

            if(!searchDeviceField.getText().equals("")) {
                StringBuilder searchResBuild = new StringBuilder();
                String searchResFin;

                try {
                    PreparedStatement checkExistHand = storeMainWindow.glob_connect.prepareStatement("SELECT * FROM available_handheld WHERE devicename LIKE '%" + searchDeviceStr + "%' OR brand LIKE '%" + searchDeviceStr + "%'");
                    PreparedStatement checkExistComp = storeMainWindow.glob_connect.prepareStatement("SELECT * FROM available_computer WHERE devicename LIKE '%" + searchDeviceStr + "%' OR brand LIKE '%" + searchDeviceStr + "%'");
                    PreparedStatement checkExistCam  = storeMainWindow.glob_connect.prepareStatement("SELECT * FROM available_camera WHERE devicename LIKE '%" + searchDeviceStr + "%' OR brand LIKE '%" + searchDeviceStr + "%'");

                    ResultSet resultSetHand = checkExistHand.executeQuery();
                    ResultSet resultSetComp = checkExistComp.executeQuery();
                    ResultSet resultSetCam  = checkExistCam.executeQuery();

                    System.out.println("List of Devices retrieved.. Parsing into string to display");

                    // Yes I know, the amount of .append() statements look rubbish, was short on time for deadline
                    while(resultSetHand.next()) {
                        String[] currentRow = new String[8];
                        for(int i = 1; i <= currentRow.length; i++) {
                            currentRow[i - 1] = resultSetHand.getString(i);
                        }
                        searchResBuild.append("Item ID: ").append(currentRow[0]).append("\n").append("Item Name: ")
                                .append(currentRow[1]).append("\n").append("Item Brand: ").append(currentRow[2]).append("\n")
                                .append("Item Price: ").append(currentRow[3]).append("\n").append("Review Rating: ").append(currentRow[4])
                                .append("\n").append("Handheld Type: ").append(currentRow[5]).append("\n")
                                .append("Screen size: ").append(currentRow[6]).append("\n").append("Storage: ").append(currentRow[7])
                                .append("\n\n");
                    }

                    while(resultSetComp.next()) {
                        String[] currentRow = new String[11];
                        for(int i = 1; i <= currentRow.length; i++) {
                            currentRow[i - 1] = resultSetComp.getString(i);
                        }
                        searchResBuild.append("Item ID: ").append(currentRow[0]).append("\n").append("Item Name: ")
                                .append(currentRow[1]).append("\n").append("Item Brand: ").append(currentRow[2]).append("\n")
                                .append("Item Price: ").append(currentRow[4]).append("\n").append("Review Rating: ").append(currentRow[3]).append("\n")
                                .append("CPU: ").append(currentRow[5]).append("\n").append("GPU: ").append(currentRow[6]).append("\n")
                                .append("Operating System: ").append(currentRow[7]).append("\n").append("RAM (GB): ").append(currentRow[8])
                                .append("\n").append("Storage: ").append(currentRow[9]).append("\n").append("Type: ").append(currentRow[10])
                                .append("\n\n");
                    }

                    while(resultSetCam.next()) {
                        String[] currentRow = new String[7];
                        for(int i = 1; i <= currentRow.length; i++) {
                            currentRow[i - 1] = resultSetCam.getString(i);
                        }
                        searchResBuild.append("Item ID: ").append(currentRow[0]).append("\n").append("Item Name: ")
                                .append(currentRow[1]).append("\n").append("Item Brand: ").append(currentRow[2]).append("\n")
                                .append("Item Price: ").append(currentRow[3]).append("\n").append("Review Rating: ").append(currentRow[4])
                                .append("\n").append("Focal Length: ").append(currentRow[5]).append("\n")
                                .append("Camera Type: ").append(currentRow[6]).append("\n\n");
                    }

                    System.out.println("String parsing complete.");

                    searchResFin = searchResBuild.toString();

                    if(!searchResFin.equals("")){
                        displayDevices.setText(searchResFin);
                    } else {
                        JOptionPane.showMessageDialog(UI_customer, "Device not Found");
                    }
                } catch (SQLException ex) {
                    System.out.println("Error searching:\n" + ex);
                }
            } else {
                JOptionPane.showMessageDialog(UI_customer, "Please insert a device name to search for.");
            }
        });

        orderDeviceButton.addActionListener(e -> {});

        exitSession.addActionListener(e -> {
            UI_customer.dispose();
            new storeMainWindow();
        });
    }
}
