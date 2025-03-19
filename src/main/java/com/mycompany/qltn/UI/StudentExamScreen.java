package com.mycompany.qltn.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class StudentExamScreen extends JFrame {
    private String category;
    private String[] questions;
    private String[][] options;
    private String[] correctAnswers;
    private JButton[][] optionButtons;
    private String[] selectedAnswers;
    private int userId;
    private int examId;
    private Timestamp startTime;

    public StudentExamScreen(String category) {
        this.category = category;
        this.userId = userId;
        this.examId = examId;
        this.startTime = new Timestamp(System.currentTimeMillis()); // Record the start time
        setTitle("Bài kiểm tra - " + category);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadQuestions(category);

        selectedAnswers = new String[10];
        optionButtons = new JButton[10][4];

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < 10; i++) {
            JPanel questionPanel = new JPanel(new BorderLayout());
            questionPanel.setBorder(BorderFactory.createTitledBorder("Câu " + (i + 1)));

            // Câu hỏi
            JLabel questionLabel = new JLabel("<html><b>" + questions[i] + "</b></html>");
            questionPanel.add(questionLabel, BorderLayout.NORTH);

            // Panel chứa 4 nút chọn
            JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));

            for (int j = 0; j < 4; j++) {
                JButton btn = new JButton((char) ('A' + j) + ". " + options[i][j]);
                btn.setActionCommand(options[i][j]);
                btn.setFont(new Font("Arial", Font.PLAIN, 14));
                btn.setFocusPainted(false);

                final int questionIndex = i;
                final int optionIndex = j;

                btn.addActionListener(e -> {
                    selectedAnswers[questionIndex] = btn.getActionCommand();
                    updateButtonStyles(questionIndex, optionIndex);
                });

                optionButtons[i][j] = btn;
                optionsPanel.add(btn);
            }

            questionPanel.add(optionsPanel, BorderLayout.CENTER);
            mainPanel.add(questionPanel);
        }

        // Nút Nộp bài
        JButton submitButton = new JButton("Nộp bài");
        submitButton.setPreferredSize(new Dimension(150, 40)); // Kích thước nút

        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.setPreferredSize(new Dimension(200, 50)); // Giảm chiều cao
        submitPanel.setMaximumSize(new Dimension(200, 60)); // Hạn chế kích thước tối đa
        submitButton.addActionListener(e -> checkAnswers());
        submitPanel.add(submitButton);
        mainPanel.add(submitPanel);

        mainPanel.add(submitPanel); // Thêm vào giao diện

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);
    }

    private void updateButtonStyles(int questionIndex, int selectedOption) {
        for (int j = 0; j < 4; j++) {
            if (j == selectedOption) {
                // Highlight the selected button
                optionButtons[questionIndex][j].setBackground(Color.BLUE);
                optionButtons[questionIndex][j].setForeground(Color.WHITE);
            } else {
                // Reset other buttons to their default color
                optionButtons[questionIndex][j].setBackground(UIManager.getColor("Button.background"));
                optionButtons[questionIndex][j].setForeground(UIManager.getColor("Button.foreground"));
            }
        }
    }

    private void loadQuestions(String category) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/quanlytracnghiem"; // Cập nhật thông tin CSDL
        String dbUser = "root";
        String dbPassword = "Khactuong1402";
    
        List<String> questionList = new ArrayList<>();
        List<String[]> optionList = new ArrayList<>();
        List<String> correctAnswerList = new ArrayList<>();
    
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            // Truy vấn danh sách câu hỏi dựa trên category
            String sql = "SELECT q.question_id, q.question_text " +
                         "FROM questions q " +
                         "JOIN topics t ON q.topic_id = t.topic_id " +
                         "WHERE t.topic_name = ? LIMIT 10";
    
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, category);
                ResultSet rs = stmt.executeQuery();
    
                while (rs.next()) {
                    int questionId = rs.getInt("question_id");
                    String questionText = rs.getString("question_text");
    
                    questionList.add(questionText);
                    System.out.println("Loaded Question: " + questionText);
    
                    // Lấy danh sách đáp án cho câu hỏi này
                    String[] options = new String[4]; // Mặc định 4 đáp án
                    String correctAnswer = null;
    
                    String answerSql = "SELECT answer_text, is_correct FROM answers WHERE question_id = ?";
                    try (PreparedStatement answerStmt = conn.prepareStatement(answerSql)) {
                        answerStmt.setInt(1, questionId);
                        ResultSet answerRs = answerStmt.executeQuery();
    
                        int index = 0;
                        while (answerRs.next() && index < 4) {
                            options[index] = answerRs.getString("answer_text");
                            System.out.println("Option: " + options[index]);
    
                            if (answerRs.getBoolean("is_correct")) {
                                correctAnswer = options[index];
                            }
                            index++;
                        }
    
                        // Kiểm tra nếu không có đủ đáp án, tránh lỗi ArrayIndexOutOfBoundsException
                        if (index < 4) {
                            for (int i = index; i < 4; i++) {
                                options[i] = "N/A"; // Gán giá trị mặc định
                            }
                        }
                    }
    
                    optionList.add(options);
                    correctAnswerList.add(correctAnswer != null ? correctAnswer : "N/A");
                }
            }
    
            // Kiểm tra nếu danh sách câu hỏi rỗng
            if (questionList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có câu hỏi nào trong danh mục này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
    
            // Chuyển dữ liệu từ danh sách về mảng
            questions = questionList.toArray(new String[0]);
            options = optionList.toArray(new String[0][0]);
            correctAnswers = correctAnswerList.toArray(new String[0]);
    
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi tải câu hỏi từ CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }    

    private void checkAnswers() {
        for (int i = 0; i < 10; i++) {
            if (selectedAnswers[i] == null) {
                JOptionPane.showMessageDialog(this,
                        "Bạn phải trả lời tất cả các câu hỏi trước khi nộp bài!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
    
        int score = 0;
        for (int i = 0; i < 10; i++) {
            if (selectedAnswers[i].equals(correctAnswers[i])) {
                score++;
            }
        }
    
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
    
        String jdbcUrl = "jdbc:mysql://localhost:3306/quanlytracnghiem";
        String dbUser = "root";
        String dbPassword = "Khactuong1402";
    
        String insertSql = "INSERT INTO results (user_id, exam_id, start_time, end_time, score, correct) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(insertSql)) {
    
            stmt.setInt(1, userId);
            stmt.setInt(2, examId);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, endTime);
            stmt.setInt(5, score);
            stmt.setDouble(6, (double) score / 10 * 100);
    
            stmt.executeUpdate();
            System.out.println("Result saved successfully.");
    
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi lưu kết quả vào CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    
        JOptionPane.showMessageDialog(this, "Bạn đạt: " + score + "/10", "Kết quả", JOptionPane.INFORMATION_MESSAGE);
    
        this.dispose();
        SwingUtilities.invokeLater(() -> new StudentHomeMenuScreen().setVisible(true));
    }    
}