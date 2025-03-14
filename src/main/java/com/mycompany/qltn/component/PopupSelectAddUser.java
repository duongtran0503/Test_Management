/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn.component;

import com.mycompany.qltn.BLL.AuthBLL;
import com.mycompany.qltn.BLL.ExamBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.BLL.UserBLL;
import com.mycompany.qltn.dto.UserDTO;
import com.mycompany.qltn.UI.AdminModalUser;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class PopupSelectAddUser extends javax.swing.JFrame {

    /**
     * Creates new form PopupSelectAddUser
     */
    private final  UserBLL userController;
    private  final AuthBLL authController;
    public PopupSelectAddUser() {
        initComponents();
           setLocationRelativeTo(null); 
             setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
             this.userController = new UserBLL();
             this.authController = new AuthBLL();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Thêm 1 tài khoản mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Import file excel");
        jButton2.setPreferredSize(new java.awt.Dimension(116, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
          AdminModalUser adminModalUser = new AdminModalUser();
      adminModalUser.setLocationRelativeTo(null);
      
      adminModalUser.setModalHandle(AdminModalUser.ADD_MODAL);
      adminModalUser.setVisible(true);
      dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      importExcel();
    }//GEN-LAST:event_jButton2ActionPerformed
   private void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
              Response.UserResult res = userController.importExcel(selectedFile);
             
              if(res.isIsSuccess()) {
                  Response.BaseResponse response = userController.addListUser(res.getUserList());
                  if(res.isIsSuccess()) {
                     JOptionPane.showMessageDialog(null, response.getMessage());
                     dispose();
                  }else {
                    JOptionPane.showMessageDialog(null, response.getMessage());
                           dispose();
                  }
                  
              }else {
                JOptionPane.showMessageDialog(null, res.getMessage());
              }
        
        }
             
        } 
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
