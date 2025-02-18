/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.dtos;

/**
 *
 * @author ACER
 */
public class OptionDTO {
     private int optionId;
    private int questionId;
    private String optionText;
    private boolean isCorrect;
    private String imageUrl;

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getOptionId() {
        return optionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OptionDTO(int optionId, int questionId, String optionText, boolean isCorrect, String imageUrl) {
        this.optionId = optionId;
        this.questionId = questionId;
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.imageUrl = imageUrl;
    }
}
