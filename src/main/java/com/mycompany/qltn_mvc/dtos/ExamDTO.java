/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.dtos;

/**
 *
 * @author ACER
 */
public class ExamDTO {
     private int examId;
    private int testId;
    private String examCode;

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public int getExamId() {
        return examId;
    }

    public int getTestId() {
        return testId;
    }

    public String getExamCode() {
        return examCode;
    }

    public ExamDTO(int examId, int testId, String examCode) {
        this.examId = examId;
        this.testId = testId;
        this.examCode = examCode;
    }
}
