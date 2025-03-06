/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.dtos;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class ResultDTO {
     private int resultId;
    private int userId;
    private int examId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int score;
    private  double  correct;
    public ResultDTO(){}
    public double getCorrect() {
        return correct;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
 
    public void setCorrect(double correct) {
        this.correct = correct;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

  

    public void setScore(int score) {
        this.score = score;
    }

    public int getResultId() {
        return resultId;
    }

    public int getUserId() {
        return userId;
    }

    public int getExamId() {
        return examId;
    }

  
    public int getScore() {
        return score;
    }

    public ResultDTO(int resultId, int userId, int examId, LocalDateTime startTime, LocalDateTime endTime, int score) {
        this.resultId = resultId;
        this.userId = userId;
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }
}
