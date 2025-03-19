package com.mycompany.qltn.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

import com.mycompany.qltn.DAL.QuestionDAL;
import com.mycompany.qltn.dto.AnswerDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.ResultDTO;


public class StudentExamScreen extends JFrame {
    private String category;
    private List<QuestionDTO> questions;
    private JButton[][] optionButtons;
    private String[] selectedAnswers;
    private int userId;
    private int examId;
    private Timestamp startTime;

    public StudentExamScreen(String category, int userId, int examId) {
        this.category = category;
        this.userId = userId;
        this.examId = examId;
        this.startTime = new Timestamp(System.currentTimeMillis());
    
        setTitle("Bài kiểm tra - " + category);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    
        // Khởi tạo đối tượng QuestionDAL
        QuestionDAL questionDAL = new QuestionDAL();
        questions = questionDAL.getQuestionsByCategory(category);
    
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có câu hỏi nào trong danh mục này!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            return;
        }
    
        selectedAnswers = new String[questions.size()];
        optionButtons = new JButton[questions.size()][4];
    
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    
        for (int i = 0; i < questions.size(); i++) {
            JPanel questionPanel = new JPanel(new BorderLayout());
            questionPanel.setBorder(BorderFactory.createTitledBorder("Câu " + (i + 1)));
    
            JLabel questionLabel = new JLabel("<html><b>" + questions.get(i).getQuestionText() + "</b></html>");
            questionPanel.add(questionLabel, BorderLayout.NORTH);
    
            JPanel optionsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
    
            List<AnswerDTO> answerOptions = questions.get(i).getAnswers();
            if (answerOptions == null || answerOptions.isEmpty()) {
                System.out.println("Question " + (i + 1) + " has no answers!");
                continue;
            }
    
            for (int j = 0; j < answerOptions.size(); j++) {
                JButton btn = new JButton((char) ('A' + j) + ". " + answerOptions.get(j).getAnswerText());
                btn.setActionCommand(answerOptions.get(j).getAnswerText());
                btn.setFont(new Font("Arial", Font.PLAIN, 14));
    
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
    
        JButton submitButton = new JButton("Nộp bài");
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.addActionListener(e -> checkAnswers());
    
        JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        submitPanel.add(submitButton);
        mainPanel.add(submitPanel);
    
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane);
    }    

    private void updateButtonStyles(int questionIndex, int selectedOption) {
        for (int j = 0; j < 4; j++) {
            if (optionButtons[questionIndex][j] != null) {
                if (j == selectedOption) {
                    optionButtons[questionIndex][j].setBackground(Color.BLUE);
                    optionButtons[questionIndex][j].setForeground(Color.WHITE);
                } else {
                    optionButtons[questionIndex][j].setBackground(UIManager.getColor("Button.background"));
                    optionButtons[questionIndex][j].setForeground(UIManager.getColor("Button.foreground"));
                }
            }
        }
    }

    private void checkAnswers() {
        for (int i = 0; i < questions.size(); i++) {
            if (selectedAnswers[i] == null) {
                JOptionPane.showMessageDialog(this, "Bạn phải trả lời tất cả các câu hỏi trước khi nộp bài!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            for (AnswerDTO answer : questions.get(i).getAnswers()) {
                if (answer.isCorrect() && selectedAnswers[i].equals(answer.getAnswerText())) {
                    score++;
                    break;
                }
            }
        }

        LocalDateTime startTimeLocal = startTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        LocalDateTime endTimeLocal = endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


        ResultDTO result = new ResultDTO(0, userId, examId, startTimeLocal, endTimeLocal, score);
        QuestionDAL.saveResult(result);

        JOptionPane.showMessageDialog(this, "Bạn đạt: " + score + "/" + questions.size(), "Kết quả", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        SwingUtilities.invokeLater(() -> new StudentHomeMenuScreen().setVisible(true));
    }
}