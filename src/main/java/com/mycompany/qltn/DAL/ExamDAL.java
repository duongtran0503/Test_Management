/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.mycompany.qltn.BLL.Response;
import com.mycompany.qltn.config.DatabaseConnection;
import com.mycompany.qltn.dto.ExamDTO;
import com.mycompany.qltn.dto.OptionDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.ResultDTO;
import com.mycompany.qltn.dto.ResultDetailDTO;
import com.mycompany.qltn.dto.TestDTO;

/**
 *
 * @author ACER
 */
public class ExamDAL {
    private  final QuestionDAL questionModel;
     public ExamDAL(){
      this.questionModel = new QuestionDAL();
     }
       public Response.ExamResult getExamResult(int status) {
                 Connection conn = null;
        Response.ExamResult res = new Response.ExamResult();
       res.setTestLists(new ArrayList<>());
       res.setExamList(new ArrayList<>());
        try {
            conn = DatabaseConnection.getConnection();
                String sql =" SELECT * FROM tests WHERE is_deleted =  ?";
                PreparedStatement testStmt = conn.prepareStatement(sql);
                testStmt.setBoolean(1,status==1);
                ResultSet testRs = testStmt.executeQuery();
                while (testRs.next()) {                
                    TestDTO testDTO = new TestDTO();
                    testDTO.setTopicId(testRs.getInt("topic_id"));
                    testDTO.setTestName(testRs.getString("test_name"));
                    testDTO.setTestId(testRs.getInt("test_id"));
                    testDTO.setTestTime(testRs.getInt("test_time"));
                    testDTO.setEasy(testRs.getInt("easy"));
                    testDTO.setMedium(testRs.getInt("medium"));
                    testDTO.setDifficult(testRs.getInt("difficult"));
                    testDTO.setTotalQuestion(testRs.getInt("number_of_questions"));
                   res.getTestLists().add(testDTO);
            }
           for(TestDTO test:res.getTestLists()) {
            String sqlgetExam = "SELECT * FROM exams WHERE test_id = ?";
            PreparedStatement examStmt = conn.prepareStatement(sqlgetExam);
            examStmt.setInt(1, test.getTestId());
            ResultSet rs= examStmt.executeQuery();
            while(rs.next()) {
               ExamDTO examDTO = new ExamDTO();
               examDTO.setTestId(test.getTestId());
               examDTO.setExamId(rs.getInt("exam_id"));
               examDTO.setExamCode(rs.getString("exam_code"));
               res.getExamList().add(examDTO);
            }
           }
         res.setMessage("Lấy dữ liệu thành công!");
         res.setIsSuccess(true);
         return  res;
        } catch (SQLException e) {
            res.setIsSuccess(false);
            res.setMessage("Lỗi lấy dữ liệu: " + e.getMessage());
        } 

        return res;   
       }
        public Response.ExamResult getTestResultById(int status,int id) {
                 Connection conn = null;
        Response.ExamResult res = new Response.ExamResult();
       res.setTestLists(new ArrayList<>());
       res.setExamList(new ArrayList<>());
        try {
            conn = DatabaseConnection.getConnection();
                String sql =" SELECT * FROM tests WHERE is_deleted =  ? AND test_id = ? ";
                PreparedStatement testStmt = conn.prepareStatement(sql);
                testStmt.setBoolean(1,status==1);
                testStmt.setInt(2, id);
                ResultSet testRs = testStmt.executeQuery();
                while (testRs.next()) {                
                    TestDTO testDTO = new TestDTO();
                    testDTO.setTopicId(testRs.getInt("topic_id"));
                    testDTO.setTestName(testRs.getString("test_name"));
                    testDTO.setTestId(testRs.getInt("test_id"));
                    testDTO.setTestTime(testRs.getInt("test_time"));
                      testDTO.setEasy(testRs.getInt("easy"));
                    testDTO.setMedium(testRs.getInt("medium"));
                    testDTO.setDifficult(testRs.getInt("difficult"));
                    testDTO.setTotalQuestion(testRs.getInt("number_of_questions"));
                   res.getTestLists().add(testDTO);
            }
           for(TestDTO test:res.getTestLists()) {
            String sqlgetExam = "SELECT * FROM exams WHERE test_id = ?";
            PreparedStatement examStmt = conn.prepareStatement(sqlgetExam);
            examStmt.setInt(1, test.getTestId());
            ResultSet rs= examStmt.executeQuery();
            while(rs.next()) {
               ExamDTO examDTO = new ExamDTO();
               examDTO.setTestId(test.getTestId());
               examDTO.setExamId(rs.getInt("exam_id"));
               examDTO.setExamCode(rs.getString("exam_code"));
               res.getExamList().add(examDTO);
            }
           }
         res.setMessage("Lấy dữ liệu thành công!");
         res.setIsSuccess(true);
         return  res;
        } catch (SQLException e) {
            res.setIsSuccess(false);
            res.setMessage("Lỗi lấy dữ liệu: " + e.getMessage());
        } 

        return res;   
       }
        public Response.ExamResult searchExam(int status,String title ,int topicId) {
                 Connection conn = null;
        Response.ExamResult res = new Response.ExamResult();
       res.setTestLists(new ArrayList<>());
       res.setExamList(new ArrayList<>());
        try {
            conn = DatabaseConnection.getConnection();
               String searchText = "%" + title + "%";
                String sql =" SELECT * FROM tests WHERE is_deleted =  ?  ";
                if(title.length()!=0 || !title.trim().isEmpty()) {
                 sql +=" AND test_name LIKE " +searchText;
                }
                 if(topicId !=-1) {
                   sql+=" AND topic_id = " +topicId;
                 }
                PreparedStatement testStmt = conn.prepareStatement(sql);
                testStmt.setBoolean(1,status==1);
                
               
                ResultSet testRs = testStmt.executeQuery();
                while (testRs.next()) {                
                    TestDTO testDTO = new TestDTO();
                    testDTO.setTopicId(testRs.getInt("topic_id"));
                    testDTO.setTestName(testRs.getString("test_name"));
                    testDTO.setTestId(testRs.getInt("test_id"));
                    testDTO.setTestTime(testRs.getInt("test_time"));
                      testDTO.setEasy(testRs.getInt("easy"));
                    testDTO.setMedium(testRs.getInt("medium"));
                    testDTO.setDifficult(testRs.getInt("difficult"));
                    testDTO.setTotalQuestion(testRs.getInt("number_of_questions"));
                   res.getTestLists().add(testDTO);
            }
           for(TestDTO test:res.getTestLists()) {
            String sqlgetExam = "SELECT * FROM exams WHERE test_id = ?";
            PreparedStatement examStmt = conn.prepareStatement(sqlgetExam);
            examStmt.setInt(1, test.getTestId());
            ResultSet rs= examStmt.executeQuery();
            while(rs.next()) {
               ExamDTO examDTO = new ExamDTO();
               examDTO.setTestId(test.getTestId());
               examDTO.setExamId(rs.getInt("exam_id"));
               examDTO.setExamCode(rs.getString("exam_code"));
               res.getExamList().add(examDTO);
            }
           }
         res.setMessage("Lấy dữ liệu thành công!");
         res.setIsSuccess(true);
         return  res;
        } catch (SQLException e) {
            res.setIsSuccess(false);
            res.setMessage("Lỗi lấy dữ liệu: " + e.getMessage());
        } 

        return res;   
       }
      
     public Response.ExamResult getTestByIdAndExamCode(int id, String examCode) {
        Response.ExamResult res = new Response.ExamResult();
        res.setTestLists(new ArrayList<>());
        res.setExamList(new ArrayList<>());
        Response.QuestoinResult questions = new Response.QuestoinResult();
        questions.setQuestionList(new ArrayList<>());
        questions.setAnswerList(new ArrayList<>());

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement testStmt = conn.prepareStatement("SELECT tests.topic_id, tests.test_name, tests.test_id, tests.test_time, tests.easy, tests.medium,tests.difficult,tests.number_of_questions"
                     + ", exams.exam_id, exams.exam_code, questions.question_text, questions.image_url,"
                     + " questions.updater, questions.question_id,questions.difficulty, questions.updated_at,"
                     + " questions.create_at, options.option_id, options.option_text, options.is_correct, options.image_url FROM tests"
                     + " JOIN exams ON tests.test_id = exams.test_id"
                     + " JOIN exam_questions ON exams.exam_id = exam_questions.exam_id"
                     + " JOIN questions ON exam_questions.question_id = questions.question_id"
                     + " JOIN options ON questions.question_id = options.question_id"
                     + " WHERE tests.test_id = ? AND exams.exam_code = ?")) {

            testStmt.setInt(1, id);
            testStmt.setString(2, examCode);
            try (ResultSet rs = testStmt.executeQuery()) {
                while (rs.next()) {
                    TestDTO testDTO = new TestDTO();
                    testDTO.setTopicId(rs.getInt("topic_id"));
                    testDTO.setTestName(rs.getString("test_name"));
                    testDTO.setTestId(rs.getInt("test_id"));
                    testDTO.setTestTime(rs.getInt("test_time"));
                      testDTO.setEasy(rs.getInt("easy"));
                    testDTO.setMedium(rs.getInt("medium"));
                    testDTO.setDifficult(rs.getInt("difficult"));
                    testDTO.setTotalQuestion(rs.getInt("number_of_questions"));
                    res.getTestLists().add(testDTO);

                    ExamDTO examDTO = new ExamDTO();
                    examDTO.setTestId(testDTO.getTestId());
                    examDTO.setExamId(rs.getInt("exam_id"));
                    examDTO.setExamCode(rs.getString("exam_code"));
                    res.getExamList().add(examDTO);

                    QuestionDTO questionDTO = new QuestionDTO();
                    questionDTO.setQuestionText(rs.getString("question_text"));
                    questionDTO.setImageUrl(rs.getString("image_url"));
                    questionDTO.setTopicId(rs.getInt("topic_id"));
                    questionDTO.setUpdater(rs.getString("updater"));
                    questionDTO.setQuestionId(rs.getInt("question_id"));
                    questionDTO.setDifficulty(rs.getString("difficulty"));
                    questionDTO.setUpdatedAt(convertTimestampToLocalDateTime(rs.getTimestamp("updated_at")));
                    questionDTO.setCreatedAt(convertTimestampToLocalDateTime(rs.getTimestamp("create_at")));
                    boolean check =true;
                    for(QuestionDTO question:questions.getQuestionList()) {
                        if(question.getQuestionId() ==questionDTO.getQuestionId())  {
                        check = false;
                        }
                     }
                    if(check) {
                     questions.getQuestionList().add(questionDTO);
                    }

                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setOptionId(rs.getInt("option_id"));
                    optionDTO.setOptionText(rs.getString("option_text"));
                    optionDTO.setQuestionId(questionDTO.getQuestionId());
                    optionDTO.setIsCorrect(rs.getBoolean("is_correct"));
                    optionDTO.setImageUrl(rs.getString("image_url"));
                    questions.getAnswerList().add(optionDTO);
                }
            }
            res.setListQestionOfExam(questions);
            res.setMessage("Lấy dữ liệu thành công!");
            res.setIsSuccess(true);
            return res;

        } catch (SQLException e) {
            res.setIsSuccess(false);
            res.setMessage("Lỗi lấy dữ liệu: " + e.getMessage());
        
        }

        return res;
    }

     public Response.ExamResult addQuestionToTheTest(int examQuestionId,int QuestionId) {
         Response.ExamResult res = new Response.ExamResult();
          try {
             Connection conn = DatabaseConnection.getConnection();
             String check = "SELECT * FROM exam_questions WHERE exam_id = ? AND question_id = ?";
             PreparedStatement checkStmt = conn.prepareStatement(check);
             
            checkStmt.setInt(1, examQuestionId);
            checkStmt.setInt(2, QuestionId);

            try (ResultSet checkResult = checkStmt.executeQuery()) {
                if (checkResult != null && checkResult.next()) {
                    res.setMessage("Câu hỏi đã có trong đề thi!");
                    res.setIsSuccess(false);
                    return res;
                }
            }
             String insert = "INSERT INTO exam_questions (exam_id, question_id) VALUES (?, ?)";
              PreparedStatement stmt = conn.prepareStatement(insert);
              stmt.setInt(1, examQuestionId);
              stmt.setInt(2, QuestionId);
              stmt.executeUpdate();
              res.setMessage("Thêm thành công!");
              res.setIsSuccess(true);
                return res;
         } catch (Exception e) {
             res.setMessage("Lỗi Thêm câu hỏi vào đề thi"+ e.getMessage());
            res.setIsSuccess(false);
         }
          return  res;
     }
     public  Response.ExamResult updateNametestAndTimeTest(String name,int time ,int id) {
         Response.ExamResult res = new Response.ExamResult();
         try {
             Connection conn = DatabaseConnection.getConnection();
             String sql = "UPDATE tests SET test_name = ? ,test_time = ?  WHERE test_id = ?";
             PreparedStatement stmt = conn.prepareStatement(sql);
             stmt.setString(1, name);
             stmt.setInt(2, time);
             stmt.setInt(3, id);
             stmt.executeUpdate();
             res.setMessage("Cập nhật thành công!");
             res.setIsSuccess(true);
         } catch (SQLException e) {
             res.setMessage("Lỗi cập nhật :"+ e.getMessage());
             res.setIsSuccess(false);
         }
         return  res;
     }
     
      public Response.BaseResponse deleteTest(int testId) {
    Response.BaseResponse res = new Response.BaseResponse();
    res.setIsSuccess(false);

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement updateTestsStmt = conn.prepareStatement(
                 "UPDATE Tests SET is_deleted = true WHERE test_id = ?")) {

        updateTestsStmt.setInt(1, testId);
        int rowsUpdated = updateTestsStmt.executeUpdate();

        if (rowsUpdated > 0) {
            res.setIsSuccess(true);
            res.setMessage("Xóa bài thi thành công!");
        } else {
            res.setMessage("Không tìm thấy bài thi với test_id: " + testId);
        }

    } catch (SQLException e) {
        res.setMessage("Lỗi xóa bài thi: " + e.getMessage());
        e.printStackTrace();
    }

    return res;
}
          public Response.BaseResponse recoverTest(int testId) {
    Response.BaseResponse res = new Response.BaseResponse();
    res.setIsSuccess(false);

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement updateTestsStmt = conn.prepareStatement(
                 "UPDATE Tests SET is_deleted = false WHERE test_id = ?")) {

        updateTestsStmt.setInt(1, testId);
        int rowsUpdated = updateTestsStmt.executeUpdate();

        if (rowsUpdated > 0) {
            res.setIsSuccess(true);
            res.setMessage("Xóa bài thi thành công!");
        } else {
            res.setMessage("Không tìm thấy bài thi với test_id: " + testId);
        }

    } catch (SQLException e) {
        res.setMessage("Lỗi xóa bài thi: " + e.getMessage());
        e.printStackTrace();
    }

    return res;
}
          
           public Response.ExamResult getTestResult() {
        Connection conn = null;
        PreparedStatement testStmt = null;
     
        Response.ExamResult res = new Response.ExamResult();
         try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM tests WHERE is_deleted = 0";
             testStmt = conn.prepareStatement(sql);
             ResultSet testRs = testStmt.executeQuery();
             ArrayList<TestDTO> listTest = new ArrayList<>();
             while (testRs.next()) {                 
                  TestDTO test = new TestDTO();
                   test.setTestId(testRs.getInt("test_id"));
                   test.setTestName(testRs.getString("test_name"));
                   test.setTestTime(testRs.getInt("test_time"));
                   listTest.add(test);
             }
             res.setTestLists(listTest);
             res.setMessage("Lấy danh sách bài thi thành công");
             res.setIsSuccess(true);
               return  res;
             
         } catch (Exception e) {
             res.setIsSuccess(false);
             res.setMessage("Lỗi tải dữ liệu!");
         }
         return  res;
       
     }
               public Response.ExamResult getExamById(int id) {
        Connection conn = null;
        PreparedStatement testStmt = null;
     
        Response.ExamResult res = new Response.ExamResult();
        res.setExamList(new ArrayList<>());
         try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM exams WHERE exam_id = ?";
             testStmt = conn.prepareStatement(sql);
             testStmt.setInt(1, id);
             ResultSet examRs = testStmt.executeQuery();
                      
             while (examRs.next()) {                 
                ExamDTO examDTO = new ExamDTO();
                 examDTO.setExamCode(examRs.getString("exam_code"));
                 examDTO.setExamId(examRs.getInt("exam_id"));
                 res.getExamList().add(examDTO);
             }
             
             res.setMessage("Lấy bài thi thành công!");
             res.setIsSuccess(true);
               return  res;
             
         } catch (Exception e) {
             res.setIsSuccess(false);
             res.setMessage("Lỗi tải dữ liệu!");
         }
         return  res;
       
     }
      public Response.BaseResponse createNewTest(TestDTO test, String topic_name, ArrayList<String> examCodeList, int easyQ, int mediumQ, int diffQ) {
        Response.BaseResponse res = new Response.BaseResponse();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkTopicId = "SELECT * FROM topics WHERE topic_id = ?";
            try (PreparedStatement checkTp = conn.prepareStatement(checkTopicId)) {
                checkTp.setInt(1, test.getTopicId());
                try (ResultSet checkTpId = checkTp.executeQuery()) {
                    if (!checkTpId.next()) {
                        String addTopic = "INSERT INTO topics(topic_id,topic_name) VALUES(?,?)";
                        try (PreparedStatement addTopicStmt = conn.prepareStatement(addTopic)) {
                            addTopicStmt.setInt(1, test.getTopicId());
                            addTopicStmt.setString(2, topic_name);
                            addTopicStmt.executeUpdate();
                        }
                    }
                }
            }

            String insert = "INSERT INTO tests(test_name,topic_id,test_time,num_limit,easy,medium,difficult,number_of_questions) VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement testStmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
                testStmt.setString(1, test.getTestName());
                testStmt.setInt(2, test.getTopicId());
                testStmt.setInt(3, test.getTestTime());
                testStmt.setInt(4, test.getNumLimit());
                testStmt.setInt(5, easyQ);
               testStmt.setInt(6, mediumQ);
               testStmt.setInt(7, diffQ);
               testStmt.setInt(8, diffQ+mediumQ+easyQ);
                testStmt.executeUpdate();

                try (ResultSet generatedKeys = testStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        test.setTestId(generatedKeys.getInt(1));
                        System.out.println("new id test:" + test.getTestId());
                    } else {
                        throw new SQLException("Lỗi lấy id bài thi!");
                    }
                }
            }

            for (String examCode : examCodeList) {
                String insEx = "INSERT INTO exams(test_id,exam_code) VALUES(?,?)";
                try (PreparedStatement stmt = conn.prepareStatement(insEx, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, test.getTestId());
                    stmt.setString(2, examCode);
                    stmt.executeUpdate();
                    try (ResultSet generatedKey = stmt.getGeneratedKeys()) {
                        if (generatedKey.next()) {
                            int ExamId = generatedKey.getInt(1);

                            if (easyQ > 0) {
                                Response.QuestoinResult resulteasyQ = questionModel.filterQuestionBydifficulty(QuestionDTO.EASY,test.getTopicId());
                                for (int i = 1; i <= easyQ; i++) {
                                    String insEasyQ = "INSERT INTO exam_questions(exam_id,question_id) VALUES(?,?)";
                                    try (PreparedStatement insEasyQStmt = conn.prepareStatement(insEasyQ)) {
                                        insEasyQStmt.setInt(1, ExamId);
                                        int indexQ = getRadomNumber(resulteasyQ.getQuestionList().size() - 1);
                                        insEasyQStmt.setInt(2, resulteasyQ.getQuestionList().get(indexQ).getQuestionId());
                                        resulteasyQ.getQuestionList().remove(indexQ);
                                        insEasyQStmt.executeUpdate();
                                    }
                                }
                            }

                            if (mediumQ > 0) {
                                Response.QuestoinResult resultMediumQ = questionModel.filterQuestionBydifficulty(QuestionDTO.MEDIUM,test.getTopicId());
                                for (int i = 1; i <= mediumQ; i++) {
                                    String insMediumQ = "INSERT INTO exam_questions(exam_id,question_id) VALUES(?,?)";
                                    try (PreparedStatement insMediumQStmt = conn.prepareStatement(insMediumQ)) {
                                        insMediumQStmt.setInt(1, ExamId);
                                        int indexQ = getRadomNumber(resultMediumQ.getQuestionList().size() - 1);
                                        insMediumQStmt.setInt(2, resultMediumQ.getQuestionList().get(indexQ).getQuestionId());
                                        resultMediumQ.getQuestionList().remove(indexQ);
                                        insMediumQStmt.executeUpdate();
                                    }
                                }
                            }

                            if (diffQ > 0) {
                                Response.QuestoinResult resultdiffQ = questionModel.filterQuestionBydifficulty(QuestionDTO.DIFFICULT,test.getTopicId());
                                for (int i = 1; i <= diffQ; i++) {
                                    String insdiffQ = "INSERT INTO exam_questions(exam_id,question_id) VALUES(?,?)";
                                    try (PreparedStatement insdiffQStmt = conn.prepareStatement(insdiffQ)) {
                                        insdiffQStmt.setInt(1, ExamId);
                                        int indexQ = getRadomNumber(resultdiffQ.getQuestionList().size() - 1);
                                        insdiffQStmt.setInt(2, resultdiffQ.getQuestionList().get(indexQ).getQuestionId());
                                        resultdiffQ.getQuestionList().remove(indexQ);
                                        insdiffQStmt.executeUpdate();
                                    }
                                }
                            }
                        } else {
                            throw new SQLException("Lỗi lấy id đề thi!");
                        }
                    }
                }
            }

            res.setIsSuccess(true);
            res.setMessage("Thêm thành công!");

        } catch (SQLException e) {
            res.setMessage("Lỗi thêm bài thi! " + e.getMessage());
            res.setIsSuccess(false);
        }
        return res;
    }
     public Response.BaseResponse addNewExamCode(TestDTO test, ArrayList<String> examCodeList, int easyQ, int mediumQ, int diffQ) {
           Response.BaseResponse res = new Response.BaseResponse();
         try {
             Connection conn = DatabaseConnection.getConnection();
             for (String examCode : examCodeList) {
                String insEx = "INSERT INTO exams(test_id,exam_code) VALUES(?,?)";
                try (PreparedStatement stmt = conn.prepareStatement(insEx, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, test.getTestId());
                    stmt.setString(2, examCode);
                    stmt.executeUpdate();
                    try (ResultSet generatedKey = stmt.getGeneratedKeys()) {
                        if (generatedKey.next()) {
                            int ExamId = generatedKey.getInt(1);

                            if (easyQ > 0) {
                                Response.QuestoinResult resulteasyQ = questionModel.filterQuestionBydifficulty(QuestionDTO.EASY,test.getTopicId());
                                for (int i = 1; i <= easyQ; i++) {
                                    String insEasyQ = "INSERT INTO exam_questions(exam_id,question_id) VALUES(?,?)";
                                    try (PreparedStatement insEasyQStmt = conn.prepareStatement(insEasyQ)) {
                                        insEasyQStmt.setInt(1, ExamId);
                                        int indexQ = getRadomNumber(resulteasyQ.getQuestionList().size() - 1);
                                        insEasyQStmt.setInt(2, resulteasyQ.getQuestionList().get(indexQ).getQuestionId());
                                        resulteasyQ.getQuestionList().remove(indexQ);
                                        insEasyQStmt.executeUpdate();
                                    }
                                }
                            }

                            if (mediumQ > 0) {
                                Response.QuestoinResult resultMediumQ = questionModel.filterQuestionBydifficulty(QuestionDTO.MEDIUM,test.getTopicId());
                                for (int i = 1; i <= mediumQ; i++) {
                                    String insMediumQ = "INSERT INTO exam_questions(exam_id,question_id) VALUES(?,?)";
                                    try (PreparedStatement insMediumQStmt = conn.prepareStatement(insMediumQ)) {
                                        insMediumQStmt.setInt(1, ExamId);
                                        int indexQ = getRadomNumber(resultMediumQ.getQuestionList().size() - 1);
                                        insMediumQStmt.setInt(2, resultMediumQ.getQuestionList().get(indexQ).getQuestionId());
                                        resultMediumQ.getQuestionList().remove(indexQ);
                                        insMediumQStmt.executeUpdate();
                                    }
                                }
                            }

                            if (diffQ > 0) {
                                Response.QuestoinResult resultdiffQ = questionModel.filterQuestionBydifficulty(QuestionDTO.DIFFICULT,test.getTopicId());
                                for (int i = 1; i <= diffQ; i++) {
                                    String insdiffQ = "INSERT INTO exam_questions(exam_id,question_id) VALUES(?,?)";
                                    try (PreparedStatement insdiffQStmt = conn.prepareStatement(insdiffQ)) {
                                        insdiffQStmt.setInt(1, ExamId);
                                        int indexQ = getRadomNumber(resultdiffQ.getQuestionList().size() - 1);
                                        insdiffQStmt.setInt(2, resultdiffQ.getQuestionList().get(indexQ).getQuestionId());
                                        resultdiffQ.getQuestionList().remove(indexQ);
                                        insdiffQStmt.executeUpdate();
                                    }
                                }
                            }
                        } else {
                            throw new SQLException("Lỗi lấy id đề thi!");
                        }
                    }
                }
            }
             res.setIsSuccess(true);
             res.setMessage("Thêm mã đề thành công!");
         } catch (SQLException e) {
             res.setIsSuccess(false);
             res.setMessage("Lỗi thêm mã đề:"+e.getMessage());
         }
         return res;
     }
     
          public Response.TestResult getTestsResult(int examId) {
        Response.TestResult res = new Response.TestResult();
        res.setTestResultList(new ArrayList<>());
        res.setTestResultDetailList(new HashMap<>()); 

        try (Connection conn = DatabaseConnection.getConnection()) {
            StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM results ");
            if (examId != -1) {
                sqlBuilder.append(" WHERE exam_id = ?");
            }
             sqlBuilder.append("  ORDER BY correct DESC");
            try (PreparedStatement stmt = conn.prepareStatement(sqlBuilder.toString())) {
                if (examId != -1) {
                    stmt.setInt(1, examId);
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        ResultDTO resultDTO = new ResultDTO();
                        resultDTO.setResultId(rs.getInt("result_id"));
                        resultDTO.setUserId(rs.getInt("user_id"));
                        resultDTO.setEndTime(convertTimestampToLocalDateTime(rs.getTimestamp("end_time")));
                        resultDTO.setStartTime(convertTimestampToLocalDateTime(rs.getTimestamp("start_time")));
                        resultDTO.setScore(rs.getInt("score"));
                        resultDTO.setCorrect(rs.getDouble("correct"));
                        resultDTO.setExamId(rs.getInt("exam_id"));
                        res.getTestResultList().add(resultDTO);

                  
                    }
                }
            }

            for (ResultDTO resultDTO : res.getTestResultList()) {
                try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM result_details WHERE result_id = ?")) {
                    statement.setInt(1, resultDTO.getResultId());
                    try (ResultSet rsDetailRs = statement.executeQuery()) {
                        while (rsDetailRs.next()) {
                            ResultDetailDTO resultDetailDTO = new ResultDetailDTO();
                            resultDetailDTO.setResultDetailId(rsDetailRs.getInt("result_detail_id"));
                            resultDetailDTO.setOptionId(rsDetailRs.getInt("option_id"));
                            resultDetailDTO.setResultId(rsDetailRs.getInt("result_id"));
                            resultDetailDTO.setQuestionId(rsDetailRs.getInt("question_id"));
                            res.getTestResultDetailList().put(resultDetailDTO.getResultId(), resultDetailDTO);
                        }
                    }
                }
            }

            res.setIsSuccess(true);
            res.setMessage("Lấy dữ liệu thành công!");

        } catch (SQLException e) {
            res.setIsSuccess(false);
            res.setMessage("Lỗi lấy dữ liệu: " + e.getMessage());
        }

        return res;
    }
    public Response.ContestAntResult getContestAntResult(int testId){
        Response.ContestAntResult res = new Response.ContestAntResult();
        try {
            String sql = "SELECT t.test_id, COUNT(DISTINCT r.user_id) AS total_users, SUM(CASE WHEN r.correct > 50 THEN 1 ELSE 0 END) AS users_above_50_percent"
                    + " FROM Tests t"
                    + " JOIN Exams e ON t.test_id = e.test_id JOIN Results r ON e.exam_id = r.exam_id WHERE t.test_id = ? GROUP BY t.test_id"  ;
           Connection conn = DatabaseConnection.getConnection();
           PreparedStatement stmt = conn.prepareStatement(sql);
           stmt.setInt(1, testId);
           ResultSet rs = stmt.executeQuery();
           if(rs.next()) {
              res.setTotal(rs.getInt("total_users"));
              res.setCompleted(rs.getInt("users_above_50_percent"));
           }
           res.setIsSuccess(true);
           
        } catch (SQLException e) {
            res.setIsSuccess(false);
        }
        return  res;
    }
    
    private  int getRadomNumber(int upperBound) {
        if(upperBound <=0 ) return  0 ;
        Random random = new Random();
        return  random.nextInt(upperBound);
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
