/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.BLL;

import com.mycompany.qltn.dto.UserDTO;
import com.mycompany.qltn.DAL.AuthDAL;

/**
 *
 * @author ACER
 */
public class AuthBLL {

    private final AuthDAL authDAL = new AuthDAL();

    public Response.loginResult login(String email, String password) {
        Response.loginResult res;
        res = this.authDAL.login(email);
        if (res.isIsSuccess()) {
            System.out.println("password" + res.getUser().getPassword());
            if (password.equalsIgnoreCase(res.getUser().getPassword())) {
                res.getUser().setPassword("");
                res.setMessage("Đăng nhập thành công!");
                res.setIsSuccess(true);
                return res;
            } else {
                res.setMessage("Mật khẩu hoặc email không đúng!");
                res.setIsSuccess(false);
                return res;
            }
        }
        res.setIsSuccess(false);
        res.setMessage("Người dùng chưa đăng ký!");
        return res;
    }

    public Response.RegisterResult register(UserDTO userDTO) {

        return authDAL.register(userDTO);
    }
}
