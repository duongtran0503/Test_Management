/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.BLL;

import com.mycompany.qltn.dto.OptionDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.UserDTO;
import com.mycompany.qltn.DAL.UserDAL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ACER
 */
public class UserBLL {

    private final UserDAL userDAL;
    private final AuthBLL authBLL;

    public UserBLL() {
        this.userDAL = new UserDAL();
        this.authBLL = new AuthBLL();
    }

    public Response.UserResult getUser(int status) {
        return userDAL.getUser(status);
    }

    public Response.UserResult searResult(String key) {
        return userDAL.searchUsers(key);
    }

    public Response.BaseResponse deleteUser(int id) {
        return userDAL.deleteUser(id);
    }

    public Response.BaseResponse updateUser(UserDTO user) {
        return userDAL.updateUser(user);
    }

    public Response.BaseResponse exportToExcel(ArrayList<UserDTO> users, String filePath) {
        Response.BaseResponse res = new Response.BaseResponse();
        res.setIsSuccess(true);
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream outputStream = new FileOutputStream(filePath)) {

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
            return res;
        } catch (IOException e) {
            res.setMessage("Lỗi xuất file" + e.getMessage());
        }
        return res;
    }

    public Response.BaseResponse updatePassword(String currentPassword, String newPassword, int userId) {
        return userDAL.updatePassword(currentPassword, newPassword, userId);
    }

    public Response.UserResult importExcel(File selectedFile) {
        Response.UserResult Result = new Response.UserResult();
        Result.setUserList(new ArrayList<>());
        Result.setIsSuccess(true);
        try {
            FileInputStream fis = new FileInputStream(selectedFile);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int numberOfRows = sheet.getLastRowNum();

            int numberOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            for (int i = 1; i <= numberOfRows; i++) {
                Row currentRow = sheet.getRow(i);

                UserDTO user = new UserDTO();

                if (numberOfColumns >= 4) {
                    System.out.println("run2");
                    Cell userNameCell = currentRow.getCell(0);
                    if (userNameCell != null && userNameCell.getCellType() == CellType.STRING) {
                        user.setUsername(userNameCell.getStringCellValue());
                    }

                    Cell emailCell = currentRow.getCell(1);
                    if (emailCell != null && emailCell.getCellType() == CellType.STRING) {
                        user.setEmail(emailCell.getStringCellValue());

                    }

                    Cell passwordCell = currentRow.getCell(2);
                    if (passwordCell != null && passwordCell.getCellType() == CellType.STRING) {
                        user.setPassword(passwordCell.getStringCellValue());
                    }

                    Cell roleCell = currentRow.getCell(3);
                    if (roleCell != null && roleCell.getCellType() == CellType.STRING) {
                        user.setRole(roleCell.getStringCellValue());
                    }

                    Result.getUserList().add(user);

                } else {
                    Result.setMessage("dữ liệu cảu file không hợp lệ!");
                    Result.setIsSuccess(false);

                }
            }

            workbook.close();
            fis.close();
            if (Result.isIsSuccess()) {
                Result.setMessage("đọc file excel thành công!");
            }
            return Result;
        } catch (IOException e) {

            Result.setIsSuccess(false);
            Result.setMessage("Lỗi không đọc được file:" + e.getMessage());

        }
        return Result;
    }

    public Response.BaseResponse addListUser(ArrayList<UserDTO> users) {
        Response.BaseResponse res = new Response.BaseResponse();
        for (UserDTO user : users) {
            String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            if (!user.getEmail().matches(regex)) {
                res.setIsSuccess(false);
                res.setMessage("email không hợp lệ!");
                return res;
            }
        }
        res.setIsSuccess(true);
        String errors = "";
        StringBuilder errorBuilder = new StringBuilder();

        for (UserDTO user : users) {
            try {
                Response.RegisterResult response = authBLL.register(user);
                if (!response.isIsSuccess()) {
                    errorBuilder.append(user.getEmail()).append(" err: ").append(response.getMessage()).append("\n");
                    res.setIsSuccess(false);
                    System.err.println("Lỗi đăng ký người dùng " + user.getEmail() + ": " + response.getMessage());
                }
            } catch (Exception e) {
                errorBuilder.append(user.getEmail()).append(" err: Lỗi không xác định ").append(e.getMessage()).append("\n");
                System.err.println("Lỗi đăng ký người dùng " + user.getEmail() + ": " + e.getMessage());
            }
        }
        errors = errorBuilder.toString();
        if (res.isIsSuccess()) {
            res.setMessage("Đã  thêm danh sách tài khoản vào!");
        } else {
            res.setMessage(errors);
        }
        return res;
    }

    public Response.BaseResponse resetPassword(int id) {
        return userDAL.resetPassword(id);

    }

    public Response.UserResult getUserById(int id) {
        return userDAL.getUserById(id);
    }
    public Response.BaseResponse recoverUser(int userId) {
     return  this.userDAL.recoverUser(userId);
    }
}
