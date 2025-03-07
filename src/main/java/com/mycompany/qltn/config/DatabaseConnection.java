/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ACER
 */
public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quanlytracnghiem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("ket noi thanh cong!");
        } catch (ClassNotFoundException e) {
            System.err.println("loi: khong tim thay driver db");
        } catch (SQLException e) {
            System.err.println("loi ket noi db " + e.getMessage());
        }
        return connection;
    }

    
}
