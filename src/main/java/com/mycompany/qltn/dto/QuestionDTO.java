/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.dto;

import java.sql.Date;
import java.time.LocalDateTime;


/**
 *
 * @author ACER
 */
public class QuestionDTO {
    public static String EASY="easy";
    public static String MEDIUM ="medium";
    public static String DIFFICULT = "difficult";
 private int questionId;
    private int topicId;
    private String questionText;
    private String imageUrl;
    private String difficulty; // "easy", "medium" hoặc "difficult" 
     private LocalDateTime updated_at;
    private LocalDateTime create_ar;
    private String updater;
    
    private  boolean  is_deleted;
    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setCreate_ar(LocalDateTime create_ar) {
        this.create_ar = create_ar;
    }

    public LocalDateTime getCreate_ar() {
        return create_ar;
    }
    public boolean isIs_deleted() {
        return is_deleted;
    }


    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
  

    public String getUpdater() {
        return updater;
    }

    

    public void setUpdater(String updater) {
        this.updater = updater;
    }
     
 public QuestionDTO() {};
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public QuestionDTO(int questionId, int topicId, String questionText, String imageUrl, String difficulty) {
        this.questionId = questionId;
        this.topicId = topicId;
        this.questionText = questionText;
        this.imageUrl = imageUrl;
        this.difficulty = difficulty;
    }
}
