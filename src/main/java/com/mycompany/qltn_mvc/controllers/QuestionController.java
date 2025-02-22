/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.controllers;

import com.mycompany.qltn_mvc.models.QuestionModel;

/**
 *
 * @author ACER
 */
public class QuestionController {
      private final  QuestionModel questionModel ;

    public QuestionController() {
      this.questionModel = new QuestionModel();
    }
    public Response.QuestoinResult getQuestoinResult(int page,int questionsPerPage) {
       Response.QuestoinResult res = questionModel.getQuestoinResult(page,questionsPerPage);
        return  res;
    }
    
    public Response.TopicResult getTopic() {
      Response.TopicResult res = questionModel.getTopic();
      return  res;
    }
    
    public Response.QuestoinResult searchQuestionByTitle(String tilte) {
       Response.QuestoinResult res = questionModel.searchQuestionByTitle(tilte);
       return  res;
    }
    
    public Response.QuestoinResult searchQuestionByTitleAndTopic(String title,int topic) {
         Response.QuestoinResult res = questionModel.searchQuestionByTitleAndTopic(title, topic);
         return  res;
    }
      
}
