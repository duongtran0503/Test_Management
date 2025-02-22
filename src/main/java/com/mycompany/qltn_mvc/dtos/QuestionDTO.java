/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.dtos;

/**
 *
 * @author ACER
 */
public class QuestionDTO {
 private int questionId;
    private int topicId;
    private String questionText;
    private String imageUrl;
    private String difficulty; // "easy", "medium" hoặc "difficult"   
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
