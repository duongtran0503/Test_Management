/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.DAL;

import com.mycompany.qltn.App;
import com.mycompany.qltn.config.DatabaseConnection;
import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.dtos.TopicDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class QuestionDAL {
    
     public Response.QuestoinResult getQuestoinResult(int page, int questionsPerPage) { 
        Connection conn = null;
        PreparedStatement questionStmt = null;
        PreparedStatement answerStmt = null;
        Response.QuestoinResult res = new Response.QuestoinResult();

        try {
            conn = DatabaseConnection.getConnection();

           // Lấy tổng số câu hỏi
            String countSql = "SELECT COUNT(*) FROM questions WHERE is_deleted = 0"; 
            PreparedStatement countStmt = conn.prepareStatement(countSql);
            ResultSet countRs = countStmt.executeQuery();
            if (countRs.next()) {
                res.setTotalQuestions(countRs.getInt(1));
            }
            countStmt.close();

            // 2. Lấy danh sách câu hỏi theo trang
            String questionSql = "SELECT * FROM questions WHERE is_deleted = 0 LIMIT ? OFFSET ?"; 
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
               questionDTO.setUpdater(questionRs.getString("updater"));
                System.out.println("errr");
            
             questionDTO.setUpdated_at(convertTimestampToLocalDateTime(questionRs.getTimestamp("updated_at")));
                    questionDTO.setCreate_ar(convertTimestampToLocalDateTime(questionRs.getTimestamp("create_at")));
                questionList.add(questionDTO);
            }
            res.setQuestionList(questionList);

           
            ArrayList<OptionDTO> answerList = new ArrayList<>();
            for (QuestionDTO question : questionList) {
                String answerSql = "SELECT * FROM options WHERE question_id = ? "; 
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
         
          String countSql = "SELECT COUNT(*) FROM questions WHERE LOWER(question_text) LIKE LOWER(?) AND  is_deleted = 0";
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
          
          String questionSql = "SELECT * FROM questions WHERE LOWER(question_text) LIKE LOWER(?) AND is_deleted = 0 LIMIT 8";
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
               questionDTO.setUpdater(questionRs.getString("updater"));
              questionDTO.setUpdated_at(convertTimestampToLocalDateTime(questionRs.getTimestamp("updated_at")));
                    questionDTO.setCreate_ar(convertTimestampToLocalDateTime(questionRs.getTimestamp("create_at")));
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
      public Response.QuestoinResult getQuestionById(int id) {
       Connection conn = null;
        PreparedStatement questionStmt = null;
        PreparedStatement answerStmt = null;
        Response.QuestoinResult res = new Response.QuestoinResult();

        try {
            conn = DatabaseConnection.getConnection();
          String questionSql = "SELECT * FROM questions WHERE question_id = ? ";
            questionStmt = conn.prepareStatement(questionSql);
            questionStmt.setInt(1, id);
           
            ResultSet questionRs = questionStmt.executeQuery();

            ArrayList<QuestionDTO> questionList = new ArrayList<>();
            if (questionRs.next()) {
                System.out.println("run");
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setQuestionId(questionRs.getInt("question_id")); 
                questionDTO.setQuestionText(questionRs.getString("question_text")); 
               questionDTO.setImageUrl(questionRs.getString("image_url"));
               questionDTO.setDifficulty(questionRs.getString("difficulty"));
               questionDTO.setTopicId(questionRs.getInt("topic_id"));
               questionDTO.setUpdater(questionRs.getString("updater"));
              questionDTO.setUpdated_at(convertTimestampToLocalDateTime(questionRs.getTimestamp("updated_at")));
                    questionDTO.setCreate_ar(convertTimestampToLocalDateTime(questionRs.getTimestamp("create_at")));
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
         
          String countSql = "SELECT COUNT(*) FROM questions WHERE LOWER(question_text) LIKE LOWER(?) AND topic_id = ? AND is_deleted = 0 ";
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
          
          String questionSql = "SELECT * FROM questions WHERE LOWER(question_text) LIKE LOWER(?) AND topic_id = ? AND is_deleted = 0 LIMIT 8";
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
               questionDTO.setUpdater(questionRs.getString("updater"));
                questionDTO.setUpdated_at(convertTimestampToLocalDateTime(questionRs.getTimestamp("updated_at")));
                    questionDTO.setCreate_ar(convertTimestampToLocalDateTime(questionRs.getTimestamp("create_at")));
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
   public Response.QuestoinResult AddQuestoin(QuestionDTO questionDTO, ArrayList<OptionDTO> listOption) {
        Response.QuestoinResult res = new Response.QuestoinResult();
        res.setIsSuccess(false);
        res.setQuestionList(new ArrayList<>());
        Connection conn = null;
        PreparedStatement questionStmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                res.setMessage("Lỗi kết nối database!");
                return res;
            }
            conn.setAutoCommit(false); // Start transaction

            String sqlAddQuestion = "INSERT INTO questions (topic_id, question_text, image_url, difficulty) VALUES (?, ?, ?, ?)";
            questionStmt = conn.prepareStatement(sqlAddQuestion, PreparedStatement.RETURN_GENERATED_KEYS);
            questionStmt.setInt(1, questionDTO.getTopicId());
            questionStmt.setString(2, questionDTO.getQuestionText());
            questionStmt.setString(3, questionDTO.getImageUrl());
            questionStmt.setString(4, questionDTO.getDifficulty());
            questionStmt.executeUpdate();

            ResultSet generatedKeys = questionStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                questionDTO.setQuestionId(generatedKeys.getInt(1));
                System.out.println("new id question:"+questionDTO.getQuestionId());
            } else {
                throw new SQLException("Lỗi lấy id câu hỏi!"); // Throw exception
            }

            if (listOption != null && !listOption.isEmpty()) {
                for (OptionDTO optionDTO : listOption) {
                    String sqlAddOption = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
                    PreparedStatement optionStmt = conn.prepareStatement(sqlAddOption);
                    optionStmt.setInt(1, questionDTO.getQuestionId());
                    optionStmt.setString(2, optionDTO.getOptionText());
                    optionStmt.setBoolean(3, optionDTO.isIsCorrect());
                    optionStmt.executeUpdate();
                    optionStmt.close();
                }
            }

            conn.commit();
            res.getQuestionList().add(questionDTO);
            res.setIsSuccess(true);
            res.setMessage("Thêm câu hỏi thành công.");
            return res;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            res.setMessage("Lỗi thêm câu hỏi vào database: " + e.getMessage());
            return res;
        } finally {
            if (questionStmt != null) {
                try {
                    questionStmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     public Response.QuestoinResult updateQuestion(QuestionDTO questionDTO, ArrayList<OptionDTO> listOption) {
        Response.QuestoinResult res = new Response.QuestoinResult();
        res.setIsSuccess(false);
        Connection conn = null;
        PreparedStatement questionStmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                res.setMessage("Lỗi kết nối database!");
                return res;
            }
            conn.setAutoCommit(false); // Start transaction

            // Update Question
            String sqlUpdateQuestion = "UPDATE questions SET topic_id = ?, question_text = ?, image_url = ?, difficulty = ? ,updater = ? WHERE question_id = ?";
            questionStmt = conn.prepareStatement(sqlUpdateQuestion);
            questionStmt.setInt(1, questionDTO.getTopicId());
            questionStmt.setString(2, questionDTO.getQuestionText());
            questionStmt.setString(3, questionDTO.getImageUrl());
            questionStmt.setString(4, questionDTO.getDifficulty());
            questionStmt.setString(5, App.user.getUsername());
            questionStmt.setInt(6, questionDTO.getQuestionId()); // Where clause
            int rowsAffected = questionStmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Không tìm thấy câu hỏi với id: " + questionDTO.getQuestionId());
            }

            // Delete existing options
            String sqlDeleteOptions = "DELETE FROM options WHERE question_id = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(sqlDeleteOptions);
            deleteStmt.setInt(1, questionDTO.getQuestionId());
            deleteStmt.executeUpdate();
            deleteStmt.close();

            // Insert new options
            if (listOption != null && !listOption.isEmpty()) {
                for (OptionDTO optionDTO : listOption) {
                    String sqlAddOption = "INSERT INTO options (question_id, option_text, is_correct) VALUES (?, ?, ?)";
                    PreparedStatement optionStmt = conn.prepareStatement(sqlAddOption);
                    optionStmt.setInt(1, questionDTO.getQuestionId());
                    optionStmt.setString(2, optionDTO.getOptionText());
                    optionStmt.setBoolean(3, optionDTO.isIsCorrect());
                    optionStmt.executeUpdate();
                    optionStmt.close();
                }
            }

            conn.commit();
            res.setIsSuccess(true);
            res.setMessage("Cập nhật câu hỏi thành công.");
            return res;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            res.setMessage("Lỗi cập nhật câu hỏi vào database: " + e.getMessage());
            return res;
        } finally {
            if (questionStmt != null) {
                try {
                    questionStmt.close();
                } catch (SQLException e) {
                   e.printStackTrace(); 
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
  
     public Response.QuestoinResult delelteQuestoin(int id) {
         Response.QuestoinResult res = new Response.QuestoinResult();
        res.setIsSuccess(false);
        Connection conn = null;
        PreparedStatement questionStmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                res.setMessage("Lỗi kết nối database!");
                return res;
            }
            conn.setAutoCommit(false); // Start transaction

            // Update Question
            String sqlUpdateQuestion = "UPDATE questions SET is_deleted = ? ,updater = ? WHERE question_id = ?";
            questionStmt = conn.prepareStatement(sqlUpdateQuestion);
           questionStmt.setBoolean(1, true);
            questionStmt.setString(2, App.user.getUsername());
            questionStmt.setInt(3, id);
            int rowsAffected = questionStmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Không tìm thấy câu hỏi với id: " + id);
            }

          

            conn.commit();
            res.setIsSuccess(true);
            res.setMessage("Xóa câu hỏi thành công.");
            return res;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            res.setMessage("Lỗi xóa câu hỏi vào database: " + e.getMessage());
            return res;
        } finally {
            if (questionStmt != null) {
                try {
                    questionStmt.close();
                } catch (SQLException e) {
                   e.printStackTrace(); 
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
     
     }
     
     public Response.QuestoinResult getQuestionDeleted() {
        Connection conn = null;
        PreparedStatement questionStmt = null;
        PreparedStatement answerStmt = null;
        Response.QuestoinResult res = new Response.QuestoinResult();

        try {
            conn = DatabaseConnection.getConnection();
      
            String questionSql = "SELECT * FROM questions WHERE is_deleted = 1"; 
            questionStmt = conn.prepareStatement(questionSql);
          
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
               questionDTO.setUpdater(questionRs.getString("updater"));
                questionDTO.setUpdated_at(convertTimestampToLocalDateTime(questionRs.getTimestamp("updated_at")));
                    questionDTO.setCreate_ar(convertTimestampToLocalDateTime(questionRs.getTimestamp("create_at")));
            
          
                questionList.add(questionDTO);
            }
            res.setQuestionList(questionList);

           
            ArrayList<OptionDTO> answerList = new ArrayList<>();
            for (QuestionDTO question : questionList) {
                String answerSql = "SELECT * FROM options WHERE question_id = ? "; 
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
    public Response.QuestoinResult recoverQuestion(int id){
        Response.QuestoinResult res = new Response.QuestoinResult();
        res.setIsSuccess(false);
        Connection conn = null;
        PreparedStatement questionStmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            if (conn == null) {
                res.setMessage("Lỗi kết nối database!");
                return res;
            }
            conn.setAutoCommit(false); // Start transaction

            // Update Question
            String sqlUpdateQuestion = "UPDATE questions SET is_deleted = ? ,updater = ? WHERE question_id = ?";
            questionStmt = conn.prepareStatement(sqlUpdateQuestion);
            questionStmt.setBoolean(1, false);
            questionStmt.setString(2, App.user.getUsername());
            questionStmt.setInt(3, id);
            questionStmt.executeUpdate();
            conn.commit();
            res.setIsSuccess(true);
            res.setMessage("Khôi phục câu hỏi thành công.");
            return res;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            res.setMessage("Lỗi Khôi phục câu hỏi: " + e.getMessage());
            return res;
        } finally {
            if (questionStmt != null) {
                try {
                    questionStmt.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    
    public  Response.QuestoinResult filterQuestionBydifficulty(String difficulty,int topicId){
        Connection conn = null;
        PreparedStatement questionStmt = null;
        PreparedStatement answerStmt = null;
        Response.QuestoinResult res = new Response.QuestoinResult();

        try {
            conn = DatabaseConnection.getConnection();
           
            String questionSql = "SELECT * FROM questions WHERE is_deleted = 0 AND difficulty = ? ";
             if(topicId!=-1) {
               questionSql +="AND topic_id = ?";
             }
            questionStmt = conn.prepareStatement(questionSql);
            questionStmt.setString(1, difficulty);
            if(topicId!=-1) {
             questionStmt.setInt(2, topicId);
            }
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
               questionDTO.setUpdater(questionRs.getString("updater"));
                questionDTO.setUpdated_at(convertTimestampToLocalDateTime(questionRs.getTimestamp("updated_at")));
                    questionDTO.setCreate_ar(convertTimestampToLocalDateTime(questionRs.getTimestamp("create_at")));
            
          
                questionList.add(questionDTO);
            }
            res.setQuestionList(questionList);
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
     private LocalDateTime convertTimestampToLocalDateTime(Timestamp timestamp) {
        if (timestamp != null) {
            LocalDateTime localDateTime = timestamp.toLocalDateTime();
            if (localDateTime.equals(LocalDateTime.MIN)) {
                return null;
            } else {
                return localDateTime;
            }
        }
        return null;
    }
}
