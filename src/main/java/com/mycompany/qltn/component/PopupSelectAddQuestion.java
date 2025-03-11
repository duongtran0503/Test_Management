/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn.component;

import com.mycompany.qltn.BLL.QuestionBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.UI.AdminModalQuestion;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class PopupSelectAddQuestion extends javax.swing.JFrame {

    /**
     * Creates new form PopupSelectAddQuestion
     */
    private QuestionBLL questionController;

    public PopupSelectAddQuestion() {
        this.questionController = new QuestionBLL();
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        importFileExcel = new javax.swing.JButton();
        buttonAddOneQuestion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Thêm câu hỏi mới");

        importFileExcel.setText("Import file excel");
        importFileExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importFileExcelActionPerformed(evt);
            }
        });

        buttonAddOneQuestion.setText("Thêm 1 câu hỏi");
        buttonAddOneQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddOneQuestionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(importFileExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(71, 71, 71)
                    .addComponent(buttonAddOneQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(72, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addComponent(importFileExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(45, 45, 45)
                    .addComponent(buttonAddOneQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(95, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAddOneQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddOneQuestionActionPerformed
        JFrame frame = new JFrame("Thêm câu hỏi");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setSize(1080, 600); // Điều chỉnh kích thước
        frame.add(new AdminModalQuestion()); // Thêm JPanel vào JFrame
        frame.setLocationRelativeTo(null); // Căn giữa màn hình
        frame.setVisible(true);
        dispose();
    }//GEN-LAST:event_buttonAddOneQuestionActionPerformed

    private void importFileExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importFileExcelActionPerformed
        importExcel();
    }//GEN-LAST:event_importFileExcelActionPerformed
    private void importExcel() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            Response.QuestoinResult res = this.questionController.importExcelAndPopulateLists(selectedFile);
            if (res.isIsSuccess()) {
                DisplayQuestionFromExcel displayQuestionFromExcel = new DisplayQuestionFromExcel();
                Response.TopicResult topics = questionController.getTopic();
                displayQuestionFromExcel.displayDataOnTable(res, topics);
                displayQuestionFromExcel.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, res.getMessage());

            }
        }
    }
    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAddOneQuestion;
    private javax.swing.JButton importFileExcel;
    // End of variables declaration//GEN-END:variables
}
