/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.models;

import com.mycompany.qltn_mvc.App;
import com.mycompany.qltn_mvc.config.DatabaseConnection;
import com.mycompany.qltn_mvc.controllers.Response;
import com.mycompany.qltn_mvc.controllers.Response.BaseResponse;
import com.mycompany.qltn_mvc.dtos.UserDTO;
import com.sun.tools.javac.Main;
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
public class UserModel {
      public Response.UserResult getUser(int status) {
        Response.UserResult result = new Response.UserResult();
        ArrayList<UserDTO> userList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             
                PreparedStatement pstmt = conn.prepareStatement("SELECT user_id, fullname, password, email, role,updated_at,updater FROM users WHERE is_deleted = "+status);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                UserDTO user = new UserDTO();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("fullname"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setUpdater(rs.getString("updater"));
                user.setUpdated_at(convertTimestampToLocalDateTime(rs.getTimestamp("updated_at")));
                userList.add(user);
            }

            result.setUserList(userList);
            result.setIsSuccess(true);
            result.setMessage("Lấy danh sách người dùng thành công.");

        } catch (SQLException e) {
            result.setIsSuccess(false);
            result.setMessage("Lỗi lấy danh sách người dùng: " + e.getMessage());
        }

        return result;
    }
      
       public BaseResponse deleteUser(int userId) {
        BaseResponse response = new BaseResponse();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET is_deleted = ?, updater = ? WHERE user_id = ?")) {

            pstmt.setBoolean(1, true);
            pstmt.setString(2,App.user.getUsername());
            pstmt.setInt(3, userId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIsSuccess(true);
                response.setMessage("Người dùng đã được xóa thành công.");
            } else {
                response.setIsSuccess(false);
                response.setMessage("Không tìm thấy người dùng với ID: " + userId);
            }

        } catch (SQLException e) {
            response.setIsSuccess(false);
            response.setMessage("Lỗi xóa người dùng: " + e.getMessage());
        }

        return response;
    }
       
        public BaseResponse updateUser(UserDTO user) {
        BaseResponse response = new BaseResponse();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE users SET fullname = ?, password = ?, email = ?, role = ?, updater = ? WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword()); 
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getRole());
           
                pstmt.setString(5, App.user.getUsername());
                pstmt.setInt(6, user.getUserId());

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    response.setIsSuccess(true);
                    response.setMessage("Thông tin người dùng đã được cập nhật thành công.");
                } else {
                    response.setIsSuccess(false);
                    response.setMessage("Không tìm thấy người dùng với ID: " + user.getUserId());
                }
            }

        } catch (SQLException e) {
            response.setIsSuccess(false);
            response.setMessage("Lỗi cập nhật thông tin người dùng: " + e.getMessage());
        }

        return response;
    }
         public Response.UserResult searchUsers(String searchTerm) {
        Response.UserResult result = new Response.UserResult();
        ArrayList<UserDTO> userList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE " +
                    "user_id = ? OR fullname LIKE ? OR email LIKE ?  AND is_deleted = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                   
                    ) {

                try {
                    pstmt.setInt(1, Integer.parseInt(searchTerm)); 
                } catch (NumberFormatException e) {
                    pstmt.setNull(1, java.sql.Types.INTEGER); 
                }
                                     

                pstmt.setString(2, "%" + searchTerm + "%"); 
                pstmt.setString(3, "%" + searchTerm + "%"); 
                  pstmt.setBoolean(4, false);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        UserDTO user = new UserDTO();
                        user.setUserId(rs.getInt("user_id"));
                        user.setUsername(rs.getString("fullname"));
                        user.setPassword(rs.getString("password"));
                        user.setEmail(rs.getString("email"));
                        user.setRole(rs.getString("role"));
                        userList.add(user);
                    }
                }
                result.setUserList(userList);
                result.setIsSuccess(true);
                result.setMessage("Tìm kiếm người dùng thành công.");

            }

        } catch (SQLException e) {
            result.setIsSuccess(false);
            result.setMessage("Lỗi tìm kiếm người dùng: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
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
   
         public BaseResponse updatePassword(String currentPassword, String newPassword, int userId) {
        BaseResponse response = new BaseResponse();
        try (Connection conn = DatabaseConnection.getConnection()) {
        
            String checkPasswordSql = "SELECT password FROM users WHERE user_id = ?";
            try (PreparedStatement checkPasswordStmt = conn.prepareStatement(checkPasswordSql)) {
                checkPasswordStmt.setInt(1, userId);
                try (ResultSet rs = checkPasswordStmt.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        if (!storedPassword.equals(currentPassword)) { 
                            response.setIsSuccess(false);
                            response.setMessage("Mật khẩu hiện tại không chính xác.");
                            return response; 
                        }
                    } else {
                        response.setIsSuccess(false);
                        response.setMessage("Không tìm thấy người dùng với ID: " + userId);
                        return response; 
                    }
                }
            }

      
            String updatePasswordSql = "UPDATE users SET password = ?, updater = ? WHERE user_id = ?";
            try (PreparedStatement updatePasswordStmt = conn.prepareStatement(updatePasswordSql)) {
                updatePasswordStmt.setString(1, newPassword);
                updatePasswordStmt.setString(2, App.user.getUsername()); 
                updatePasswordStmt.setInt(3, userId);

                int rowsAffected = updatePasswordStmt.executeUpdate();

                if (rowsAffected > 0) {
                    response.setIsSuccess(true);
                    response.setMessage("Mật khẩu đã được cập nhật thành công.");
                } else {
                    response.setIsSuccess(false);
                    response.setMessage("Không tìm thấy người dùng với ID: " + userId);
                }
            }

        } catch (SQLException e) {
            response.setIsSuccess(false);
            response.setMessage("Lỗi cập nhật mật khẩu: " + e.getMessage());
        }

        return response;
    }
           public BaseResponse resetPassword( int userId) {
        BaseResponse response = new BaseResponse();
        try (Connection conn = DatabaseConnection.getConnection()) {
        
            String checkPasswordSql = "SELECT * FROM users WHERE user_id = ?";
            try (PreparedStatement checkPasswordStmt = conn.prepareStatement(checkPasswordSql)) {
                checkPasswordStmt.setInt(1, userId);
                try (ResultSet rs = checkPasswordStmt.executeQuery()) {
                    if (!rs.next()) {
                     response.setIsSuccess(false);
                        response.setMessage("Không tìm thấy người dùng với ID: " + userId);
                        return response; 
                   
                }
            }
            }

      
            String updatePasswordSql = "UPDATE users SET password = ?, updater = ? WHERE user_id = ?";
            try (PreparedStatement updatePasswordStmt = conn.prepareStatement(updatePasswordSql)) {
                updatePasswordStmt.setString(1, 1+"");
                updatePasswordStmt.setString(2, App.user.getUsername()); 
                updatePasswordStmt.setInt(3, userId);

                int rowsAffected = updatePasswordStmt.executeUpdate();

                if (rowsAffected > 0) {
                    response.setIsSuccess(true);
                    response.setMessage("Mật khẩu đã được cập nhật thành công.");
                } else {
                    response.setIsSuccess(false);
                    response.setMessage("Không tìm thấy người dùng với ID: " + userId);
                }
            }

        } catch (SQLException e) {
            response.setIsSuccess(false);
            response.setMessage("Lỗi cập nhật mật khẩu: " + e.getMessage());
        }

        return response;
    }
}
