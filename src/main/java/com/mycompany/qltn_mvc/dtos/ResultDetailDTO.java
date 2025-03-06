/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.dtos;

/**
 *
 * @author ACER
 */
public class ResultDetailDTO {
    private int resultDetailId;
    private int resultId;
    private int questionId;
    private int optionId; 
  public ResultDetailDTO(){}
    public void setResultDetailId(int resultDetailId) {
        this.resultDetailId = resultDetailId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getResultDetailId() {
        return resultDetailId;
    }

    public int getResultId() {
        return resultId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getOptionId() {
        return optionId;
    }

    public ResultDetailDTO(int resultDetailId, int resultId, int questionId, int optionId) {
        this.resultDetailId = resultDetailId;
        this.resultId = resultId;
        this.questionId = questionId;
        this.optionId = optionId;
    }
}
