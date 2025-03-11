/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.qltn;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.mycompany.qltn.dto.UserDTO;
import com.mycompany.qltn.UI.Login;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author ACER
 */
public class App {
 /////////////////////////////////////////////////////////// alt + shift + f để format code//////////////////////////////
    public static UserDTO user = new UserDTO();

    public static void main(String[] args) {

        try {
            // Cài đặt giao diện FlatLaf
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("Button.arc", 30); // Bo góc button
            UIManager.put("Button.background", new java.awt.Color(100, 150, 255));
            UIManager.put("Button.foreground", java.awt.Color.WHITE);

        } catch (Exception e) {
        }
        Login login = new Login();

        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setLocationRelativeTo(null); // Căn giữa màn hình
        login.setVisible(true);

    }
}
