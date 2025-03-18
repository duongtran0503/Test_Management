/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.qltn.UI;

import com.mycompany.qltn.BLL.ExamBLL;
import com.mycompany.qltn.BLL.QuestionBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.dto.ExamDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.TestDTO;
import com.mycompany.qltn.dto.TopicDTO;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER
 */
public class AdminCreateTestScreen extends javax.swing.JFrame {

    /**
     * Creates new form AdminCreateTestScreen
     */
    private ArrayList<String> listExamCod = new ArrayList<>();
    private Response.ExamResult examData;
    private final QuestionBLL questionBLL;
    private final ExamBLL examBLL;
    private Response.TopicResult topicResult;
    private String status = "new";
    private TestDTO testSelect;
    private ArrayList<ExamDTO> examOFTestSelected;

    public AdminCreateTestScreen() {
        this.examOFTestSelected = new ArrayList<>();
        this.testSelect = new TestDTO();
        this.topicResult = new Response.TopicResult();
        this.questionBLL = new QuestionBLL();
        this.examBLL = new ExamBLL();
        this.examData = new Response.ExamResult();

        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setValueSelectNameTest();
        setValueSelectTopic();
        changeValueWhenSelectTestName();
        setInforQueston(-1);
    }

    private void setInforQueston(int topicId) {
        Response.QuestoinResult easyQuestions = questionBLL.filterQuestionsResult(QuestionDTO.EASY, topicId);
        Response.QuestoinResult MeddiumQuestions = questionBLL.filterQuestionsResult(QuestionDTO.MEDIUM, topicId);
        Response.QuestoinResult DiffQuestions = questionBLL.filterQuestionsResult(QuestionDTO.DIFFICULT, topicId);
        this.labelEasyQ.setText("Số câu dẽ (hiện có " + easyQuestions.getQuestionList().size() + " câu )");
        this.labelMediumQ.setText("Số câu trung bình (hiện có " + MeddiumQuestions.getQuestionList().size() + " câu )");
        this.LabelDiff.setText("Số câu khó (hiện có " + DiffQuestions.getQuestionList().size() + " câu)");
    }

    private void changeValueWhenSelectTestName() {
        this.nameTestSelect.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                int itemSelected = this.nameTestSelect.getSelectedIndex();
                if (itemSelected != 0) {
                    this.status = "addExamCode";
                    this.nameTest.setText(this.examData.getTestLists().get(itemSelected - 1).getTestName());
                    this.testSelect = this.examData.getTestLists().get(itemSelected - 1);
                    this.topicSelect.setSelectedIndex(this.examData.getTestLists().get(itemSelected - 1).getTopicId());
                    setInforQueston(this.examData.getTestLists().get(itemSelected - 1).getTopicId());

                    this.listExamCod = new ArrayList<>();
                    for (ExamDTO exam : this.examData.getExamList()) {
                        if (exam.getTestId() == this.testSelect.getTestId()) {
                            this.listExamCod.add(exam.getExamCode());
                            this.examOFTestSelected.add(exam);
                        }
                    }
                    this.listExamCode.setText(this.listExamCod.toString());
                    this.timeTest.setText(this.examData.getTestLists().get(itemSelected - 1).getTestTime() + "");
                    this.easyQuestion.setText(this.examData.getTestLists().get(itemSelected - 1).getEasy() + "");
                    this.mediumQuestion.setText(this.examData.getTestLists().get(itemSelected - 1).getMedium() + "");
                    this.difiQuestion.setText(this.examData.getTestLists().get(itemSelected - 1).getDifficult() + "");
                    this.numberQuestion.setText(this.examData.getTestLists().get(itemSelected - 1).getTotalQuestion() + "");
                    this.numberQuestion.setEnabled(false);
                    this.topicSelect.setEnabled(false);
                    this.nameTopic.setEditable(false);
                    this.timeTest.setEnabled(false);
                    this.lableTitle.setText("Thêm đề thi vào bài thi!");
                    this.jButton1.setText("Thêm mã đề");
                } else {
                    setInforQueston(-1);
                    this.numberQuestion.setEnabled(true);
                    this.topicSelect.setEnabled(true);
                    this.nameTopic.setEditable(true);
                    this.timeTest.setEnabled(true);
                    this.status = "new";
                    this.nameTest.setText("");
                    this.listExamCod = new ArrayList<>();
                    this.listExamCode.setText("");
                    this.topicSelect.setSelectedIndex(0);
                    this.nameTopic.setText("");
                    this.lableTitle.setText("tạo bài thi mới!");
                    this.jButton1.setText("tạo bài thi");
                }
            }
        });
        this.topicSelect.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedOption = (String) this.topicSelect.getSelectedItem();
                setInforQueston(this.topicSelect.getSelectedIndex() + 1);
                this.nameTopic.setText(selectedOption);
            }
        });
    }

    private void setValueSelectNameTest() {
        this.examData = this.examBLL.getExamResult();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn bài thi trong hệ thống");
        for (TestDTO test : this.examData.getTestLists()) {
            model.addElement(test.getTestName());
        }
        this.nameTestSelect.setModel(model);
    }

    private void setValueSelectTopic() {
        this.topicResult = this.questionBLL.getTopic();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn chủ đề trong  hệ thống");
        for (TopicDTO topic : this.topicResult.getTopicList()) {
            model.addElement(topic.getTopicName());
        }
        this.topicSelect.setModel(model);
    }

    private boolean validateData() {
        String nameTest = this.nameTest.getText().trim();
        String nameTopic = this.nameTopic.getText().trim();

        if (nameTest.isBlank() || nameTopic.isBlank()) {
            JOptionPane.showMessageDialog(null, "Tên bài thi và chủ đề không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (this.listExamCod == null || this.listExamCod.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Bạn chưa thêm mã đề nào!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (this.timeTest.getText().trim().isBlank()) {
            JOptionPane.showMessageDialog(null, "Vui lòng thêm thời gian làm bài thi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        int numberQuestion;
        int easyQ;
        int mediumQ;
        int diffQ;
        int timeTest;
        int limit_num;
        try {
            numberQuestion = Integer.parseInt(this.numberQuestion.getText().trim());
            easyQ = Integer.parseInt(this.easyQuestion.getText().trim());
            mediumQ = Integer.parseInt(this.mediumQuestion.getText().trim());
            diffQ = Integer.parseInt(this.difiQuestion.getText().trim());
            timeTest = Integer.parseInt(this.timeTest.getText().trim());
            limit_num = Integer.parseInt(this.limit_num.getText().trim());

            if (numberQuestion <= 0 || easyQ < 0 || mediumQ < 0 || diffQ < 0 || timeTest <= 0 || limit_num <= 0) {
                JOptionPane.showMessageDialog(null, "Giá trị nhập vào không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            int total = easyQ + mediumQ + diffQ;
            if (total > numberQuestion) {
                JOptionPane.showMessageDialog(null, "Tổng số câu dễ, trung bình, khó lớn hơn số lượng câu hỏi của bài thi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (total < numberQuestion) {
                JOptionPane.showMessageDialog(null, "Tổng số câu dễ, trung bình, khó nhở hơn số lượng câu hỏi của bài thi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        this.nameTestConirm.setText(nameTest);
        this.nameTopicConfirm.setText(nameTopic);
        this.totalQuestionconfirm.setText(numberQuestion + "");
        this.timeConfirm.setText(timeTest + "");
        this.easyQConfirm.setText(easyQ + "");
        this.mediumQConfirm.setText(mediumQ + "");
        this.diffQCofirm.setText(diffQ + "");
        this.totalExamCode.setText(listExamCod.toString());
        this.limit_num_conf.setText(limit_num + "");
        return true;

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
        lableTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nameTestSelect = new javax.swing.JComboBox<>();
        nameTest = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        nameTopic = new javax.swing.JTextField();
        topicSelect = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        numberQuestion = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        examCode = new javax.swing.JTextField();
        buttonAddExamCode = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        listExamCode = new javax.swing.JLabel();
        labelEasyQ = new javax.swing.JLabel();
        easyQuestion = new javax.swing.JTextField();
        labelMediumQ = new javax.swing.JLabel();
        mediumQuestion = new javax.swing.JTextField();
        LabelDiff = new javax.swing.JLabel();
        difiQuestion = new javax.swing.JTextField();
        buttonValidate = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        timeTest = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        limit_num = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nameTestConirm = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        nameTopicConfirm = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        numberQuestionConfirm = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        timeConfirm = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        totalQuestionconfirm = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        easyQConfirm = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        mediumQConfirm = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        diffQCofirm = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        totalExamCode = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        limit_num_conf = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lableTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lableTitle.setText("Taọ bài thi mới");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lableTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lableTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tên bài thi");

        nameTestSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        nameTestSelect.setPreferredSize(new java.awt.Dimension(72, 30));
        nameTestSelect.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                nameTestSelectItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Chủ đề ");

        nameTopic.setPreferredSize(new java.awt.Dimension(73, 30));

        topicSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        topicSelect.setPreferredSize(new java.awt.Dimension(72, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Số Lượng câu hỏi");

        numberQuestion.setPreferredSize(new java.awt.Dimension(73, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setText("Thêm mã đề");

        examCode.setPreferredSize(new java.awt.Dimension(73, 30));
        examCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                examCodeActionPerformed(evt);
            }
        });

        buttonAddExamCode.setText("Thêm");
        buttonAddExamCode.setPreferredSize(new java.awt.Dimension(72, 30));
        buttonAddExamCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddExamCodeActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setText("Mã đề đã thêm");

        listExamCode.setText("Bạn chưa thêm mã đề nào!");
        listExamCode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        labelEasyQ.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelEasyQ.setText("Số lượng câu dẽ");

        easyQuestion.setText(" ");
        easyQuestion.setPreferredSize(new java.awt.Dimension(73, 30));

        labelMediumQ.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        labelMediumQ.setText("Số lượng câu trung bình");

        mediumQuestion.setText(" ");
        mediumQuestion.setPreferredSize(new java.awt.Dimension(73, 30));

        LabelDiff.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LabelDiff.setText("Số lượng câu khó");

        difiQuestion.setPreferredSize(new java.awt.Dimension(73, 30));

        buttonValidate.setText("Kiểm tra thông tin");
        buttonValidate.setPreferredSize(new java.awt.Dimension(126, 30));
        buttonValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonValidateActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Thời gian thi");

        timeTest.setPreferredSize(new java.awt.Dimension(64, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Số lần thi");

        limit_num.setText("1");
        limit_num.setPreferredSize(new java.awt.Dimension(64, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(labelEasyQ, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(easyQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(112, 112, 112))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelMediumQ, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(mediumQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)))
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listExamCode, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameTestSelect, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(numberQuestion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameTest, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(112, 112, 112)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(examCode, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(buttonAddExamCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(topicSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(nameTopic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(303, 303, 303)
                                .addComponent(buttonValidate, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(LabelDiff, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                                    .addComponent(difiQuestion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(109, 109, 109)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(timeTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                                .addGap(85, 85, 85)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(limit_num, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nameTestSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(topicSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTest, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTopic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(numberQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(examCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonAddExamCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(labelEasyQ))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(listExamCode, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(easyQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelMediumQ)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mediumQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelDiff)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(difiQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(timeTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(limit_num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonValidate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Thông tin bài thi:");

        jLabel6.setText("Tên bài thi:");

        jLabel8.setText("Chủ đè:");

        nameTopicConfirm.setText(" ");

        jLabel10.setText("Số lương câu hỏi :");

        jLabel12.setText("Thời gian làm bài:");

        jLabel14.setText("Số câu dẽ");

        jLabel16.setText("Số câu trung bình");

        easyQConfirm.setText(" ");
        easyQConfirm.setPreferredSize(new java.awt.Dimension(3, 20));

        jLabel18.setText("Số câu khó");

        mediumQConfirm.setText(" ");
        mediumQConfirm.setPreferredSize(new java.awt.Dimension(3, 20));

        jLabel20.setText(" Các mã đề:");

        diffQCofirm.setText(" ");

        jButton1.setText("Tạo bài thi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Hủy");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel11.setText("Số lược thi:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nameTestConirm, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(nameTopicConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(totalQuestionconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(72, 72, 72)
                                        .addComponent(numberQuestionConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(easyQConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                                .addComponent(timeConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(mediumQConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(diffQCofirm, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(totalExamCode, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(limit_num_conf, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTestConirm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTopicConfirm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(numberQuestionConfirm))
                    .addComponent(totalQuestionconfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(easyQConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(mediumQConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(diffQCofirm, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(totalExamCode, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(limit_num_conf, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn muốn hủy thao tác!", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void examCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_examCodeActionPerformed

    }//GEN-LAST:event_examCodeActionPerformed

    private void buttonValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonValidateActionPerformed
        validateData();
    }//GEN-LAST:event_buttonValidateActionPerformed

    private void buttonAddExamCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddExamCodeActionPerformed
        String examCode = this.examCode.getText();
        if (examCode.length() == 0) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập mã đề !", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean check = false;
        for (ExamDTO examDTO : this.examOFTestSelected) {
            if (examDTO.getExamCode().equalsIgnoreCase(examCode)) {
                check = true;
            }
        }

        for (String code : this.listExamCod) {
            if (examCode.equalsIgnoreCase(code)) {
                check = true;
            }
        }
        if (check) {
            JOptionPane.showMessageDialog(null, "Mã đề đã tồn tại !", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.listExamCod.add(examCode);
        this.listExamCode.setText(listExamCod.toString());
    }//GEN-LAST:event_buttonAddExamCodeActionPerformed

    private void nameTestSelectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_nameTestSelectItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTestSelectItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int currentExamCode = this.examOFTestSelected.size();
        if (this.listExamCod.size() == currentExamCode) {
            JOptionPane.showMessageDialog(null, "Các mã đề hiện tại đã tồn tại bạn chua thêm mã đề mới nào!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (status.equalsIgnoreCase("new")) {
            TestDTO test = new TestDTO();
            String topicValue = (String) this.nameTopicConfirm.getText();
            boolean check = false;
            for (TopicDTO topic : topicResult.getTopicList()) {
                if (topicValue.equalsIgnoreCase(topic.getTopicName())) {
                    check = true;
                    test.setTopicId(topic.getTopicId());
                }
            }
            if (!check) {
                test.setTopicId(this.topicResult.getTopicList().getLast().getTopicId() + 1);
            }
            test.setTestName(this.nameTestConirm.getText());
            test.setTestTime(Integer.parseInt(this.timeConfirm.getText()));
            test.setNumLimit(Integer.parseInt(this.limit_num_conf.getText()));
            int easyQ = Integer.parseInt(this.easyQConfirm.getText());

            int meddiumQ = Integer.parseInt(this.mediumQConfirm.getText());
            int diffQ = Integer.parseInt(this.diffQCofirm.getText());
            Response.BaseResponse res = examBLL.createNewTest(test, topicValue, listExamCod, easyQ, meddiumQ, diffQ);
            if (res.isIsSuccess()) {
                JOptionPane.showMessageDialog(null, res.getMessage());
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, res.getMessage());
                dispose();
            }
        } else if (status.equalsIgnoreCase("addExamCode")) {
            for (int i = 0; i < this.examOFTestSelected.size(); i++) {
                System.out.println("remo:" + i);
                this.listExamCod.remove(i);
            }
            TestDTO test = this.testSelect;
            int easyQ = Integer.parseInt(this.easyQConfirm.getText());
            int meddiumQ = Integer.parseInt(this.mediumQConfirm.getText());
            int diffQ = Integer.parseInt(this.diffQCofirm.getText());
            Response.BaseResponse res = this.examBLL.addNewExamCode(test, listExamCod, easyQ, meddiumQ, diffQ);
            if (res.isIsSuccess()) {
                JOptionPane.showMessageDialog(null, res.getMessage());
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, res.getMessage());
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lỗi tạo bài thi");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AdminCreateTestScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminCreateTestScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminCreateTestScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminCreateTestScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminCreateTestScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LabelDiff;
    private javax.swing.JButton buttonAddExamCode;
    private javax.swing.JButton buttonValidate;
    private javax.swing.JLabel diffQCofirm;
    private javax.swing.JTextField difiQuestion;
    private javax.swing.JLabel easyQConfirm;
    private javax.swing.JTextField easyQuestion;
    private javax.swing.JTextField examCode;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelEasyQ;
    private javax.swing.JLabel labelMediumQ;
    private javax.swing.JLabel lableTitle;
    private javax.swing.JTextField limit_num;
    private javax.swing.JLabel limit_num_conf;
    private javax.swing.JLabel listExamCode;
    private javax.swing.JLabel mediumQConfirm;
    private javax.swing.JTextField mediumQuestion;
    private javax.swing.JTextField nameTest;
    private javax.swing.JLabel nameTestConirm;
    private javax.swing.JComboBox<String> nameTestSelect;
    private javax.swing.JTextField nameTopic;
    private javax.swing.JLabel nameTopicConfirm;
    private javax.swing.JTextField numberQuestion;
    private javax.swing.JLabel numberQuestionConfirm;
    private javax.swing.JLabel timeConfirm;
    private javax.swing.JTextField timeTest;
    private javax.swing.JComboBox<String> topicSelect;
    private javax.swing.JLabel totalExamCode;
    private javax.swing.JLabel totalQuestionconfirm;
    // End of variables declaration//GEN-END:variables
}
