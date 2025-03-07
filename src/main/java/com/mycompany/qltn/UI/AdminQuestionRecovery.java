/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn.UI;

import com.mycompany.qltn.component.ActionPanelRenderer;
import com.mycompany.qltn.component.ButtonRecoverEdit;
import com.mycompany.qltn.component.ButtonRecoverRender;
import com.mycompany.qltn.BLL.ExamBLL;
import com.mycompany.qltn.BLL.QuestionBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.dto.ExamDTO;
import com.mycompany.qltn.dto.OptionDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.TestDTO;
import com.mycompany.qltn.dto.TopicDTO;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author ACER
 */
public class AdminQuestionRecovery extends javax.swing.JFrame {

    /**
     * Creates new form AdminQuestionRecovery
     */
    private  QuestionBLL    questionBLL;
    private  ButtonRecoverEdit buttonRecoverEdit;
    private   ArrayList<QuestionDTO> questionsData  ;
    private  Response.ExamResult examData;
    private  ExamBLL examBLL;
    private  ButtonRecoverEdit buttonRecoverTest;
    public AdminQuestionRecovery() {
        this.examBLL = new ExamBLL();
        this.examData = new Response.ExamResult();
        this.questionsData = new ArrayList<>();
        this.   questionBLL = new QuestionBLL();
        this.buttonRecoverEdit = new ButtonRecoverEdit();
        this.buttonRecoverTest = new  ButtonRecoverEdit();
        initComponents();
        jTable1.setSelectionBackground(Color.WHITE);
    jTable1.setSelectionForeground(Color.BLACK);
        loadQuestions();
          jTable1.setRowHeight(60);
         TableColumn questionColumn = this.jTable1.getColumnModel().getColumn(0); 
        questionColumn.setPreferredWidth(400);
         this.buttonRecoverEdit.getPanel().getjButton1().addActionListener((ActionEvent e) -> {
             int row= this.buttonRecoverEdit.getRow();
          QuestionDTO questionDTO = new QuestionDTO();
              questionDTO.setQuestionId(this.questionsData.get(row).getQuestionId());
               int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Bạn có chắc chắn muốn khôi phục câu hỏi?", 
        "Xác nhận", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
         Response.QuestoinResult res =    questionBLL.recoverQuestion(questionDTO.getQuestionId());
         if(res.isIsSuccess()) {
           JOptionPane.showMessageDialog(null, res.getMessage());
    
         }else {
                    JOptionPane.showMessageDialog(null, res.getMessage());

          }
    }
               
               this.buttonRecoverEdit.fireEditStop();
               loadQuestions();
     });
         
       this.buttonRecoverTest.getPanel().getjButton1().addActionListener((ActionEvent e) -> { 
          int row = this.buttonRecoverTest.getRow();
          int testid = this.examData.getExamList().get(row).getTestId();
         int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Bạn có chắc chắn muốn khôi phục đề thi ?", 
        "Xác nhận", 
        JOptionPane.YES_NO_OPTION
    );
         if(confirm ==JOptionPane.YES_OPTION) {
           Response.BaseResponse res = examBLL.recoverTest(testid);
           if(res.isIsSuccess()) {
             JOptionPane.showMessageDialog(null, res.getMessage()); 
           }else {
            JOptionPane.showMessageDialog(null, res.getMessage());
           }
         }
         this.buttonRecoverTest.fireEditStop();
         loadExams();
       });
      
       
    }
 private  void  displayDataOnTable(Response.QuestoinResult result,Response.TopicResult  topicResult) {
     ArrayList<QuestionDTO> questions = result.getQuestionList();
       this.questionsData =questions;
       ArrayList<OptionDTO> options = result.getAnswerList();
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new ButtonRecoverRender());
             jTable1.getColumnModel().getColumn(4).setCellEditor(this.buttonRecoverEdit);
       System.out.println("item1:"+questions.size());
          
             DefaultTableModel tableModel = (DefaultTableModel) this.jTable1.getModel();
             tableModel.setRowCount(0);     
               for (QuestionDTO question : questions) {
                StringBuilder answers = new StringBuilder();
                String topicText = "";
                for (OptionDTO option : options) {
                   
                    if (option.getQuestionId() == question.getQuestionId() && option.isIsCorrect()) {
                        answers.append(option.getOptionText()).append(", ");
                    }
                }
                for(TopicDTO topic: topicResult.getTopicList()) {
                 if(topic.getTopicId()==question.getTopicId()) {
                   topicText = topic.getTopicName();
                 }
                }
                   System.out.println("title:"+topicText);
                if (answers.length() > 2) {
                    answers.setLength(answers.length() - 2);
                }
                
                tableModel.addRow(new Object[]{question.getQuestionText(), answers,topicText, question.getDifficulty()});
            }
          
           
           
     
    }
    private  void  displayDataOnTableTest(Response.ExamResult result,Response.TopicResult  topicResult) {
       ArrayList<TestDTO> testList = result.getTestLists();
       ArrayList<ExamDTO> exmList = result.getExamList();
        this.examData = result;
           String[] columnNames = { "Mã đề", "Tên bài thi","Chủ đề","Thời gian thi", "Khôi phục"};
             DefaultTableModel tableModel = (DefaultTableModel) this.jTable1.getModel();
                tableModel.setColumnIdentifiers(columnNames);
             tableModel.setRowCount(0);
                   jTable1.getColumnModel().getColumn(4).setCellRenderer(new ButtonRecoverRender());
             jTable1.getColumnModel().getColumn(4).setCellEditor(this.buttonRecoverTest);
              
            
              
         
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
                tableModel.addRow(new Object[]{exam.getExamCode(), testName, topic, testTime +" phút"});
            }
               
            
         
    
             
     
    }
    private  void loadExams() {
       Response.ExamResult res = examBLL.getExamDeletedResult();
       Response.TopicResult topicResult =    questionBLL.getTopic();
       if(res.isIsSuccess()) {
           displayDataOnTableTest(res, topicResult);
       }else {
          JOptionPane.showMessageDialog(null, res.getMessage());
       }
    }
  private  void loadQuestions() {
    Response.QuestoinResult result  =    questionBLL.getQuestionDeleted();
        Response.TopicResult topicResult =    questionBLL.getTopic();
        
        if(result.isIsSuccess() && topicResult.isIsSuccess()) {
              displayDataOnTable(result, topicResult);
            
        } else {
            JOptionPane.showMessageDialog(this, result.getMessage());

           
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Câu hỏi đã xóa");

        jButton1.setText("Trờ lại");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Khôi phục đề thi");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jButton2))
                        .addGap(7, 7, 7))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Cáu hỏi", "đáp án", "chủ đề", "Mức độ", "Khôi phục"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addContainerGap())
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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AdminHomeScreen adminHomeScreen = new AdminHomeScreen();
        adminHomeScreen.setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
          loadExams();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(AdminQuestionRecovery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminQuestionRecovery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminQuestionRecovery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminQuestionRecovery.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminQuestionRecovery().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
