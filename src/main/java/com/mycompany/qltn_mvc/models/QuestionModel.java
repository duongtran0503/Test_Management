/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.models;

import com.mycompany.qltn_mvc.config.DatabaseConnection;
import com.mycompany.qltn_mvc.controllers.Response;
import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.dtos.TopicDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class QuestionModel {
    
     public Response.QuestoinResult getQuestoinResult(int page, int questionsPerPage) { 
        Connection conn = null;
        PreparedStatement questionStmt = null;
        PreparedStatement answerStmt = null;
        Response.QuestoinResult res = new Response.QuestoinResult();

        try {
            conn = DatabaseConnection.getConnection();

           // Lấy tổng số câu hỏi
            String countSql = "SELECT COUNT(*) FROM questions"; 
            PreparedStatement countStmt = conn.prepareStatement(countSql);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next()) {
                res.setTotalQuestions(countRs.getInt(1));
            }
            countStmt.close();

            // 2. Lấy danh sách câu hỏi theo trang
            String questionSql = "SELECT * FROM questions LIMIT ? OFFSET ?"; 
            questionStmt = conn.prepareStatement(questionSql);
            questionStmt.setInt(1, questionsPerPage);
            questionStmt.setInt(2, page * questionsPerPage);
            ResultSet questionRs = questionStmt.executeQuery();

            ArrayList<QuestionDTO> questionList = new ArrayList<>();
            while (questionRs.next()) {
                System.out.println("run");
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setQuestionId(questionRs.getInt("question_id")); 
                questionDTO.setQuestionText(questionRs.getString("question_text")); 
               questionDTO.setImageUrl(questionRs.getString("image_url"));
               questionDTO.setDifficulty(questionRs.getString("difficulty"));
               questionDTO.setTopicId(questionRs.getInt("topic_id"));
                questionList.add(questionDTO);
            }
            res.setQuestionList(questionList);

           
            ArrayList<OptionDTO> answerList = new ArrayList<>();
            for (QuestionDTO question : questionList) {
                String answerSql = "SELECT * FROM options WHERE question_id = ?"; 
                answerStmt = conn.prepareStatement(answerSql);
                answerStmt.setInt(1, question.getQuestionId());
                ResultSet answerRs = answerStmt.executeQuery();

                while (answerRs.next()) {
                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setOptionId(answerRs.getInt("option_id")); 
                    optionDTO.setOptionText(answerRs.getString("option_text")); 
                    optionDTO.setQuestionId(question.getQuestionId());
                    optionDTO.setIsCorrect(answerRs.getBoolean("is_correct"));
                    optionDTO.setImageUrl(answerRs.getString("image_url"));
                    
                    answerList.add(optionDTO);
                }
            }
            res.setAnswerList(answerList);

            res.setIsSuccess(true);
            res.setMessage("Lấy dữ liệu thành công!");
            res.setCurrentPage(page);
            res.setQuestionsPerPage(questionsPerPage);

        } catch (SQLException e) {
            res.setIsSuccess(false);
            res.setMessage("Lỗi lấy dữ liệu: " + e.getMessage());
        } finally {
            try {
                if (questionStmt != null) questionStmt.close();
                if (answerStmt != null) answerStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
            }
        }

        return res;
    }
     
     public Response.TopicResult getTopic() {
        Connection conn = null;
        PreparedStatement topicStmt = null;
     
        Response.TopicResult res = new Response.TopicResult();
         try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM topics";
             topicStmt = conn.prepareStatement(sql);
             ResultSet topicRs = topicStmt.executeQuery();
              ArrayList<TopicDTO> topicList = new ArrayList<>();
               while (topicRs.next()) {
                    TopicDTO topicDTO = new TopicDTO();
                    topicDTO.setTopicId(topicRs.getInt("topic_id"));
                    topicDTO.setTopicName(topicRs.getString("topic_name"));
                    topicList.add(topicDTO);
                     
                }
               res.setTopicList(topicList);
               res.setIsSuccess(true);
               res.setMessage("Lấy chủ đề thành công!");
               return  res;
             
         } catch (Exception e) {
             res.setIsSuccess(false);
             res.setMessage("Lỗi tải dữ liệu!");
         }
         return  res;
       
     }
     
     public Response.QuestoinResult searchQuestionByTitle(String title) {
       Connection conn = null;
        PreparedStatement questionStmt = null;
        PreparedStatement answerStmt = null;
        Response.QuestoinResult res = new Response.QuestoinResult();

        try {
            conn = DatabaseConnection.getConnection();
              String searchText = "%" + title + "%";
         
          String countSql = "SELECT COUNT(*) FROM questions WHERE LOWER(question_text) LIKE LOWER(?)";
            PreparedStatement countStmt = conn.prepareStatement(countSql);
             countStmt.setString(1, searchText);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next()) {
                res.setTotalQuestions(countRs.getInt(1));
                if(res.getTotalQuestions()==0) {
                  res.setIsSuccess(false);
                  res.setMessage("Không tìm thấy kết quả");
                  return  res;
                }
            }
            countStmt.close();

            // 2. Lấy danh sách câu hỏi theo trang
          
          String questionSql = "SELECT * FROM questions WHERE LOWER(question_text) LIKE LOWER(?) LIMIT 8";
            questionStmt = conn.prepareStatement(questionSql);
            questionStmt.setString(1, searchText);
           
            ResultSet questionRs = questionStmt.executeQuery();

            ArrayList<QuestionDTO> questionList = new ArrayList<>();
            while (questionRs.next()) {
                System.out.println("run");
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setQuestionId(questionRs.getInt("question_id")); 
                questionDTO.setQuestionText(questionRs.getString("question_text")); 
               questionDTO.setImageUrl(questionRs.getString("image_url"));
               questionDTO.setDifficulty(questionRs.getString("difficulty"));
               questionDTO.setTopicId(questionRs.getInt("topic_id"));
                questionList.add(questionDTO);
            }
            res.setQuestionList(questionList);

           
            ArrayList<OptionDTO> answerList = new ArrayList<>();
            for (QuestionDTO question : questionList) {
                String answerSql = "SELECT * FROM options WHERE question_id = ?"; 
                answerStmt = conn.prepareStatement(answerSql);
                answerStmt.setInt(1, question.getQuestionId());
                ResultSet answerRs = answerStmt.executeQuery();

                while (answerRs.next()) {
                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setOptionId(answerRs.getInt("option_id")); 
                    optionDTO.setOptionText(answerRs.getString("option_text")); 
                    optionDTO.setQuestionId(question.getQuestionId());
                    optionDTO.setIsCorrect(answerRs.getBoolean("is_correct"));
                    optionDTO.setImageUrl(answerRs.getString("image_url"));
                    
                    answerList.add(optionDTO);
                }
            }
            res.setAnswerList(answerList);

            res.setIsSuccess(true);
            res.setMessage("Lấy dữ liệu thành công!");
        

        } catch (SQLException e) {
            res.setIsSuccess(false);
            res.setMessage("Lỗi lấy dữ liệu: " + e.getMessage());
        } finally {
            try {
                if (questionStmt != null) questionStmt.close();
                if (answerStmt != null) answerStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
            }
        }

        return res;
      
     }
     
     public Response.QuestoinResult searchQuestionByTitleAndTopic(String title,int topic) {
        Connection conn = null;
        PreparedStatement questionStmt = null;
        PreparedStatement answerStmt = null;
        Response.QuestoinResult res = new Response.QuestoinResult();

        try {
            conn = DatabaseConnection.getConnection();
              String searchText = "%" + title + "%";
         
          String countSql = "SELECT COUNT(*) FROM questions WHERE LOWER(question_text) LIKE LOWER(?) AND topic_id = ?";
            PreparedStatement countStmt = conn.prepareStatement(countSql);
             countStmt.setString(1, searchText);
             countStmt.setInt(2, topic);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next()) {
                res.setTotalQuestions(countRs.getInt(1));
                if(res.getTotalQuestions()==0) {
                  res.setIsSuccess(false);
                  res.setMessage("Không tìm thấy kết quả");
                  return  res;
                }
            }
            countStmt.close();

            // 2. Lấy danh sách câu hỏi theo trang
          
          String questionSql = "SELECT * FROM questions WHERE LOWER(question_text) LIKE LOWER(?) AND topic_id = ? LIMIT 8";
            questionStmt = conn.prepareStatement(questionSql);
            questionStmt.setString(1, searchText);
            questionStmt.setInt(2, topic);
           
            ResultSet questionRs = questionStmt.executeQuery();

            ArrayList<QuestionDTO> questionList = new ArrayList<>();
            while (questionRs.next()) {
                System.out.println("run");
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setQuestionId(questionRs.getInt("question_id")); 
                questionDTO.setQuestionText(questionRs.getString("question_text")); 
               questionDTO.setImageUrl(questionRs.getString("image_url"));
               questionDTO.setDifficulty(questionRs.getString("difficulty"));
               questionDTO.setTopicId(questionRs.getInt("topic_id"));
                questionList.add(questionDTO);
            }
            res.setQuestionList(questionList);

           
            ArrayList<OptionDTO> answerList = new ArrayList<>();
            for (QuestionDTO question : questionList) {
                String answerSql = "SELECT * FROM options WHERE question_id = ?"; 
                answerStmt = conn.prepareStatement(answerSql);
                answerStmt.setInt(1, question.getQuestionId());
                ResultSet answerRs = answerStmt.executeQuery();

                while (answerRs.next()) {
                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setOptionId(answerRs.getInt("option_id")); 
                    optionDTO.setOptionText(answerRs.getString("option_text")); 
                    optionDTO.setQuestionId(question.getQuestionId());
                    optionDTO.setIsCorrect(answerRs.getBoolean("is_correct"));
                    optionDTO.setImageUrl(answerRs.getString("image_url"));
                    
                    answerList.add(optionDTO);
                }
            }
            res.setAnswerList(answerList);

            res.setIsSuccess(true);
            res.setMessage("Lấy dữ liệu thành công!");
        

        } catch (SQLException e) {
            res.setIsSuccess(false);
            res.setMessage("Lỗi lấy dữ liệu: " + e.getMessage());
        } finally {
            try {
                if (questionStmt != null) questionStmt.close();
                if (answerStmt != null) answerStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
            }
        }

        return res;
     }
}
