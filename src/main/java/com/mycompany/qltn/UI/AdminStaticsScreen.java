/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn.UI;

import com.mycompany.qltn.BLL.ExamBLL;
import com.mycompany.qltn.BLL.QuestionBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.BLL.UserBLL;
import com.mycompany.qltn.dto.ExamDTO;
import com.mycompany.qltn.dto.ResultDTO;
import com.mycompany.qltn.dto.TestDTO;
import com.mycompany.qltn.dto.TopicDTO;
import java.awt.event.ItemEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class AdminStaticsScreen extends javax.swing.JFrame {

    /**
     * Creates new form AdminStaticsScreen
     */
    private Response.ExamResult examResultData;
    private final ExamBLL examBLL;
    private final QuestionBLL questionBLL;
    private final UserBLL userBLL;
    private ExamDTO examDTOData;
    private ArrayList<TestDTO> testData;

    public AdminStaticsScreen() {
        this.testData = new ArrayList<>();
        this.examDTOData = new ExamDTO();
        this.userBLL = new UserBLL();
        this.examResultData = new Response.ExamResult();
        this.questionBLL = new QuestionBLL();
        this.examBLL = new ExamBLL();
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setValueTestNameSelectResult(1);
        setValueStaticsView(1);
        loadDataOnTable();
        jTable1.setRowHeight(50);
        jTable1.setFocusable(false);
        addEventOnChangeForSelect();
        addEventOnChangeForTestNameSelect();
    }

    private void addEventOnChangeForTestNameSelect() {
        this.testNameSelect.addItemListener(((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int index = this.testNameSelect.getSelectedIndex();
                displayDataOnTable(this.testData.get(index).getTestId());

            }
        }));
    }

    private void addEventOnChangeForSelect() {
        this.topicSelect.addItemListener(((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int index = this.topicSelect.getSelectedIndex();
                System.out.println("item slec" + index);
                Response.ExamResult resultExam = examBLL.search(0, "", index + 1);

                updateValueTestNameSelect(resultExam.getTestLists());

            }
        }));
    }

    private void setValueStaticsView(int topicId) {
        Response.ExamResult resultExam = examBLL.search(0, "", topicId);
        int examId = 0;
        for (ExamDTO exam : resultExam.getExamList()) {
            if (exam.getTestId() == resultExam.getTestLists().getFirst().getTestId()) {
                examId = exam.getExamId();
            }
        }
        this.examDTOData.setExamId(examId);
        Response.TestResult result = examBLL.getTestsResult(examId);
        this.totalStudent.setText(result.getTestResultList().size() + "");
        int totalCompleted = 0;
        int totalUnfinished = 0;
        for (ResultDTO resultDTO : result.getTestResultList()) {
            if (resultDTO.getCorrect() < 50) {
                totalUnfinished++;
            }
            totalCompleted++;
        }
        this.totalCompleted.setText(totalCompleted + "");
        this.totalUnfinished.setText(totalUnfinished + "");
    }

    private void updateValueTestNameSelect(ArrayList<TestDTO> testList) {
        DefaultComboBoxModel<String> modelTestName = new DefaultComboBoxModel<>();
        this.testData = new ArrayList<>();
        for (TestDTO test : testList) {
            modelTestName.addElement(test.getTestName());
            this.testData.add(test);
        }
        this.testNameSelect.setModel(modelTestName);
        displayDataOnTable(testList.getFirst().getTestId());
        setValueStaticsView(testList.getFirst().getTopicId());
    }

    private void setValueTestNameSelectResult(int topicId) {
        DefaultComboBoxModel<String> modelTopic = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> modelTestName = new DefaultComboBoxModel<>();
        Response.TopicResult topics = this.questionBLL.getTopic();
        for (TopicDTO topic : topics.getTopicList()) {
            modelTopic.addElement(topic.getTopicName());
        }
        Response.ExamResult result = examBLL.search(0, "", topicId);
        System.out.println("le:" + result.getTestLists().size());
        for (TestDTO test : result.getTestLists()) {

            modelTestName.addElement(test.getTestName());
            this.testData.add(test);
        }
        this.topicSelect.setModel(modelTopic);
        this.testNameSelect.setModel(modelTestName);
    }

    private void displayDataOnTable(int testId) {
        DefaultTableModel tableModel = (DefaultTableModel) this.jTable1.getModel();
        tableModel.setRowCount(0);

        Response.ExamResult listExam = examBLL.getTestById(testId);
        Response.TestResult testResult = new Response.TestResult();
        testResult.setTestResultList(new ArrayList<>());
        testResult.setTestResultDetailList(new HashMap<>());
        Response.ContestAntResult antResult = examBLL.getUserContestAntResult(testId);
        int totalUser = antResult.getTotal();
        int totalCompleted = antResult.getCompleted();
        int totalUnfinished = antResult.getTotal() - antResult.getCompleted();
        this.totalStudent.setText(totalUser + "");
        this.totalCompleted.setText(totalCompleted + "");
        this.totalUnfinished.setText(totalUnfinished + "");
        for (ExamDTO exam : listExam.getExamList()) {
            ArrayList<ResultDTO> listResult = examBLL.getTestsResult(exam.getExamId()).getTestResultList();
            for (ResultDTO rs : listResult) {
                testResult.getTestResultList().add(rs);
            }
        }

        for (ResultDTO resultDTO : testResult.getTestResultList()) {
            System.out.println("id" + resultDTO.getUserId());
            Response.UserResult user = userBLL.getUserById(resultDTO.getUserId());
            System.out.println("ủe" + user.getUserList().getFirst().getUsername());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            Duration duration = Duration.between(resultDTO.getStartTime(), resultDTO.getEndTime());
            String time = duration.toHours() + "h:" + duration.toMinutesPart() + "p:" + duration.toSecondsPart();
            String examCode = "";
            for (ExamDTO exam : listExam.getExamList()) {
                if (exam.getExamId() == resultDTO.getExamId()) {
                    examCode = exam.getExamCode();
                }
            }
            tableModel.addRow(new Object[]{user.getUserList().getFirst().getUsername(), examCode, resultDTO.getCorrect() + "%", resultDTO.getScore() + "/100", time, resultDTO.getStartTime().format(formatter)});
        }

    }

    private void loadDataOnTable() {
        Response.ExamResult resultExam = examBLL.search(0, "", 1);

        displayDataOnTable(resultExam.getTestLists().getFirst().getTestId());
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
        topicSelect = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        testNameSelect = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        totalStudent = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        totalCompleted = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        totalUnfinished = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Thông kê bài thi");

        topicSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        topicSelect.setPreferredSize(new java.awt.Dimension(72, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Chủ đề thi:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Bài thi:");

        testNameSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        testNameSelect.setPreferredSize(new java.awt.Dimension(72, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(topicSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(testNameSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(topicSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addComponent(testNameSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButton1.setText("thoát");
        jButton1.setPreferredSize(new java.awt.Dimension(72, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Sô lượng Sinh viên dự thi");

        totalStudent.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        totalStudent.setText("30");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Sinh viên");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Số lượng sinh viện đạt:");

        totalCompleted.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        totalCompleted.setText("30");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Sinh viên");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Số lượng sinh viên không đat:");

        totalUnfinished.setText("30");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Sinh viên");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalStudent, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(totalCompleted, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(totalUnfinished, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(totalStudent)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(totalCompleted)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(totalUnfinished)
                    .addComponent(jLabel12))
                .addContainerGap())
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Tên ", "Mã đề", "Tỉ lệ trả lời đúng", "điểm", "Thời gian làm bài", "Ngày thi", "Xem chi tiết"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AdminHomeScreen adminHomeScreen = new AdminHomeScreen();
        adminHomeScreen.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> testNameSelect;
    private javax.swing.JComboBox<String> topicSelect;
    private javax.swing.JLabel totalCompleted;
    private javax.swing.JLabel totalStudent;
    private javax.swing.JLabel totalUnfinished;
    // End of variables declaration//GEN-END:variables
}
