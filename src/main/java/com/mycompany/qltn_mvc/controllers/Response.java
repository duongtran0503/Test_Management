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
     public static  class  BaseResponse {
       private  String message;
        private boolean  isSuccess;

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
    
     public static  class loginResult extends  BaseResponse{
       private UserDTO user;

     
    

        public void setUser(UserDTO user) {
            this.user = user;
        }

       

        public UserDTO getUser() {
            return user;
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
     public static class QuestoinResult extends  BaseResponse{

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

      
    private  ArrayList<QuestionDTO> questionList = new ArrayList<>();
    private   ArrayList<OptionDTO>  answerList = new ArrayList<>();
    private int currentPage ;
    private int questionsPerPage ;
    private int totalQuestions ;
 
    
     }
     public static class TopicResult extends  BaseResponse{

        public ArrayList<TopicDTO> getTopicList() {
            return topicList;
        }

        public void setTopicList(ArrayList<TopicDTO> topicList) {
            this.topicList = topicList;
        }

      
    
       private  ArrayList<TopicDTO> topicList = new ArrayList<>();
     
     }
     public static class ExamResult extends  BaseResponse{
       private  ArrayList<TestDTO> testLists;
       private  ArrayList<ExamDTO> examList;
    

        public QuestoinResult getListQestionOfExam() {
            return listQestionOfExam;
        }

        public void setListQestionOfExam(QuestoinResult listQestionOfExam) {
            this.listQestionOfExam = listQestionOfExam;
        }
       private  Response.QuestoinResult listQestionOfExam;
   

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

     
     }
}
