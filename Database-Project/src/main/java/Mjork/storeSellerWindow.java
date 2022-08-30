package Mjork;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Seller window will show the seller fields, so they could add an electronic device into the inventory
 * @author Supermjork
 */

public class storeSellerWindow extends JPanel {
    public storeSellerWindow() {
        // Creating Frame
        JFrame sellerUI = new JFrame("Seller Window");
        sellerUI.setSize(600, 600);

        // Setting components
            // Radio Button
        JRadioButton isHandheld = new JRadioButton();
        JRadioButton isCamera = new JRadioButton();
        JRadioButton isComputer = new JRadioButton();

        JLabel radioLabel_hand = new JLabel("Handheld");
        JLabel radioLabel_cam = new JLabel("Camera");
        JLabel radioLabel_comp = new JLabel("Computer");

        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(isHandheld);
        typeGroup.add(isCamera);
        typeGroup.add(isComputer);

            //Buttons
        JButton deviceAdd = new JButton("List Item");
        JButton exitSession = new JButton("Log out");

        // Required fields to list a device (All attributes of devices)
            // Common Attributes
        JLabel device_Name = new JLabel("Device Name");
        JLabel device_Brand = new JLabel("Device Brand");
        JLabel device_Price = new JLabel("Device Price");
        JLabel device_Type = new JLabel("Device Type");
        JLabel device_Storage = new JLabel("Storage"); // Common Between Handheld and Computers
            // Device Specific (Camera)
        JLabel camera_FocLen = new JLabel("Camera Focal Length");
            // Device Specific (Handheld)
        JLabel handheld_screen = new JLabel("Handheld Screen");
            // Device Specific (Computers)
        JLabel computer_cpu = new JLabel("Computer CPU");
        JLabel computer_gpu = new JLabel("Computer GPU");
        JLabel computer_opSys = new JLabel("Operating System");
        JLabel computer_ram = new JLabel("Computer RAM");

            // Common Attributes' Fields
        JTextField nameIn = new JTextField();
        JTextField brandIn = new JTextField();
        JTextField priceIn = new JTextField();
        JTextField typeIn = new JTextField();
        JTextField storageIn = new JTextField();

            // Device Specific Fields (Camera)
        JTextField focLenIn = new JTextField();

            // Device Specific Fields (Handheld)
        JTextField screenIn = new JTextField();

            // Device Specific Fields (Computers)
        JTextField cpuIn = new JTextField();
        JTextField gpuIn = new JTextField();
        JTextField opSysIn = new JTextField();
        JTextField ramIn = new JTextField();

        // Setting components' boundaries
        device_Name.setBounds(10, 10, 120, 30);
        nameIn.setBounds(160, 10, 410, 30);

        device_Brand.setBounds(10, 50, 120, 30);
        brandIn.setBounds(160, 50, 410, 30);

        device_Price.setBounds(10, 90, 120, 30);
        priceIn.setBounds(160, 90, 410, 30);

        device_Type.setBounds(10, 130, 120, 30);
        typeIn.setBounds(160, 130, 410, 30);

        device_Storage.setBounds(10, 170, 120, 30);
        storageIn.setBounds(160, 170, 410, 30);

        camera_FocLen.setBounds(10, 210, 120, 30);
        focLenIn.setBounds(160, 210, 120, 30);

        handheld_screen.setBounds(10, 250, 120, 30);
        screenIn.setBounds(160, 250, 120, 30);

        computer_cpu.setBounds(10, 290, 120, 30);
        cpuIn.setBounds(160, 290, 120, 30);
        computer_gpu.setBounds(310, 290, 120, 30);
        gpuIn.setBounds(450, 290, 120, 30);
        computer_ram.setBounds(10, 330, 120, 30);
        ramIn.setBounds(160, 330, 120, 30);
        computer_opSys.setBounds(310, 330, 120, 30);
        opSysIn.setBounds(450, 330, 120, 30);

        radioLabel_hand.setBounds(10, 360, 80, 30);
        isHandheld.setBounds(100, 360, 20, 30);

        radioLabel_cam.setBounds(130, 360, 80, 30);
        isCamera.setBounds(220, 360, 20, 30);

        radioLabel_comp.setBounds(260, 360, 80, 30);
        isComputer.setBounds(350, 360, 20, 30);

        exitSession.setBounds(10, 450, 120, 30);

        deviceAdd.setBounds(140, 450, 120, 30);

        // Adding components
            // Common
        sellerUI.add(device_Name);
        sellerUI.add(nameIn);
        sellerUI.add(device_Brand);
        sellerUI.add(brandIn);
        sellerUI.add(device_Price);
        sellerUI.add(priceIn);
        sellerUI.add(device_Type);
        sellerUI.add(typeIn);
        sellerUI.add(device_Storage);
        sellerUI.add(storageIn);

            // Camera
        sellerUI.add(camera_FocLen);
        sellerUI.add(focLenIn);

            // Handheld
        sellerUI.add(handheld_screen);
        sellerUI.add(screenIn);

            // Computer
        sellerUI.add(computer_cpu);
        sellerUI.add(cpuIn);
        sellerUI.add(computer_gpu);
        sellerUI.add(gpuIn);
        sellerUI.add(computer_ram);
        sellerUI.add(ramIn);
        sellerUI.add(computer_opSys);
        sellerUI.add(opSysIn);

            // Radio Buttons
        sellerUI.add(radioLabel_hand);
        sellerUI.add(isHandheld);
        sellerUI.add(radioLabel_cam);
        sellerUI.add(isCamera);
        sellerUI.add(radioLabel_comp);
        sellerUI.add(isComputer);

            // Buttons
        sellerUI.add(exitSession);
        sellerUI.add(deviceAdd);

        sellerUI.setLayout(null);
        sellerUI.setLocationRelativeTo(null);
        sellerUI.setVisible(true);

        sellerUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                sellerUI.dispose();
            }
        });

        exitSession.addActionListener(e -> {
            sellerUI.dispose();
            new storeMainWindow();
        });

        deviceAdd.addActionListener(e -> {
            // For all insert queries "INSERT INTO  VALUES (Default, dev_name, dev_brand, dev_review ,dev_price, ...)
            if(!(nameIn.toString().equals("") || brandIn.toString().equals("") || priceIn.toString().equals("") || typeIn.toString().equals("") || storageIn.toString().equals(""))) {
                String dev_name   = nameIn.getText();
                String dev_brand  = brandIn.getText();
                String dev_price  = priceIn.getText();
                String dev_type   = typeIn.getText();
                String dev_storage= storageIn.getText();
                int dev_review    = 2;

                if(isCamera.isSelected()) {
                    if(!focLenIn.toString().equals("")) {
                        try {
                            PreparedStatement camInsert = storeMainWindow.glob_connect.prepareStatement("INSERT INTO camera VALUES (DEFAULT, '" +
                                                          dev_name + "', '" + dev_brand + "', " + dev_price + ", " + dev_review + ", " +
                                                          focLenIn.getText() + ", '" + dev_type + "', " + storeMainWindow.user_id
                                                          + ", FALSE, NULL);");
                            camInsert.executeUpdate();

                            JOptionPane.showMessageDialog(sellerUI, "Camera successfully added.");
                        } catch (SQLException ex) {
                            System.out.println("Error whilst adding camera:\n" + ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(sellerUI, "Enter camera info");
                    }
                } else if(isHandheld.isSelected()) {
                    if(!(screenIn.toString().equals("") || storageIn.toString().equals(""))) {
                        try {
                            PreparedStatement handHeldInsert = storeMainWindow.glob_connect.prepareStatement("INSERT INTO handheld VALUES (DEFAULT, '" +
                                                               dev_name + "', '" + dev_brand + "', " + dev_price + ", " + dev_review + ", '" + dev_type +
                                                               "', " + screenIn.getText() + ", " + dev_storage + ", " +
                                                               storeMainWindow.user_id + ", FALSE, NULL);");
                            handHeldInsert.executeUpdate();

                            JOptionPane.showMessageDialog(sellerUI, "Handheld device successfully added.");
                        } catch (SQLException ex) {
                            System.out.println("Error whilst adding Handheld: \n" + ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(sellerUI, "Enter Handheld information");
                    }
                } else if(isComputer.isSelected()) {
                    if(!(cpuIn.toString().equals("") || gpuIn.toString().equals("") || opSysIn.toString().equals("") || ramIn.toString().equals("") || storageIn.toString().equals("")))  {
                        try {
                            PreparedStatement computerInsert = storeMainWindow.glob_connect.prepareStatement(
                                    "INSERT INTO computer VALUES (DEFAULT, '" + dev_name + "', '" + dev_brand + "', "
                                    + dev_review + ", " + dev_price + ", '" + cpuIn.getText() + "', '" + gpuIn.getText() + "', '"
                                    + opSysIn.getText() + "', '" + ramIn.getText() + "', " + dev_storage + ", '" + dev_type + "', "
                                    + storeMainWindow.user_id + ", FALSE, NULL);");
                            computerInsert.executeUpdate();

                            JOptionPane.showMessageDialog(sellerUI, "Computer added successfully.");
                        } catch (SQLException ex) {
                            System.out.println("Error whilst adding computer: \n" + ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(sellerUI, "Complete computer information");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(sellerUI, "Please fill in the basic information.");
            }
        });
    }
}
