/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn.UI;

import com.mycompany.qltn.component.ActionPanelRenderer;
import com.mycompany.qltn.component.CheckboxTableModel;
import com.mycompany.qltn.BLL.ExamBLL;
import com.mycompany.qltn.BLL.QuestionBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.dto.OptionDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.TopicDTO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class AdminQuestionSelectScreen extends javax.swing.JFrame {

    /**
     * Creates new form AdminQuestionScreen
     */
    private  QuestionBLL questionBLL;
    private  Response.QuestoinResult questoinResult;
    private  ExamBLL examBLL;

  
    private  int ExamQuestionCode ; 
    public AdminQuestionSelectScreen() {
        this.examBLL = new ExamBLL();
        this.questoinResult = new Response.QuestoinResult();
        this.questionBLL = new QuestionBLL();
        initComponents();
          setLocationRelativeTo(null); 
             setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           
          DefaultComboBoxModel<String> modelTopic = new DefaultComboBoxModel<>();
        modelTopic.addElement("Tất cả chủ đề");
        modelTopic.addElement("Lập trính");
        modelTopic.addElement("Du lịch");
        modelTopic.addElement("Toán học");
        this.selectTopic.setModel(modelTopic);
        loadQuestion();
        jTable1.setRowHeight(50);
        jTable1.setFocusable(false);
    }
    public int getExamQuestionCode() {
        return ExamQuestionCode;
    }

    public void setExamQuestionCode(int ExamQuestionCode) {
        this.ExamQuestionCode = ExamQuestionCode;
    }
       private  void  displayDataOnTable(Response.QuestoinResult questoinResult) {
          Response.TopicResult topics = questionBLL.getTopic();
             DefaultTableModel tableModel = (DefaultTableModel) this.jTable1.getModel();
             tableModel.setRowCount(0);
              List<Object[]> data = new ArrayList<>();
             for(QuestionDTO question: questoinResult.getQuestionList()) {
                 String answer = "";
                 String topic ="";
                 ArrayList<OptionDTO> listAnswer = new ArrayList<>();
                    for(OptionDTO option: questoinResult.getAnswerList()) {
                       if(option. getQuestionId() == question.getQuestionId() && option.isIsCorrect()) {
                            switch (listAnswer.size() +1) {
                               case 1 -> answer = "A";
                                    case 2 -> answer = "B";
                                    case 3 -> answer = "C";
                                    case 4 -> answer = "D";
                                   
                          
                           }
                       }
                       if(option.getQuestionId()==question.getQuestionId()) {
                         listAnswer.add(option);
                       }
                     
                    }
                   for(TopicDTO top:topics.getTopicList()) {
                    if(top.getTopicId() == question.getTopicId()) {
                     topic =top.getTopicName();
                    }
                    } 

               data.add(new Object[]{false, question.getQuestionText(), listAnswer.get(0).getOptionText(), listAnswer.get(1).getOptionText(), listAnswer.get(2).getOptionText(),listAnswer.get(3).getOptionText(), answer, question.getDifficulty(), topic});
            }
              
                  
                 
          


Object[][] dataArray = data.toArray(Object[][]::new);
    String[] columnNames = {"Chọn", "Câu hỏi", "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D", "Đáp án đúng", "Mức độ", "Chủ đề"};

           CheckboxTableModel checkboxTableModel = new CheckboxTableModel(dataArray, columnNames);
    this.jTable1.setModel(checkboxTableModel);
               
        
     
    }
       private void loadQuestion() {
        Response.QuestoinResult res = questionBLL.getQuestoinResult(0, 20);
        this.questoinResult =res;
           displayDataOnTable(res);
       }
            
               
       
      
       
       private  ArrayList<QuestionDTO> getSelectedQuestions() {
      ArrayList<QuestionDTO> questions = new ArrayList<>();
    CheckboxTableModel tableModel = (CheckboxTableModel) jTable1.getModel();
    for (int i = 0; i < tableModel.getRowCount(); i++) {
        if ((Boolean) tableModel.getValueAt(i, 0)) {
        QuestionDTO questionDTO = this.questoinResult.getQuestionList().get(i);
           questions.add(questionDTO);
            
        }
    }
    return questions;
}
    private  void searchQuestion() {
      String searchKey = inputSearch.getText().trim();
   
    int topic = selectTopic.getSelectedIndex();
        System.out.println("topiuc"+topic);
     if(topic !=0) {
       Response.QuestoinResult res = questionBLL.searchQuestionByTitleAndTopic(searchKey, topic);
         if(res.isIsSuccess()) {
             displayDataOnTable(res);
         }else {
          JOptionPane.showMessageDialog(null, res.getMessage());
         }
     } else {
          Response.QuestoinResult res = questionBLL.searchQuestionByTitle(searchKey);
         if(res.isIsSuccess()) {
             displayDataOnTable(res);
         }else {
          JOptionPane.showMessageDialog(null, res.getMessage());
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
        inputSearch = new javax.swing.JTextField();
        selectTopic = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        inputSearch.setPreferredSize(new java.awt.Dimension(73, 30));
        inputSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputSearchActionPerformed(evt);
            }
        });

        selectTopic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("search");
        jButton1.setPreferredSize(new java.awt.Dimension(75, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Chọn", "Câu hỏi", "Đáp án A", "Đáp án B", "Đán án C", "Đáp án D", "Đán án đúng", "Mức độ", "Chủ đề"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("Lưu");
        jButton3.setPreferredSize(new java.awt.Dimension(75, 30));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Hủy");
        jButton4.setPreferredSize(new java.awt.Dimension(75, 30));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(560, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputSearchActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         ArrayList<QuestionDTO> questionsResult = getSelectedQuestions();
         if(questionsResult.isEmpty()) {
             JOptionPane.showMessageDialog(null, "Vui lòng chọn câu hỏi!");
         } else {
           Response.ExamResult res = examBLL.addQuestionToTheTest(questionsResult,this.ExamQuestionCode);
           if(res.isIsSuccess()) {
            JOptionPane.showMessageDialog(null, res.getMessage());
           }else {
              JOptionPane.showMessageDialog(null, res.getMessage());
           }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
          int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Xác nhận hủy thêm câu hỏi vào để thi!",
        "Xác nhận", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
       dispose();
    }
         
       
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       searchQuestion();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField inputSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> selectTopic;
    // End of variables declaration//GEN-END:variables
}
