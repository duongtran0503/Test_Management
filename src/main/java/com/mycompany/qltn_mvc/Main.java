/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.qltn_mvc;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.mycompany.qltn_mvc.UI.Toast;
import com.mycompany.qltn_mvc.config.DatabaseConnection;
import com.mycompany.qltn_mvc.views.Login;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author ACER
 */
public class Main {

    public static void main(String[] args) {
         try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null) {
                // Sử dụng kết nối ở đây (ví dụ: thực hiện truy vấn SQL)
                conn.close(); // Đóng kết nối khi hoàn thành
            }
        } catch (SQLException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
       try {
            // Cài đặt giao diện FlatLaf
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            UIManager.put("Button.arc", 20); // Bo góc button
            UIManager.put("Button.background", new java.awt.Color(100, 150, 255));
            UIManager.put("Button.foreground", java.awt.Color.WHITE);

        } catch (Exception e) {
            e.printStackTrace();
        }
       Login login = new Login();
      
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       login.setLocationRelativeTo(null); // Căn giữa màn hình
       login.setVisible(true);
       

    }
}
