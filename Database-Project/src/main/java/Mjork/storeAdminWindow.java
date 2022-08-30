package Mjork;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Admin window will show all the items in the inventory, can remove items by ID,
 * will show listings waiting for approval
 * @author Supermjork
 */

public class storeAdminWindow extends JPanel {
    public storeAdminWindow() {
        // Creating the frame
        JFrame UI_admin = new JFrame("Admin Window");
        UI_admin.setSize(750, 500);

        // Adding components
            // Labels
        JLabel label_Unapproved = new JLabel("Unapproved Item ID");
        JLabel label_Approved   = new JLabel("Approved Item ID");

            // Buttons
        JButton button_Approval = new JButton("Approve");
        JButton button_Unapproved = new JButton("Terminate");

        JButton button_refresh = new JButton("Refresh Listings");
        JButton exitSession = new JButton("Logout");

            // Text fields
        JTextField tField_approve = new JTextField();
        JTextField tField_Unapproved = new JTextField();

            // Text Area to display list of yet unapproved items/All items
        JTextArea displayDevices = new JTextArea();
        displayDevices.setEditable(false);

        JScrollPane displayContainer = new JScrollPane(displayDevices);
        displayContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

            // Setting boundaries
        displayContainer.setBounds(10, 10, 715, 200);
        displayDevices.setBounds(10, 10, 715, 200);

        button_Approval.setBounds(155, 290, 150, 30);
        tField_approve.setBounds(155, 230, 150, 30);
        label_Approved.setBounds(10, 230, 145, 30);

        button_Unapproved.setBounds(520, 290, 175, 30);
        tField_Unapproved.setBounds(520, 230, 175, 30);
        label_Unapproved.setBounds(375, 230, 145, 30);

        button_refresh.setBounds(750 / 3, 500 / 2, 750 / 3, 30);
        exitSession.setBounds(750 / 3, (3 * 500) / 4, 750 / 3, 30);

            // Adding components to frame
        UI_admin.add(displayContainer);
        UI_admin.add(displayDevices);

        UI_admin.add(button_Approval);
        UI_admin.add(label_Approved);
        UI_admin.add(tField_approve);

        UI_admin.add(button_Unapproved);
        UI_admin.add(tField_Unapproved);
        UI_admin.add(label_Unapproved);

        UI_admin.add(button_refresh);
        UI_admin.add(exitSession);

        UI_admin.setLayout(null);
        UI_admin.setLocationRelativeTo(null);
        UI_admin.setVisible(true);

        UI_admin.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                UI_admin.dispose();
            }
        });

        exitSession.addActionListener(e -> {
            UI_admin.dispose();
            new storeMainWindow();
        });

        button_refresh.addActionListener(e -> {
            StringBuilder listingBuild = new StringBuilder();
            String refreshedListings;

            try {
                PreparedStatement retrieve_UnAppHand = storeMainWindow.glob_connect.prepareStatement("SELECT * FROM handheld WHERE approvalstatus = FALSE");
                PreparedStatement retrieve_UnAppComp = storeMainWindow.glob_connect.prepareStatement("SELECT * FROM computer WHERE approvalstatus = FALSE");
                PreparedStatement retrieve_UnAppCams = storeMainWindow.glob_connect.prepareStatement("SELECT * FROM camera WHERE approvalstatus = FALSE");

                ResultSet resultHand = retrieve_UnAppHand.executeQuery();
                ResultSet resultComp = retrieve_UnAppComp.executeQuery();
                ResultSet resultCams = retrieve_UnAppCams.executeQuery();

                System.out.println("List of Unapproved devices retrieved.. parsing into displayable string");

                while(resultHand.next()) {
                    String[] currentRow = new String[9];
                    for(int i = 1; i <= currentRow.length; i++) {
                        currentRow[i - 1] = resultHand.getString(i);
                    }
                    listingBuild.append("Item ID: ").append(currentRow[0]).append("\n")
                            .append("Item Name: ").append(currentRow[1]).append("\n")
                            .append("Item Brand: ").append(currentRow[2]).append("\n")
                            .append("Item Price: ").append(currentRow[3]).append("\n")
                            .append("Item Review Rating: ").append(currentRow[4]).append("\n")
                            .append("Item Type: ").append(currentRow[5]).append("\n")
                            .append("Screen Size: ").append(currentRow[6]).append("\n")
                            .append("Storage: ").append(currentRow[7]).append("\n")
                            .append("Listed By: ").append(currentRow[8]).append("\n\n");
                }

                while(resultComp.next()) {
                    String[] currentRow = new String[12];
                    for(int i = 1; i <= currentRow.length; i++) {
                        currentRow[i - 1] = resultComp.getString(i);
                    }
                    listingBuild.append("Item ID: ").append(currentRow[0]).append("\n")
                            .append("Item Name: ").append(currentRow[1]).append("\n")
                            .append("Item Brand: ").append(currentRow[2]).append("\n")
                            .append("Item Price: ").append(currentRow[4]).append("\n")
                            .append("Item Review Rating: ").append(currentRow[3]).append("\n")
                            .append("CPU: ").append(currentRow[5]).append("\n")
                            .append("GPU: ").append(currentRow[6]).append("\n")
                            .append("Operating System: ").append(currentRow[7]).append("\n")
                            .append("RAM Size: ").append(currentRow[8]).append("\n")
                            .append("Storage Size: ").append(currentRow[9]).append("\n")
                            .append("Computer Type: ").append(currentRow[10]).append("\n")
                            .append("Listed By: ").append(currentRow[11]).append("\n\n");
                }

                while(resultCams.next()) {
                    String[] currentRow = new String[8];
                    for(int i = 1; i <= currentRow.length; i++) {
                        currentRow[i - 1] = resultCams.getString(i);
                    }
                    listingBuild.append("Item ID: ").append(currentRow[0]).append("\n")
                            .append("Item Name: ").append(currentRow[1]).append("\n")
                            .append("Item Brand: ").append(currentRow[2]).append("\n")
                            .append("Item Price: ").append(currentRow[3]).append("\n")
                            .append("Item Review Rating: ").append(currentRow[4]).append("\n")
                            .append("Focal length: ").append(currentRow[5]).append("\n")
                            .append("Camera Type: ").append(currentRow[6]).append("\n")
                            .append("Listed By: ").append(currentRow[7]).append("\n\n");
                }

                System.out.println("String parse complete.");

                refreshedListings = listingBuild.toString();

                if(!refreshedListings.equals("")) {
                    displayDevices.setText(refreshedListings);
                } else {
                    JOptionPane.showMessageDialog(UI_admin, "No Unapproved Listings.");
                }
            } catch (SQLException ex) {
                System.out.println("Error retrieving unapproved devices.");
            }
        });

        button_Approval.addActionListener(e -> {
            if(!tField_approve.getText().equals("")) {
                String approved_ID = tField_approve.getText();

                if(approved_ID.charAt(0) == 'h') {
                    try {
                        PreparedStatement handHeld_approve = storeMainWindow.glob_connect.prepareStatement("UPDATE handheld SET approvalstatus = TRUE, approvedby = " + storeMainWindow.user_id + " WHERE inventory_id = " + approved_ID + ";");
                        handHeld_approve.executeUpdate();

                        JOptionPane.showMessageDialog(UI_admin, "Successfully approved handheld.");
                    } catch (SQLException ex) {
                        System.out.println("Error updating row in handheld Table:\n" + ex);
                    }
                } else if(approved_ID.charAt(0) == 'c') {
                    if(approved_ID.charAt(1) == 'a') {
                        try {
                            PreparedStatement camera_approve = storeMainWindow.glob_connect.prepareStatement("UPDATE camera SET approvalstatus = TRUE, approvedby = " + storeMainWindow.user_id + " WHERE inventory_id = " + approved_ID + ";");
                            camera_approve.executeUpdate();

                            JOptionPane.showMessageDialog(UI_admin, "Successfully approved camera.");
                        } catch (SQLException ex) {
                            System.out.println("Error updating row in camera:\n" + ex);
                        }
                    } else if(approved_ID.charAt(1) == 'o') {
                        try {
                            PreparedStatement computer_approve = storeMainWindow.glob_connect.prepareStatement("UPDATE computer SET approvalstatus = TRUE, approvedby = " + storeMainWindow.user_id + " WHERE inventory_id = " + approved_ID + ";");
                            computer_approve.executeUpdate();

                            JOptionPane.showMessageDialog(UI_admin, "Successfully approved computer");
                        } catch (SQLException ex) {
                            System.out.println("Error updating row in computer:\n" + ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(UI_admin, "Check ID prefix (letters before \"-\")");
                }
            } else {
                JOptionPane.showMessageDialog(UI_admin, "Please insert an ID to approve.");
            }
        });

        button_Unapproved.addActionListener(e -> {
            if(!tField_Unapproved.getText().equals("")) {
                String unapproved_ID = tField_Unapproved.getText();

                if(unapproved_ID.charAt(0) == 'h') {
                    try {
                        PreparedStatement handHeld_Terminate = storeMainWindow.glob_connect.prepareStatement("DELETE FROM handheld WHERE inventory_id = " + unapproved_ID + ";");
                        handHeld_Terminate.executeUpdate();

                        JOptionPane.showMessageDialog(UI_admin, "Successfully terminated handheld.");
                    } catch (SQLException ex) {
                        System.out.println("Error removing record in handheld:\n" + ex);
                    }
                } else if(unapproved_ID.charAt(0) == 'c') {
                    if(unapproved_ID.charAt(1) == 'a') {
                        try {
                            PreparedStatement camera_terminate = storeMainWindow.glob_connect.prepareStatement("DELETE FROM camera WHERE inventory_id = " + unapproved_ID + ";");
                            camera_terminate.executeUpdate();

                            JOptionPane.showMessageDialog(UI_admin, "Successfully terminated camera.");
                        } catch (SQLException ex) {
                            System.out.println("Error removing record in camera:\n" + ex);
                        }
                    } else if(unapproved_ID.charAt(1) == 'o') {
                        try {
                            PreparedStatement computer_terminate = storeMainWindow.glob_connect.prepareStatement("DELETE FROM computer WHERE inventory_id = " + unapproved_ID + ";");
                            computer_terminate.executeUpdate();

                            JOptionPane.showMessageDialog(UI_admin, "Successfully terminated computer");
                        } catch (SQLException ex) {
                            System.out.println("Error removing record in computer:\n" + ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(UI_admin, "Check ID prefix (letters before \"-\")");
                }
            } else {
                JOptionPane.showMessageDialog(UI_admin, "Please insert an ID to terminate.");
            }
        });
    }
}
