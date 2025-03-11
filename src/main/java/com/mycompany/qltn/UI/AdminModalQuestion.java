/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.qltn.UI;

import com.mycompany.qltn.BLL.ExamBLL;
import com.mycompany.qltn.BLL.QuestionBLL;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.dto.OptionDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.utils.RandomStringGenerator;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ACER
 */
public class AdminModalQuestion extends javax.swing.JPanel {

    public static final String MODAL_ADD = "add";
    public static final String MODAL_UPDATE = "update";
    public static final String MODAL_ADD_FOR_EXAM = "exam";
    private int questionId;
    private String task = "add";
    private final QuestionBLL questionBLL;
    private String imagePath;
    private final ExamBLL examBLL;

    private int examCode;

    public AdminModalQuestion() {
        this.examBLL = new ExamBLL();
        questionBLL = new QuestionBLL();
        initComponents();
        this.buttonCancel.setBackground(Color.red);
        DefaultComboBoxModel<String> modelLever = new DefaultComboBoxModel<>();

        modelLever.addElement("easy");
        modelLever.addElement("medium");
        modelLever.addElement("difficult");
        this.selectLever.setModel(modelLever);
        DefaultComboBoxModel<String> modelTopic = new DefaultComboBoxModel<>();

        modelTopic.addElement("Lập trình");
        modelTopic.addElement("Du lịch");
        modelTopic.addElement("Toán học");
        this.selectTopic.setModel(modelTopic);
        setupImageChooser();

        DefaultComboBoxModel<String> modelAnswer = new DefaultComboBoxModel<>();
        modelAnswer.addElement("chọn đáp án");
        modelAnswer.addElement("A");
        modelAnswer.addElement("B");
        modelAnswer.addElement("C");
        modelAnswer.addElement("D");
        this.selectAnswer.setModel(modelAnswer);
        setupImageChooser();

    }

    public void setValueQuestion(QuestionDTO questionDTO, ArrayList<OptionDTO> listAnswer, int Answer) {
        this.inputContent.setText(questionDTO.getQuestionText());
        this.inputOptionA.setText(listAnswer.get(0).getOptionText());
        this.inputOptionB.setText(listAnswer.get(1).getOptionText());
        this.inputOptionC.setText(listAnswer.get(2).getOptionText());
        this.inputOptionD.setText(listAnswer.get(3).getOptionText());
        this.selectAnswer.setSelectedIndex(Answer + 1);
        switch (questionDTO.getDifficulty()) {
            case "easy" ->
                this.selectLever.setSelectedIndex(0);
            case "medium" ->
                this.selectLever.setSelectedIndex(1);
            case "difficult" ->
                this.selectLever.setSelectedIndex(2);

        }
        this.selectTopic.setSelectedIndex(questionDTO.getTopicId() - 1);
        if (questionDTO.getImageUrl() != null) {
            displayImagePreviewFromResource(questionDTO.getImageUrl());
        }
        this.update_at.setText("Cập nhật lúc:" + questionDTO.getUpdated_at().toString());
        this.updater.setText("Người cập nhật:" + questionDTO.getUpdater());
        this.create_at.setText("Ngày tạo:" + questionDTO.getCreate_ar().toString());
        this.buttonAdd.setText("Cập nhật");
        this.questionId = questionDTO.getQuestionId();

    }

    private void closeParentFrame() {
        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parent != null) {
            parent.dispose(); // Đóng cửa sổ chứa JPanel
        }
    }

    public int getExamCode() {
        return examCode;
    }

    public void setExamCode(int examCode) {
        this.examCode = examCode;
    }
     public  void setTextTitleInTopImage(String text){
     this.jLabel9.setText(text);
    }

    private void validationData() {
        int selectTopicValue = this.selectTopic.getSelectedIndex() + 1;
        String selectLeverValue = (String) this.selectLever.getSelectedItem();
        String contentQuestion = this.inputContent.getText().trim();
        int selectAnswer = this.selectAnswer.getSelectedIndex();
        String optionA = this.inputOptionA.getText().trim();
        String optionB = this.inputOptionB.getText().trim();
        String optionC = this.inputOptionC.getText().trim();
        String optionD = this.inputOptionD.getText().trim();

        boolean isValid = true;

        if (contentQuestion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nội dung câu hỏi không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        } else if (contentQuestion.length() < 4) {
            JOptionPane.showMessageDialog(this, "Nội dung câu hỏi phải có ít nhất 4 ký tự.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        if (optionA.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nội dung đáp án A không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        if (optionB.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nội dung đáp án B không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        if (optionC.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nội dung đáp án C không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }

        if (optionD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nội dung đáp án D không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }
        if (selectAnswer == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đáp án đúng cho câu hỏi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            isValid = false;
        }
        if (isValid) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionId(this.questionId);
            questionDTO.setTopicId(selectTopicValue);
            questionDTO.setDifficulty(selectLeverValue);
            questionDTO.setImageUrl(imagePath);
            questionDTO.setQuestionText(contentQuestion);
            ArrayList<OptionDTO> listAnswer = new ArrayList<>();
            OptionDTO optionDTOA = new OptionDTO();
            optionDTOA.setOptionText(optionA);
            listAnswer.add(optionDTOA);

            OptionDTO optionDTOB = new OptionDTO();
            optionDTOB.setOptionText(optionB);
            listAnswer.add(optionDTOB);

            OptionDTO optionDTOC = new OptionDTO();
            optionDTOC.setOptionText(optionC);
            listAnswer.add(optionDTOC);

            OptionDTO optionDTOD = new OptionDTO();
            optionDTOD.setOptionText(optionD);
            listAnswer.add(optionDTOD);

            switch (selectAnswer) {
                case 1 ->
                    optionDTOA.setIsCorrect(true);
                case 2 ->
                    optionDTOB.setIsCorrect(true);
                case 3 ->
                    optionDTOC.setIsCorrect(true);
                case 4 ->
                    optionDTOD.setIsCorrect(true);

            }

            Response.QuestoinResult res = new Response.QuestoinResult();
            if (MODAL_ADD.equalsIgnoreCase(this.task)) {
                res = questionBLL.addQuestoin(questionDTO, listAnswer);
            } else if (MODAL_ADD_FOR_EXAM.equalsIgnoreCase(this.task)) {
                res = questionBLL.addQuestoin(questionDTO, listAnswer);
                Response.ExamResult result = examBLL.addQuestionToTheTest(res.getQuestionList(), examCode);
                res.setMessage(result.getMessage());
                res.setIsSuccess(result.isIsSuccess());
            } else {
                res = questionBLL.updateQuestoin(questionDTO, listAnswer);
            }
            if (res.isIsSuccess()) {
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        res.getMessage(),
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    closeParentFrame();
                }
            } else {
                JOptionPane.showMessageDialog(null, res.getMessage());
            }

        }
    }
   

    private void setupImageChooser() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        buttonAddImage.addActionListener((ActionEvent e) -> {
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File selectedFile1 = fileChooser.getSelectedFile();
                displayImagePreviewFromSistem(selectedFile1);
                saveImageToResources(selectedFile1);
            }
        });
    }

    private void displayImagePreviewFromResource(String imageName) {
        try {
            ClassLoader classLoader = AdminModalQuestion.class.getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("imageQuestion/" + imageName);
            if (inputStream == null) {
                throw new IOException("Không tìm thấy ảnh: " + imageName);
            }
            BufferedImage img = ImageIO.read(inputStream);
            ImageIcon icon = new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            labelImage.setIcon(icon);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải ảnh: " + imageName, "Lỗi", JOptionPane.ERROR_MESSAGE);
            labelImage.setIcon(null); // Xóa ảnh cũ nếu có lỗi
        }
    }

    private void displayImagePreviewFromSistem(File selectedFile) {
        try {
            BufferedImage img = ImageIO.read(selectedFile);
            ImageIcon icon = new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
            labelImage.setIcon(icon);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể tải ảnh.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveImageToResources(File selectedFile) {
        try {
            ClassLoader classLoader = AdminModalQuestion.class.getClassLoader();
            URL resourceUrl = classLoader.getResource("imageQuestion/");

            if (resourceUrl == null) {
                JOptionPane.showMessageDialog(null, "Thư mục imageQuestion không tồn tại trong resources.");
                return;
            }

            String resourcePath = resourceUrl.getFile();
            String randomToken = RandomStringGenerator.generateRandomString(10);
            File destinationFile = new File(resourcePath + randomToken + "_" + selectedFile.getName());
            Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            this.imagePath = randomToken + "_" + selectedFile.getName();
            System.out.println("Ảnh được lưu tại: " + imagePath);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lôi lưu ảnh" + ex.getMessage());

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
        labelImage = new javax.swing.JLabel();
        buttonAddImage = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        create_at = new javax.swing.JLabel();
        update_at = new javax.swing.JLabel();
        updater = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        selectTopic = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputContent = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        selectLever = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        inputOptionA = new javax.swing.JTextField();
        inputOptionB = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        inputOptionC = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        inputOptionD = new javax.swing.JTextField();
        buttonCancel = new javax.swing.JButton();
        buttonAdd = new javax.swing.JButton();
        selectAnswer = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        labelImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        buttonAddImage.setText("Thêm ảnh");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Thêm câu hỏi mới");

        create_at.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        update_at.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        updater.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(create_at, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(update_at, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updater, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(buttonAddImage)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(45, 45, 45))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonAddImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(create_at, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(update_at, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updater, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Chọn chủ để");

        selectTopic.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Chọn mức độ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Nội dụng");

        inputContent.setColumns(20);
        inputContent.setRows(5);
        jScrollPane1.setViewportView(inputContent);

        jLabel4.setText("Đáp án A");

        selectLever.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Đáp án B");

        jLabel6.setText("Đáp án C");

        jLabel7.setText("Đáp án D");

        buttonCancel.setText("Hủy");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        buttonAdd.setText("Thêm");
        buttonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddActionPerformed(evt);
            }
        });

        selectAnswer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Đáp án đúng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(selectTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(143, 143, 143)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectLever, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(inputOptionA, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(inputOptionC, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(113, 113, 113)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(inputOptionD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(inputOptionB, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(159, 159, 159)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(150, 150, 150))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(611, Short.MAX_VALUE)
                    .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(142, 142, 142)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectLever, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputOptionA, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(inputOptionB, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputOptionC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputOptionD, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(selectAnswer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(buttonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(462, Short.MAX_VALUE)
                    .addComponent(buttonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(16, 16, 16)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn hủy thao tác?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            closeParentFrame();
        }
    }//GEN-LAST:event_buttonCancelActionPerformed
    public String getTask() {
        return task;
    }

    /**
     * Creates new form AdminAddQuestion
     */
    public void setTask(String task) {
        this.task = task;
    }

    private void buttonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddActionPerformed
        validationData();
    }//GEN-LAST:event_buttonAddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAdd;
    private javax.swing.JButton buttonAddImage;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JLabel create_at;
    private javax.swing.JTextArea inputContent;
    private javax.swing.JTextField inputOptionA;
    private javax.swing.JTextField inputOptionB;
    private javax.swing.JTextField inputOptionC;
    private javax.swing.JTextField inputOptionD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelImage;
    private javax.swing.JComboBox<String> selectAnswer;
    private javax.swing.JComboBox<String> selectLever;
    private javax.swing.JComboBox<String> selectTopic;
    private javax.swing.JLabel update_at;
    private javax.swing.JLabel updater;
    // End of variables declaration//GEN-END:variables
}
