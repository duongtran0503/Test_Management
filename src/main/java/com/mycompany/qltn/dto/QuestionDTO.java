package com.mycompany.qltn.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO cho bảng `questions`
 */
public class QuestionDTO {
    public static final String EASY = "easy";
    public static final String MEDIUM = "medium";
    public static final String DIFFICULT = "difficult";

    private int questionId;
    private int topicId;
    private String questionText;
    private String imageUrl;
    private String difficulty;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String updater;
    private boolean isDeleted;

    // Danh sách phương án và đáp án đúng
    private List<String> options;
    private List<AnswerDTO> answers; // Danh sách các đáp án
    private String correctAnswer;

    public QuestionDTO() {
        this.options = new ArrayList<>();
        this.answers = new ArrayList<>();
    }
    
    public QuestionDTO(int questionId, int topicId, String questionText, String imageUrl, String difficulty) {
        this.questionId = questionId;
        this.topicId = topicId;
        this.questionText = questionText;
        this.imageUrl = imageUrl;
        this.difficulty = difficulty;
        this.options = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    // Getters & Setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    // Quản lý danh sách phương án
    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void addOption(String option) {
        this.options.add(option);
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }

}