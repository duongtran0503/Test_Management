/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn.UI;

import com.mycompany.qltn.component.ActionPanelEditor;
import com.mycompany.qltn.component.ActionPanelRenderer;
import com.mycompany.qltn.component.PupupSelectAddQuestionForExam;
import com.mycompany.qltn.BLL.ExamBLL;
import com.mycompany.qltn.BLL.QuestionBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.dto.ExamDTO;
import com.mycompany.qltn.dto.OptionDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.TestDTO;
import com.mycompany.qltn.dto.TopicDTO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author ACER
 */
public class AdminModalExam extends javax.swing.JFrame {

    /**
     * Creates new form AdminModalExam
     */
     private final  ExamBLL examBLL;
   private  ActionPanelEditor actionPanelEditor;
   private  Response.ExamResult examData;
      private final  QuestionBLL questionBLL; 
    public AdminModalExam() {
               this.questionBLL = new  QuestionBLL();
        this.examData = new Response.ExamResult();
         this.examBLL = new ExamBLL();
        this.actionPanelEditor = new ActionPanelEditor();
        initComponents();
           setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        jTable1.setFocusable(false);
    jTable1.setRowHeight(50);
     TableColumn questionColumn = this.jTable1.getColumnModel().getColumn(0); 
        questionColumn.setPreferredWidth(400);
      
        this.actionPanelEditor.getPanel().getButtonEdit().addActionListener((ActionEvent e)->{
          int row = this.actionPanelEditor.getRow();
         QuestionDTO questionDTO =  this.examData.getListQestionOfExam().getQuestionList().get(row);
         ArrayList<OptionDTO> listAnswer = new ArrayList<>();
          int indexResult = 0;
          int index = 0;
         for(OptionDTO option:this.examData.getListQestionOfExam().getAnswerList()) {
           if(option.getQuestionId() ==questionDTO.getQuestionId()) {
             listAnswer.add(option);
               if(option.isIsCorrect()) {
                indexResult = index;
               } else {
                 index ++;
               }
           }
         }
            System.out.println("index:"+indexResult+"topic:"+questionDTO.getTopicId());
           System.out.println("row selected:"+row+"id:"+questionDTO.getQuestionId());   
                             JFrame frame = new JFrame("Chỉnh sửa câu hỏi");
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
        
        
                          
      
    }
   public void displayDataEdit(int idOfTest,String examCode) {
           this.examData =examBLL.getTestByIdAndExamCode(idOfTest, examCode);
          if(this.examData.isIsSuccess()) {
              
              displayDataOnTable(this.examData);
              this.timeTest.setText(this.examData.getTestLists().getFirst().getTestTime()+"");
              this.nameTest.setText(this.examData.getTestLists().getFirst().getTestName());
          }else {
              JOptionPane.showMessageDialog(null, this.examData.getMessage());
          }         
   }
   
    private  void  displayDataOnTable(Response.ExamResult result) {
     
             DefaultTableModel tableModel = (DefaultTableModel) this.jTable1.getModel();
             tableModel.setRowCount(0);
             jTable1.getColumnModel().getColumn(7).setCellRenderer(new ActionPanelRenderer());
             jTable1.getColumnModel().getColumn(7).setCellEditor(this.actionPanelEditor);
                 for(QuestionDTO question: result.getListQestionOfExam().getQuestionList()) {
                 String answer = "";
                 ArrayList<OptionDTO> listAnswer = new ArrayList<>();
                    for(OptionDTO option: this.examData.getListQestionOfExam().getAnswerList()) {
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

                tableModel.addRow(new Object[]{question.getQuestionText(),listAnswer.get(0).getOptionText(),
                    listAnswer.get(1).getOptionText(),
                    listAnswer.get(2).getOptionText(),
                    listAnswer.get(3).getOptionText(),
                    answer, question.getDifficulty(),});
            }
               
            
         
    
             
     
    }
    
    private  void exportFIleDocs() throws InvalidFormatException {
    
      JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn vị trí lưu file docs");
        fileChooser.setFileFilter(new FileNameExtensionFilter("docs Files (*.docs)", "docs"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Thêm đuôi .xlsx nếu người dùng không nhập
            if (!filePath.toLowerCase().endsWith(".docs")) {
                filePath += ".docs";
            }
            String nametest = this.examData.getTestLists().getFirst().getTestName();
             int time = this.examData.getTestLists().getFirst().getTestTime();
             String examCode = this.examData.getExamList().getFirst().getExamCode();
         Response.ExamResult res = examBLL.exportExamToDoc(filePath, nametest, time, examCode, this.examData.getListQestionOfExam());
           
          if(res.isIsSuccess()) {
            JOptionPane.showMessageDialog(null, res.getMessage());
          } else {
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
        title = new javax.swing.JLabel();
        timeTest = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        buttonAddQuestion = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        nameTest = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setText("Chỉnh sửa đề thi");

        jLabel2.setText("Thời gian thi:");

        buttonAddQuestion.setText("Thêm câu hỏi");
        buttonAddQuestion.setPreferredSize(new java.awt.Dimension(101, 35));
        buttonAddQuestion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddQuestionActionPerformed(evt);
            }
        });

        jButton2.setText("xuất file  docs");
        jButton2.setPreferredSize(new java.awt.Dimension(104, 35));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Thoát");
        jButton3.setPreferredSize(new java.awt.Dimension(72, 35));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("phút");

        jButton1.setText("Lưu thay đổi");
        jButton1.setPreferredSize(new java.awt.Dimension(96, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTest, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timeTest, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(115, 115, 115)
                .addComponent(buttonAddQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeTest, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonAddQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Câu hỏi", "Đáp án A", "Đáp án B", "Đáp án C", "Đán án D", "Đáp án đúng", "Mức độ", "Thao tác"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void buttonAddQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddQuestionActionPerformed
         if(this.examData.getListQestionOfExam().getQuestionList().size()==this.examData.getTestLists().getFirst().getTotalQuestion()) {
           JOptionPane.showMessageDialog(null, "Số lượng câu hỏi của bài thi đã đủ!");
           return;
         }   
        PupupSelectAddQuestionForExam pop = new PupupSelectAddQuestionForExam();
           
           
           pop.setExamCode(this.examData.getExamList().getFirst().getExamId());
            pop.setVisible(true);
            
    }//GEN-LAST:event_buttonAddQuestionActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      
         try {
             exportFIleDocs();
         } catch (InvalidFormatException ex) {
             Logger.getLogger(AdminModalExam.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
              int confirm = JOptionPane.showConfirmDialog(
        this, 
        "Bạn có chắc chắn muốn thoát?", 
        "Xác nhận", 
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        dispose();
    }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      String nametest = this.nameTest.getText().trim();
      int time =  Integer.parseInt(this.timeTest.getText());
      Response.ExamResult res = examBLL.updateNameAndTestTime(nametest, time,this.examData.getTestLists().getFirst().getTestId());
      if(res.isIsSuccess()) {
       JOptionPane.showMessageDialog(null, res.getMessage());
      }else {
              JOptionPane.showMessageDialog(null, res.getMessage());

      }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAddQuestion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nameTest;
    private javax.swing.JTextField timeTest;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
