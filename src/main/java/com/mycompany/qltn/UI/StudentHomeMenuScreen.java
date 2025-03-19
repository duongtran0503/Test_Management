package com.mycompany.qltn.UI;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mycompany.qltn.DAL.QuestionDAL;
import com.mycompany.qltn.utils.UserSession;

/**
 * Student Home Menu Screen
 */
public class StudentHomeMenuScreen extends javax.swing.JFrame {

    public StudentHomeMenuScreen() {
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnWebProgramming = new javax.swing.JButton();
        btnMath = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnTravel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Home Menu");

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));

        btnWebProgramming.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnWebProgramming.setText("Lập trình Web");
        btnWebProgramming.addActionListener(evt -> openWebProgramming(evt));

        btnMath.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnMath.setText("Toán Học");
        btnMath.addActionListener(evt -> openMath(evt));

        btnBack.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnBack.setText("Quay lại");
        btnBack.addActionListener(evt -> goBack());

        btnTravel.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnTravel.setText("Du lịch");
        btnTravel.addActionListener(evt -> openTravel(evt));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnWebProgramming, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(btnMath, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(btnTravel, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnWebProgramming, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnMath, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnTravel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Frame.png")));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private void openWebProgramming(java.awt.event.ActionEvent evt) {                                        
        String loggedInEmail = UserSession.getEmail();
        int userId = QuestionDAL.getUserIdByEmail(loggedInEmail);

        int examId = 101; // ID bài kiểm tra Web (Thay bằng giá trị thực tế)
        
        if (userId == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StudentExamScreen studentExamScreen = new StudentExamScreen("Lập trình Web", userId, examId);
        studentExamScreen.setVisible(true);
        this.dispose();
    }    

    private void openMath(java.awt.event.ActionEvent evt) {                                        
        String loggedInEmail = UserSession.getEmail();
        int userId = QuestionDAL.getUserIdByEmail(loggedInEmail);

        int examId = 102; // ID bài kiểm tra Toán học

        if (userId == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StudentExamScreen studentExamScreen = new StudentExamScreen("Toán học", userId, examId);
        studentExamScreen.setVisible(true);
        this.dispose();
    }   

    private void openTravel(java.awt.event.ActionEvent evt) {                                        
        String loggedInEmail = UserSession.getEmail();
        int userId = QuestionDAL.getUserIdByEmail(loggedInEmail);

        int examId = 103; // ID bài kiểm tra Du lịch

        if (userId == -1) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy người dùng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StudentExamScreen studentExamScreen = new StudentExamScreen("Du lịch", userId, examId);
        studentExamScreen.setVisible(true);
        this.dispose();
    }   


    private void goBack() {
        new StudenHomeScreen().setVisible(true);
        this.dispose();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> new StudentHomeMenuScreen().setVisible(true));
    }

    private javax.swing.JButton btnWebProgramming;
    private javax.swing.JButton btnMath;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnTravel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
}