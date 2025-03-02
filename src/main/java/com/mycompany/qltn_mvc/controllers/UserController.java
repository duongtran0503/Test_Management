/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.controllers;

import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.dtos.UserDTO;
import com.mycompany.qltn_mvc.models.UserModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ACER
 */
public class UserController {
    private final  UserModel userModel;
    public UserController() {
     this.userModel = new UserModel();
    }
    
    public Response.UserResult getUser(int status) {
      return  userModel.getUser(status);
    }
    
    public Response.UserResult searResult(String key){
      return  userModel.searchUsers(key);
    }
    
    public Response.BaseResponse deleteUser(int id) {
     return  userModel.deleteUser(id);
    }
    
    public Response.BaseResponse updateUser(UserDTO user) {
     return  userModel.updateUser(user);
    }
     public Response.BaseResponse exportToExcel(ArrayList<UserDTO> users, String filePath) {
         Response.BaseResponse res = new Response.BaseResponse();
         res.setIsSuccess(true);
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("users");

            
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Id", "Tên", "Email", "Quyền truy cập", "thời gian cập nhật"};
                    
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
             
            // Ghi dữ liệu
            int rowNum = 1;
            for (int i = 0; i < users.size(); i++) {
                Row row = sheet.createRow(rowNum++);
                UserDTO user = users.get(i);
                   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
       
                row.createCell(0).setCellValue(user.getUserId());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getEmail());
                row.createCell(3).setCellValue(user.getRole());
                row.createCell(4).setCellValue(user.getUpdated_at().format(formatter));
 
            }

            workbook.write(outputStream);
            res.setMessage("Dã lưu file vào hệ thông!");
            return  res;
        } catch (IOException e) {
            res.setMessage("Lỗi xuất file"+e.getMessage());
        }
         return  res;
    }
     public Response.BaseResponse updatePassword(String currentPassword,String newPassword,int userId) {
        return  userModel.updatePassword(currentPassword, newPassword, userId);
     }
}
