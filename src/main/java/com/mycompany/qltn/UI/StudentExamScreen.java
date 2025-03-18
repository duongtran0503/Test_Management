package com.mycompany.qltn.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

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

    public StudentExamScreen(String category) {
        this.category = category;
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
        if (category.equals("Lập trình Web")) {
            questions = new String[]{
                "HTML là viết tắt của gì?", "Thẻ nào dùng để tạo đường link?", "Ngôn ngữ nào là nền tảng của web?", 
                "CSS viết tắt của?", "Thẻ nào dùng để chèn ảnh?", "JS là viết tắt của?", 
                "API là gì?", "Flexbox là gì?", "Bootstrap là gì?", "SEO có nghĩa là?"
            };
            options = new String[][]{
                {"Hyper Text Markup Language", "Hyperlink Text Markup Language", "High Text Machine Learning", "Home Tool Markup Language"},
                {"<link>", "<a>", "<url>", "<href>"},
                {"HTML", "Java", "Python", "C++"},
                {"Cascading Style Sheets", "Computer Style Sheets", "Colorful Style Sheets", "Creative Style Sheets"},
                {"<image>", "<img>", "<src>", "<picture>"},
                {"JavaScript", "JavaServer", "JScript", "JsonScript"},
                {"Application Programming Interface", "Advanced Programming Interface", "Automated Programming Interface", "Applied Protocol Integration"},
                {"A layout module in CSS", "A JavaScript library", "A backend framework", "A database"},
                {"A CSS framework", "A JavaScript library", "A backend framework", "A CMS"},
                {"Search Engine Optimization", "System Engine Optimization", "Structured Engine Operation", "Site Evaluation Optimization"}
            };
            correctAnswers = new String[]{
                "Hyper Text Markup Language", "<a>", "HTML", "Cascading Style Sheets", "<img>", 
                "JavaScript", "Application Programming Interface", "A layout module in CSS", "A CSS framework", "Search Engine Optimization"
            };
        } else if (category.equals("Toán học")) {
            questions = new String[]{
                "2 + 2 = ?", "5 * 6 = ?", "12 / 4 = ?", "Căn bậc hai của 16 là gì?", "7 - 3 = ?", 
                "9 + 10 = ?", "6 * 7 = ?", "Số Pi gần đúng là bao nhiêu?", "12 + 8 = ?", "Số nguyên tố nhỏ nhất là?"
            };
            options = new String[][]{
                {"3", "4", "5", "6"},
                {"20", "30", "40", "50"},
                {"2", "3", "4", "5"},
                {"2", "3", "4", "5"},
                {"3", "4", "5", "6"},
                {"19", "20", "21", "22"},
                {"40", "42", "44", "46"},
                {"3.14", "3.15", "3.16", "3.17"},
                {"18", "19", "20", "21"},
                {"1", "2", "3", "5"}
            };
            correctAnswers = new String[]{"4", "30", "3", "4", "4", "19", "42", "3.14", "20", "2"};
        } else if (category.equals("Du lịch")) {
            questions = new String[]{
                "Thủ đô của Việt Nam là?", "Thành phố nào có Nhà hát Con Sò?", "Nơi nào nổi tiếng với Kim tự tháp?", 
                "Thành phố nào là kinh đô ánh sáng?", "Nơi nào có tượng Nữ thần Tự do?", 
                "Kỳ quan thế giới nằm ở Trung Quốc là gì?", "Thành phố nào có đấu trường La Mã?", 
                "Thành phố nào có cầu cổng vàng?", "Đất nước nào nổi tiếng với sushi?", "Vạn lý trường thành dài khoảng bao nhiêu?"
            };
            options = new String[][]{
                {"Hà Nội", "Hồ Chí Minh", "Đà Nẵng", "Huế"},
                {"Sydney", "Melbourne", "Brisbane", "Perth"},
                {"Ai Cập", "Mexico", "Ấn Độ", "Mỹ"},
                {"Paris", "London", "New York", "Rome"},
                {"New York", "Los Angeles", "Chicago", "Miami"},
                {"Vạn Lý Trường Thành", "Đền Taj Mahal", "Đấu Trường La Mã", "Kim Tự Tháp"},
                {"Rome", "Madrid", "Athens", "Berlin"},
                {"San Francisco", "Los Angeles", "Seattle", "Chicago"},
                {"Nhật Bản", "Hàn Quốc", "Trung Quốc", "Thái Lan"},
                {"21.000 km", "10.000 km", "15.000 km", "8.000 km"}
            };
            correctAnswers = new String[]{"Hà Nội", "Sydney", "Ai Cập", "Paris", "New York", 
                "Vạn Lý Trường Thành", "Rome", "San Francisco", "Nhật Bản", "21.000 km"};
        }
    }

    private void checkAnswers() {
        // Check if all questions are answered
        for (int i = 0; i < 10; i++) {
            if (selectedAnswers[i] == null) { // If any question is unanswered
                JOptionPane.showMessageDialog(this, 
                    "Bạn phải trả lời tất cả các câu hỏi trước khi nộp bài!", 
                    "Cảnh báo", 
                    JOptionPane.WARNING_MESSAGE);
                return; // Stop execution
            }
        }
    
        // If all questions are answered, proceed to check answers
        int score = 0;
        for (int i = 0; i < 10; i++) {
            if (selectedAnswers[i].equals(correctAnswers[i])) {
                score++;
            }
        }
    
        // Show result and return to home screen
        JOptionPane.showMessageDialog(this, 
            "Bạn đạt: " + score + "/10", 
            "Kết quả", 
            JOptionPane.INFORMATION_MESSAGE);
        
        this.dispose(); // Close exam screen
        SwingUtilities.invokeLater(() -> {
            new StudentHomeMenuScreen().setVisible(true);
        });
    }    
}