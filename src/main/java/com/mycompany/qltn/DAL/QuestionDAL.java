package com.mycompany.qltn.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.qltn.config.DatabaseConnection;
import com.mycompany.qltn.dto.AnswerDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.ResultDTO;

public class QuestionDAL {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/quanlytracnghiem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Khactuong1402";

    public List<QuestionDTO> getQuestionsByCategory(String category) {
        List<QuestionDTO> questions = new ArrayList<>();
    
        String normalizedCategory = category.trim().toLowerCase();

        // Map category names to topic IDs
        Map<String, Integer> categoryMap = new HashMap<>();
        categoryMap.put("lập trình web", 1);
        categoryMap.put("toán học", 2);
        categoryMap.put("du lịch", 3);

        Integer topicId = categoryMap.get(normalizedCategory);
        if (topicId == null) {
            System.out.println("Invalid category: " + category);
            return questions;
        }
    
        System.out.println("Fetching questions for topic_id: " + topicId);  // Debugging log
    
        String sql = "SELECT question_id, question_text, image_url, difficulty FROM questions WHERE topic_id = ? AND is_deleted = 0";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, topicId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String questionText = rs.getString("question_text");
                String imageUrl = rs.getString("image_url");
                String difficulty = rs.getString("difficulty");
    
                // Fetch answers separately
                List<AnswerDTO> answers = getAnswersByQuestionId(questionId);
                
                QuestionDTO question = new QuestionDTO(questionId, topicId, questionText, imageUrl, difficulty);
                question.setAnswers(answers);
                questions.add(question);
            }
    
            System.out.println("Questions fetched: " + questions.size());  // Debugging log
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return questions;
    }    

    private List<AnswerDTO> getAnswersByQuestionId(int questionId) {
        List<AnswerDTO> answers = new ArrayList<>();
        String sql = "SELECT answer_id, question_id, answer_text, is_correct FROM answers WHERE question_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AnswerDTO answer = new AnswerDTO(
                        rs.getInt("answer_id"),
                        rs.getInt("question_id"),
                        rs.getString("answer_text"),
                        rs.getBoolean("is_correct"));
                answers.add(answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answers;
    }

    private static String getCorrectAnswer(int questionId) {
        String correctAnswer = null;
        String sql = "SELECT answer_text FROM answers WHERE question_id = ? AND is_correct = TRUE LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                correctAnswer = rs.getString("answer_text");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return correctAnswer;
    }

    public static void saveResult(ResultDTO result) {
        String sql = "INSERT INTO results (user_id, exam_id, start_time, end_time, score) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, result.getUserId());
            stmt.setInt(2, result.getExamId());
            stmt.setTimestamp(3, Timestamp.valueOf(result.getStartTime()));
            stmt.setTimestamp(4, Timestamp.valueOf(result.getEndTime()));
            stmt.setInt(5, result.getScore());
    
            int rowsInserted = stmt.executeUpdate();  // Get affected row count
            if (rowsInserted > 0) {
                System.out.println("✅ Result saved successfully! " + result);
            } else {
                System.out.println("⚠️ No result was inserted.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error while saving result: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public static int getUserIdByEmail(String email) {
        int userId = -1;
        String sql = "SELECT user_id FROM users WHERE email = ? AND is_deleted = 0 LIMIT 1";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }

}