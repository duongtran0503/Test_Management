/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.dtos;

/**
 *
 * @author ACER
 */
public class ExamQuestionDTO {
   private int examQuestionId;
    private int examId;
    private int questionId; 

    public void setExamQuestionId(int examQuestionId) {
        this.examQuestionId = examQuestionId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getExamQuestionId() {
        return examQuestionId;
    }

    public int getExamId() {
        return examId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public ExamQuestionDTO(int examQuestionId, int examId, int questionId) {
        this.examQuestionId = examQuestionId;
        this.examId = examId;
        this.questionId = questionId;
    }
}
