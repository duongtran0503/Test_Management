/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn_mvc.views;

import com.mycompany.qltn_mvc.UI.ActionPanelRenderer;
import com.mycompany.qltn_mvc.UI.ActionPanelEditor;
import com.mycompany.qltn_mvc.controllers.QuestionController;
import com.mycompany.qltn_mvc.controllers.Response;
import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.dtos.TopicDTO;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class AdminQuestionManagerScreen extends javax.swing.JFrame {

  

     private int currentPage = 0;
    private int questionsPerPage = 20;
    private int totalQuestions = 0;
    private  QuestionController questionController;
   private  ActionPanelEditor actionPanelEditor;
    
    public AdminQuestionManagerScreen() {
        this.questionController = new QuestionController();
        
        initComponents();
        this.buttonExport.setBackground(Color.WHITE);
       this.buttonExport.setForeground(Color.BLACK);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
           
   
    jTable1.setSelectionBackground(Color.WHITE);
    jTable1.setSelectionForeground(Color.BLACK);
     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
     model.addElement("Tất cả chủ đề");
        model.addElement("Lập trình");
        model.addElement("Du lịch");
        model.addElement("Toán học");
        selectQuery.setModel(model);
 jTable1.setFocusable(false);
    jTable1.setRowHeight(50);
        loadQuestions(0);
    }
    private  void loadQuestions(int page) {
        Response.QuestoinResult result = questionController.getQuestoinResult(page, this.questionsPerPage);
        Response.TopicResult topicResult = questionController.getTopic();
        
        if(result.isIsSuccess() && topicResult.isIsSuccess()) {
          
            displayDataOnTable(result, topicResult, page);
        } else {
            JOptionPane.showMessageDialog(this, result.getMessage());

           
        }
    }
    private  void  displayDataOnTable(Response.QuestoinResult result,Response.TopicResult  topicResult,int page) {
     ArrayList<QuestionDTO> questions = result.getQuestionList();
     
            ArrayList<OptionDTO> options = result.getAnswerList();
            this.actionPanelEditor = new ActionPanelEditor();
             DefaultTableModel tableModel = (DefaultTableModel) this.jTable1.getModel();
             tableModel.setRowCount(0);
             jTable1.getColumnModel().getColumn(5).setCellRenderer(new ActionPanelRenderer());
             jTable1.getColumnModel().getColumn(5).setCellEditor(this.actionPanelEditor);
            
             
              
         
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
                
                tableModel.addRow(new Object[]{question.getQuestionId()+"",question.getQuestionText(), answers.toString(), question.getDifficulty(), topicText,""});
            }
                currentPage = result.getCurrentPage() + 1;
                System.out.println("current page"+this.currentPage);
            totalQuestions = result.getTotalQuestions();
            updateLoadedCountLabel(questions.size() + (page * questionsPerPage));
            
             this.actionPanelEditor.getPanel().getButtonEdit().addActionListener((e) -> {
                    
                  int indexItem =Integer.parseInt((String) tableModel.getValueAt(this.actionPanelEditor.getRow(), 0)); 
                  QuestionDTO questionDTO  = new QuestionDTO();
                 
                  for(QuestionDTO question:questions) {
                     if(question.getQuestionId()==indexItem) {
                      questionDTO = question;
                     }
                  }
                   ArrayList<OptionDTO> listAnswer = new ArrayList<>();
                   int temp = 0;
                   int indexResult = 0;
                   for(OptionDTO optionDTO:options){
                    if(optionDTO.getQuestionId()==questionDTO.getQuestionId()) {
                        
                        listAnswer.add(optionDTO);
                        if(optionDTO.isIsCorrect()) {
                          indexResult =temp;
                        }
                         temp++;
                    }
                   }
                           System.out.println("row"+questionDTO.getQuestionId());   
                             JFrame frame = new JFrame("Thêm câu hỏi");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(1080, 600); // Điều chỉnh kích thước
      AdminModalQuestion updateQuestion = new AdminModalQuestion();
      updateQuestion.setTask(AdminModalQuestion.MODAL_UPDATE);
      updateQuestion.setValueQuestion(questionDTO, listAnswer, indexResult);
    frame.add(updateQuestion); // Thêm JPanel vào JFrame
    frame.setLocationRelativeTo(null); // Căn giữa màn hình
    frame.setVisible(true);
                           this.actionPanelEditor.fireEditStop();
                      
                });
             
             this.actionPanelEditor.getPanel().getButtonDelete().addActionListener((e)->{
                   int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Bạn có chắc chắn muốn xóa câu hỏi không?", 
        "Xác nhận", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
            int indexItem =Integer.parseInt((String) tableModel.getValueAt(this.actionPanelEditor.getRow(), 0)); 
                  QuestionDTO questionDTO  = new QuestionDTO();
                 
                  for(QuestionDTO question:questions) {
                     if(question.getQuestionId()==indexItem) {
                      questionDTO = question;
                     }
                  }
                System.out.println("delete question id:"+questionDTO.getQuestionId());
                
                Response.QuestoinResult res = questionController.deleteQuestion(questionDTO.getQuestionId());
                if(res.isIsSuccess()) {
                  JOptionPane.showMessageDialog(null, res.getMessage());
                }else {
                
                 JOptionPane.showMessageDialog(null, res.getMessage());
                }
    }
      this.actionPanelEditor.fireEditStop();
             
             });
             
     
    }
    private void updateLoadedCountLabel(int loadedCount) {
        currentQuestion.setText(loadedCount + "/" + totalQuestions + " câu hỏi");
    }
    private  void searchQuestion() {
       String selectedValue  =(String)  selectQuery.getSelectedItem();
        String searchKey = inputSearch.getText().trim();
          Response.TopicResult topicResult = questionController.getTopic();
       if(selectedValue.equalsIgnoreCase("Tất cả chủ đề")) {
         Response.QuestoinResult res = questionController.searchQuestionByTitle(searchKey);
         if(res.isIsSuccess()) {
             displayDataOnTable(res, topicResult,0);
         }else {
           JOptionPane.showInternalMessageDialog(null, res.getMessage());
         }
       } else {
         if(selectedValue.equalsIgnoreCase("Lập trình")) {
           Response.QuestoinResult res = questionController.searchQuestionByTitleAndTopic(searchKey, 1);
            if(res.isIsSuccess()) {
             displayDataOnTable(res, topicResult,0);
         }else {
           JOptionPane.showInternalMessageDialog(null, res.getMessage());
         }
         } else if(selectedValue.equalsIgnoreCase("Du lịch")) {
           Response.QuestoinResult res = questionController.searchQuestionByTitleAndTopic(searchKey, 2);
           if(res.isIsSuccess()) {
             displayDataOnTable(res, topicResult,0);
         }else {
           JOptionPane.showInternalMessageDialog(null, res.getMessage());
         }
         } else {
           Response.QuestoinResult res = questionController.searchQuestionByTitleAndTopic(searchKey, 3);
           if(res.isIsSuccess()) {
             displayDataOnTable(res, topicResult,0);
         }else {
           JOptionPane.showInternalMessageDialog(null, res.getMessage());
         }
         }
       }
    }
      public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setQuestionsPerPage(int questionsPerPage) {
        this.questionsPerPage = questionsPerPage;
    }

    /**
     * Creates new form AdminQuestionManagerScreen
     */
    public void setCurrentQuestion(JLabel currentQuestion) {
        this.currentQuestion = currentQuestion;
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
        buttonExport = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
        inputSearch = new javax.swing.JTextField();
        selectQuery = new javax.swing.JComboBox<>();
        buttonSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        currentQuestion = new javax.swing.JLabel();
        buttonPrev = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Quản lý câu hỏi");

        buttonExport.setText("Export");
        buttonExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExportActionPerformed(evt);
            }
        });

        buttonAdd.setText("Thêm mới");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        selectQuery.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/searchIcon.png"))); // NOI18N
        buttonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectQuery, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonExport, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonExport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonSearch, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(selectQuery, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(inputSearch, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "Câu hỏi", "Đáp án", "Mức độ", "Chủ đề", "Thao tác"
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

        jButton1.setText("Thoát");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Tải thêm dữ liệu");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        currentQuestion.setText("30/50 câu hỏi");

        buttonPrev.setText("dữ liệu trước đó");
        buttonPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrevActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1058, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(currentQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(164, 164, 164)
                .addComponent(buttonPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(currentQuestion)
                        .addComponent(buttonPrev)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonExportActionPerformed

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
                                         
    JFrame frame = new JFrame("Thêm câu hỏi");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(1080, 600); // Điều chỉnh kích thước
    frame.add(new AdminModalQuestion()); // Thêm JPanel vào JFrame
    frame.setLocationRelativeTo(null); // Căn giữa màn hình
    frame.setVisible(true);

    }//GEN-LAST:event_buttonAddActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         AdminHomeScreen adminHomeScreen = new AdminHomeScreen();
           adminHomeScreen.setVisible(true);
           dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void buttonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSearchActionPerformed
       searchQuestion();
    }//GEN-LAST:event_buttonSearchActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         if(this.currentPage*this.questionsPerPage <this.totalQuestions) {
             setCurrentPage(this.currentPage);
              loadQuestions(this.currentPage);
         }else {
          JOptionPane.showMessageDialog(null, "Đã tài hết câu hỏi");
         }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void buttonPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrevActionPerformed
      
    
        loadQuestions(0);
    
  
    }//GEN-LAST:event_buttonPrevActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonExport;
    private javax.swing.JButton buttonPrev;
    private javax.swing.JButton buttonSearch;
    private javax.swing.JLabel currentQuestion;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> selectQuery;
    // End of variables declaration//GEN-END:variables
}
