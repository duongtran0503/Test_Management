/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.dtos;

import java.util.Date;

/**
 *
 * @author ACER
 */
public class ResultDTO {
     private int resultId;
    private int userId;
    private int examId;
    private Date startTime;
    private Date endTime;
    private int score;

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getScore() {
        return score;
    }

    public ResultDTO(int resultId, int userId, int examId, Date startTime, Date endTime, int score) {
        this.resultId = resultId;
        this.userId = userId;
        this.examId = examId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.score = score;
    }
}
