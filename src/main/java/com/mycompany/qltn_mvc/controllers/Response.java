/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.controllers;

import com.mycompany.qltn_mvc.dtos.UserDTO;

/**
 *
 * @author ACER
 */
public class Response {
     public static  class loginResult {
       private UserDTO user;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
       private  String message;
       private  boolean  isSuccess;

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public UserDTO getUser() {
            return user;
        }

        public boolean isIsSuccess() {
            return isSuccess;
        }
  

   
     }
     public static class RegisterResult {
        private  String message;
        private boolean  isSuccess;

        public void setMessage(String message) {
            this.message = message;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public boolean isIsSuccess() {
            return isSuccess;
        }

       
        
      }
}
