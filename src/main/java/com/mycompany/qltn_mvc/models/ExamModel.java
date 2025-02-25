/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.models;

import com.mycompany.qltn_mvc.config.DatabaseConnection;
import com.mycompany.qltn_mvc.controllers.Response;
import com.mycompany.qltn_mvc.dtos.ExamDTO;
import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.dtos.TestDTO;
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
public class ExamModel {
       public Response.ExamResult getExamResult() {
                 Connection conn = null;
        Response.ExamResult res = new Response.ExamResult();
       res.setTestLists(new ArrayList<>());
       res.setExamList(new ArrayList<>());
        try {
            conn = DatabaseConnection.getConnection();
                String sql =" SELECT * FROM tests";
                PreparedStatement testStmt = conn.prepareStatement(sql);
                ResultSet testRs = testStmt.executeQuery();
                while (testRs.next()) {                
                    TestDTO testDTO = new TestDTO();
                    testDTO.setTopicId(testRs.getInt("topic_id"));
                    testDTO.setTestName(testRs.getString("test_name"));
                    testDTO.setTestId(testRs.getInt("test_id"));
                    testDTO.setTestTime(testRs.getInt("test_time"));
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
}
