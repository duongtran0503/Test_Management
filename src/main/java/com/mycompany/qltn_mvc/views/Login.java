/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn_mvc.views;

import com.mycompany.qltn_mvc.Main;
import com.mycompany.qltn_mvc.UI.RoundedFlatBorder;
import com.mycompany.qltn_mvc.controllers.AuthController;
import com.mycompany.qltn_mvc.controllers.Response;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author ACER
 */
public class Login extends javax.swing.JFrame {
   private final AuthController authController = new AuthController();
    /**
     * Creates new form Login
     */
    public Login() {
         
        initComponents();
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        jLabel3.setText("<html>Đăng nhập ứng dụng để bắt đầu thi</html>");
        this.setBackground(Color.white);
        containerFormLogin.setBorder(new RoundedFlatBorder(15, Color.WHITE, 2)); // Bo góc 15px, viền đen, dày 2px
        containerFormLogin.setBackground(Color.WHITE);
         
    }
   private void validationLogin() {
    String email = userName.getText().trim();
    char[] passwordChars = inputPassword.getPassword();
    String password = new String(passwordChars); // Chuyển đổi sang String (cần thiết cho so sánh)

    System.out.println("password:" + password + " email:" + email);
    if (email.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập email và mật khẩu.", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
    } else if (!isValidEmail(email)) { 
        JOptionPane.showMessageDialog(this, "Email không hợp lệ.", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
    } else {
       
        Response.loginResult res = authController.login(email, password);
            Main.user = res.getUser();
        if (res.isIsSuccess()) {
           if(res.getUser().getRole().equalsIgnoreCase("admin")) {
            AdminHomeScreen adminHomeScreen = new AdminHomeScreen();
            adminHomeScreen.setVisible(true);
            this.dispose();
           } else {
            StudenHomeScreen studenHomeScreen = new StudenHomeScreen();
            studenHomeScreen.setVisible(true);
            this.setVisible(true);
            this.dispose();
           }
         
            
        } else {
          
            JOptionPane.showMessageDialog(this,res.getMessage(), "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }
    }

}


private boolean isValidEmail(String email) {
    // Biểu thức chính quy kiểm tra email (có thể tùy chỉnh)
    String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    return email.matches(regex);
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerFormLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        inputPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(400, 600));
        setSize(new java.awt.Dimension(400, 600));

        containerFormLogin.setBackground(new java.awt.Color(255, 255, 255));
        containerFormLogin.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Mật khẩu");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên người dùng");

        userName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        userName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Đăng ký");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel3.setText("Đăng nhập vào ứng dụng để bắt đâu thi");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setText("Đăng nhập");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("Bạn chưa có tài khoản ?");

        javax.swing.GroupLayout containerFormLoginLayout = new javax.swing.GroupLayout(containerFormLogin);
        containerFormLogin.setLayout(containerFormLoginLayout);
        containerFormLoginLayout.setHorizontalGroup(
            containerFormLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFormLoginLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(containerFormLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userName)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerFormLoginLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputPassword))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        containerFormLoginLayout.setVerticalGroup(
            containerFormLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerFormLoginLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addGroup(containerFormLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(23, 23, 23))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerFormLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(containerFormLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Register register = new Register();
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       register.setLocationRelativeTo(null); // Căn giữa màn hình
       register.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// file login
// bật lên để login
//      validationLogin();
// hiện giao diện admin ko loggin
 Main.user.setUsername("tran duong");
   AdminHomeScreen adminHomeScreen = new AdminHomeScreen();
   adminHomeScreen.setVisible(true);
   dispose();
   // hiện giao diện studen ko login
//   StudenHomeScreen studenHomeScreen = new StudenHomeScreen();
//    studenHomeScreen.setVisible(true);
//     dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void userNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userNameActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel containerFormLogin;
    private javax.swing.JPasswordField inputPassword;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField userName;
    // End of variables declaration//GEN-END:variables
}
