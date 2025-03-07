/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.component;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author ACER
 */
public class Toast extends JWindow {

    private final JLabel label;
     // Định nghĩa màu sắc cho các trạng thái (đã làm nhạt)
    public static final Color SUCCESS_COLOR = new Color(0, 153, 0, 100); // Xanh lá cây nhạt
    public static final Color ERROR_COLOR = new Color(204, 0, 0, 100);   // Đỏ nhạt
    public static final Color WARNING_COLOR = new Color(255, 204, 0, 100); // Vàng nhạt
    public Toast(JFrame parent, String message, int duration,String status) {
        super(parent);
      label = new JLabel(); 
       // Tự động xuống hàng
        StringBuilder wrappedText = new StringBuilder("<html>");
        int charCount = 0;
        for (char c : message.toCharArray()) {
            wrappedText.append(c);
            charCount++;
            if (charCount >= 20 && c == ' ') { // Xuống hàng sau 10 ký tự và gặp khoảng trắng
                wrappedText.append("<br>");
                charCount = 0;
            }
        }
         wrappedText.append("</html>");
          label.setText(wrappedText.toString());
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setBorder(new EmptyBorder(15, 25, 15, 25));
        // Top, Left, Bottom, Right
        Color backgroundColor;
        switch (status) {
            case "success":
                backgroundColor = SUCCESS_COLOR;
                break;
            case "error":
                backgroundColor = ERROR_COLOR;
                break;
            case "warning":
                backgroundColor = WARNING_COLOR;
                break;
            default:
                backgroundColor = new Color(176, 196, 222); // Màu mặc định
        }
         
        getContentPane().setBackground(backgroundColor);
        getContentPane().add(label);
        pack();
        setLocationRelativeTo(parent); // Hiển thị ở giữa parent
        setVisible(true);

        // Tự động đóng sau duration giây
        new Thread(() -> {
            try {
                Thread.sleep(duration * 1000);
                dispose();
            } catch (InterruptedException e) {
            }
        }).start();
    }
}
