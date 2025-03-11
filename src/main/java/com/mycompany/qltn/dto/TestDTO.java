/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn.dto;

/**
 *
 * @author ACER
 */
public class TestDTO {
      private int testId;
    private int topicId;
    private String testName;
    private int testTime;
    private int numLimit;
    private  int easy;
    private  int medium;
    private  int difficult;
    private  int totalQuestion;

    public int getEasy() {
        return easy;
    }

    public void setEasy(int easy) {
        this.easy = easy;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }
    
    public TestDTO() {
    }
   
    public void setTestId(int testId) {
        this.testId = testId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setTestTime(int testTime) {
        this.testTime = testTime;
    }

    public void setNumLimit(int numLimit) {
        this.numLimit = numLimit;
    }

    public int getTestId() {
        return testId;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getTestName() {
        return testName;
    }

    public int getTestTime() {
        return testTime;
    }

    public int getNumLimit() {
        return numLimit;
    }

    public TestDTO(int testId, int topicId, String testName, int testTime, int numLimit) {
        this.testId = testId;
        this.topicId = topicId;
        this.testName = testName;
        this.testTime = testTime;
        this.numLimit = numLimit;
    }
}
