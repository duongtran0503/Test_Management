/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.models;

import com.mycompany.qltn_mvc.config.DatabaseConnection;
import com.mycompany.qltn_mvc.controllers.Response;
import com.mycompany.qltn_mvc.dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class AuthModel {

     public Response.loginResult login(String email) {
         Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
         Response.loginResult res = new Response.loginResult();
         try {
             conn = DatabaseConnection.getConnection();
             String sql = "SELECT * FROM users WHERE email = ?"; // Thay thế bằng tên bảng và cột của bạn
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
 
              rs = pstmt.executeQuery();
              if(rs.next()) {
                 UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(rs.getString("email"));
                    userDTO.setPassword(rs.getString("password"));
                    userDTO.setUserId(rs.getInt("user_id"));
                    userDTO.setRole(rs.getString("role"));
                    userDTO.setUsername(rs.getString("fullname"));
                     res.setUser(userDTO);
                     res.setIsSuccess(true);
                     return  res;
                     
                          } else {
                    res.setIsSuccess(false);
                    return  res;
              }
         } catch (SQLException e) {
          
            return null;
         }
          
     } 
     
    public Response.RegisterResult register(UserDTO userDTO) {
        Response.RegisterResult res = new Response.RegisterResult();
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null; 

        try {
            conn = DatabaseConnection.getConnection();

          
            String checkSql = "SELECT email FROM users WHERE email = ?";
            checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, userDTO.getEmail());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                res.setIsSuccess(false);
                res.setMessage("Email đã được đăng ký!");
                return res;
            } else {
 
                String insertSql = "INSERT INTO users (fullname, email, password, role) VALUES (?, ?, ?, ?)"; 
                insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, userDTO.getUsername());
                insertStmt.setString(2, userDTO.getEmail());
                insertStmt.setString(3, userDTO.getPassword()); 
                insertStmt.setString(4, userDTO.getRole()); 

                int affectedRows = insertStmt.executeUpdate();

                if (affectedRows > 0) {
                    res.setIsSuccess(true);
                    res.setMessage("Đăng ký thành công!");
                    return res;
                } else {
                    res.setIsSuccess(false);
                    res.setMessage("Đăng ký thất bại. Vui lòng thử lại.");
                    return res;
                }
            }

        } catch (SQLException e) {
         
            res.setIsSuccess(false);
            res.setMessage("Lỗi đăng ký: "); 
            return res;
        } 
    } 
}
