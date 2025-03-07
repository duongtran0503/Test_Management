/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn.UI;

import com.mycompany.qltn.component.ActionPanelEditor;
import com.mycompany.qltn.component.ActionPanelRenderer;
import com.mycompany.qltn.BLL.ExamBLL;
import com.mycompany.qltn.BLL.QuestionBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn_mvc.dtos.ExamDTO;
import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.dtos.TestDTO;
import com.mycompany.qltn_mvc.dtos.TopicDTO;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class AdminManagerTest extends javax.swing.JFrame {

    /**
     * Creates new form AdminCreateTest
     */
    private final  ExamBLL examBLL;
   private  ActionPanelEditor actionPanelEditor;
   private final  QuestionBLL questionBLL;
   private  Response.ExamResult examData;
    public AdminManagerTest() {
        this.examData = new Response.ExamResult();
        this.questionBLL = new  QuestionBLL();
        this.examBLL = new ExamBLL();
        this.actionPanelEditor = new ActionPanelEditor();
        initComponents();
         DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
     model.addElement("Tất cả chủ đề");
        model.addElement("Lập trình");
        model.addElement("Du lịch");
        model.addElement("Toán học");
        selectTopic.setModel(model);
        loadExams();
        jTable1.setRowHeight(60);
          jTable1.setSelectionBackground(Color.WHITE);
    jTable1.setSelectionForeground(Color.BLACK);
     jTable1.setFocusable(false);
      this.actionPanelEditor.getPanel().getButtonEdit().addActionListener((e) -> {
                       int row  = this.actionPanelEditor.getRow();
                      int idTest =   this.examData.getExamList().get(row).getTestId();
                      String examCode = this.examData.getExamList().get(row).getExamCode();
                          AdminModalExam adminModalExam = new AdminModalExam();
                          adminModalExam.displayDataEdit(idTest, examCode);
                          adminModalExam.setVisible(true);
                           this.actionPanelEditor.fireEditStop();
                       
                            
                      
                });
      this.actionPanelEditor.getPanel().getButtonDelete().addActionListener((e) -> {
                       int row  = this.actionPanelEditor.getRow();
                      int idTest =   this.examData.getExamList().get(row).getTestId();
                          int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Bạn có chắc chắn muốn xóa bài thi không?", 
        "Xác nhận", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) { 
          Response.BaseResponse res = examBLL.deleteTest(idTest);
          if(res.isIsSuccess()) {
           JOptionPane.showMessageDialog(null, res.getMessage());
        
          } else {
            JOptionPane.showMessageDialog(null, res.getMessage());
          }
    }
                     
                       
              this.actionPanelEditor.fireEditStop();
                 loadExams();
                      
                });
    }
   private  void loadExams() {
       Response.ExamResult res = examBLL.getExamResult();
       Response.TopicResult topicResult = questionBLL.getTopic();
       if(res.isIsSuccess()) {
           displayDataOnTable(res, topicResult);
       }else {
          JOptionPane.showMessageDialog(null, res.getMessage());
       }
   }
    private  void  displayDataOnTable(Response.ExamResult result,Response.TopicResult  topicResult) {
       ArrayList<TestDTO> testList = result.getTestLists();
       ArrayList<ExamDTO> exmList = result.getExamList();
        this.examData = result;
             DefaultTableModel tableModel = (DefaultTableModel) this.jTable1.getModel();
             tableModel.setRowCount(0);
             jTable1.getColumnModel().getColumn(5).setCellRenderer(new ActionPanelRenderer());
             jTable1.getColumnModel().getColumn(5).setCellEditor(this.actionPanelEditor);
            
             
              
         
               for (ExamDTO exam:exmList) {
                String topic = "";
                String testName ="";
                String testTime = "";
                int  topicID =1;
                 for(TestDTO test:testList) {
                    if(test.getTestId() ==exam.getTestId()) {
                      testName = test.getTestName();
                      testTime =test.getTestTime()+"";
                      topicID = test.getTopicId();
                    }
                 }
                 for(TopicDTO top: topicResult.getTopicList()) {
                   if(top.getTopicId()==topicID) {
                    topic = top.getTopicName();
                   }
                 }
                tableModel.addRow(new Object[]{exam.getExamId()+"",exam.getExamCode(), testName, topic, testTime +" phút",""});
            }
               
            
         
    
             
     
    }
     private  void searchTest() {
       String selectedValue  =(String)  this.selectTopic.getSelectedItem();
        String searchKey = inputSearch.getText().trim();
          Response.TopicResult topicResult = questionBLL.getTopic();
       if(selectedValue.equalsIgnoreCase("Tất cả chủ đề")) {
         Response.ExamResult res = examBLL.search(0, searchKey, -1);
         if(res.isIsSuccess()) {
             displayDataOnTable(res, topicResult);
         }else {
           JOptionPane.showInternalMessageDialog(null, res.getMessage());
         }
       } else {
         if(selectedValue.equalsIgnoreCase("Lập trình")) {
            Response.ExamResult res = examBLL.search(0, searchKey, 1);
            if(res.isIsSuccess()) {
          displayDataOnTable(res, topicResult);
         }else {
           JOptionPane.showInternalMessageDialog(null, res.getMessage());
         }
         } else if(selectedValue.equalsIgnoreCase("Du lịch")) {
         Response.ExamResult res = examBLL.search(0, searchKey, 2);
           if(res.isIsSuccess()) {
            displayDataOnTable(res, topicResult);
         }else {
           JOptionPane.showInternalMessageDialog(null, res.getMessage());
         }
         } else {
           Response.ExamResult res = examBLL.search(0, searchKey, 3);
           if(res.isIsSuccess()) {
                 displayDataOnTable(res, topicResult);
         }else {
           JOptionPane.showInternalMessageDialog(null, res.getMessage());
         }
         }
       }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        inputSearch = new javax.swing.JTextField();
        selectTopic = new javax.swing.JComboBox<>();
        buttonSearchj = new javax.swing.JButton();
        buttonCreateTest = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Quản lý đề thi");

        selectTopic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonSearchj.setText("search");
        buttonSearchj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchjActionPerformed(evt);
            }
        });

        buttonCreateTest.setText("Tạo đề thi");
        buttonCreateTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateTestActionPerformed(evt);
            }
        });

        jButton4.setText(" Trở lại");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSearchj, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(buttonCreateTest, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonSearchj, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(selectTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonCreateTest, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Mã đề", "Tên bài thi", "Chủ đề", "Thời gian thi", "Thao tác"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         AdminHomeScreen adminHomeScreen = new AdminHomeScreen();
          adminHomeScreen.setVisible(true);
          dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void buttonCreateTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateTestActionPerformed
       AdminCreateTestScreen adminCreateTestScreen = new AdminCreateTestScreen();
        adminCreateTestScreen.setLocationRelativeTo(null);
        adminCreateTestScreen.setVisible(true);
    }//GEN-LAST:event_buttonCreateTestActionPerformed

    private void buttonSearchjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchjActionPerformed
       searchTest();
    }//GEN-LAST:event_buttonSearchjActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminManagerTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminManagerTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminManagerTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminManagerTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminManagerTest().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCreateTest;
    private javax.swing.JButton buttonSearchj;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> selectTopic;
    // End of variables declaration//GEN-END:variables
}
