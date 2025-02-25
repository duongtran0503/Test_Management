package com.mycompany.qltn_mvc.controllers;

import com.mycompany.qltn_mvc.models.ExamModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ACER
 */
public class ExamController {
    private  ExamModel examModel;
    public ExamController() {
     this.examModel = new ExamModel();
    }
      public Response.ExamResult getExamResult() {
       Response.ExamResult res = examModel.getExamResult();
       return  res;
      }
}
