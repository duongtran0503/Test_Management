/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.controllers;

import com.mycompany.qltn_mvc.dtos.ExamDTO;
import com.mycompany.qltn_mvc.dtos.ExamQuestionDTO;
import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.dtos.TestDTO;
import com.mycompany.qltn_mvc.dtos.TopicDTO;
import com.mycompany.qltn_mvc.dtos.UserDTO;
import java.util.ArrayList;

/**
 *
 * @author ACER
 */
public class Response {
     public static  class loginResult {
       private UserDTO user;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
       private  String message;
       private  boolean  isSuccess;

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public UserDTO getUser() {
            return user;
        }

        public boolean isIsSuccess() {
            return isSuccess;
        }
  

   
     }
     public static class RegisterResult {
        private  String message;
        private boolean  isSuccess;

        public void setMessage(String message) {
            this.message = message;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public boolean isIsSuccess() {
            return isSuccess;
        }

       
        
      }
     public static class QuestoinResult {

        public ArrayList<QuestionDTO> getQuestionList() {
            return questionList;
        }

        public ArrayList<OptionDTO> getAnswerList() {
            return answerList;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public int getQuestionsPerPage() {
            return questionsPerPage;
        }

        public int getTotalQuestions() {
            return totalQuestions;
        }

        public boolean isIsSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public void setQuestionList(ArrayList<QuestionDTO> questionList) {
            this.questionList = questionList;
        }

        public void setAnswerList(ArrayList<OptionDTO> answerList) {
            this.answerList = answerList;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public void setQuestionsPerPage(int questionsPerPage) {
            this.questionsPerPage = questionsPerPage;
        }

        public void setTotalQuestions(int totalQuestions) {
            this.totalQuestions = totalQuestions;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    private  ArrayList<QuestionDTO> questionList = new ArrayList<>();
    private   ArrayList<OptionDTO>  answerList = new ArrayList<>();
    private int currentPage ;
    private int questionsPerPage ;
    private int totalQuestions ;
    private  boolean  isSuccess;
    private  String message;
    
     }
     public static class TopicResult {

        public ArrayList<TopicDTO> getTopicList() {
            return topicList;
        }

        public boolean isIsSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public void setTopicList(ArrayList<TopicDTO> topicList) {
            this.topicList = topicList;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        public void setMessage(String message) {
            this.message = message;
        }
       private  ArrayList<TopicDTO> topicList = new ArrayList<>();
       private  boolean  isSuccess;
       private  String message;
     }
     public static class ExamResult {
       private  ArrayList<TestDTO> testLists;
       private  ArrayList<ExamDTO> examList;
       private  ArrayList<ExamQuestionDTO> questoinList;
       private  String message;
       private  boolean  isSuccess;

        public ArrayList<TestDTO> getTestLists() {
            return testLists;
        }

        public void setTestLists(ArrayList<TestDTO> testLists) {
            this.testLists = testLists;
        }

        public ArrayList<ExamDTO> getExamList() {
            return examList;
        }

        public void setExamList(ArrayList<ExamDTO> examList) {
            this.examList = examList;
        }

        public ArrayList<ExamQuestionDTO> getQuestoinList() {
            return questoinList;
        }

        public void setQuestoinList(ArrayList<ExamQuestionDTO> questoinList) {
            this.questoinList = questoinList;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isIsSuccess() {
            return isSuccess;
        }

        public void setIsSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }
     }
}
