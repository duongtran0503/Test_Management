/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.controllers;

import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.models.QuestionModel;
import java.util.ArrayList;

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
    
   public Response.QuestoinResult addQuestoin(QuestionDTO questionDTO, ArrayList<OptionDTO> listOption){
      Response.QuestoinResult res = questionModel.AddQuestoin(questionDTO, listOption);
      return  res;
   }
   
    public Response.QuestoinResult updateQuestoin(QuestionDTO questionDTO, ArrayList<OptionDTO> listOption){
      Response.QuestoinResult res = questionModel.updateQuestion(questionDTO, listOption);
      return  res;
   }
    
   public Response.QuestoinResult deleteQuestion(int id) {
      Response.QuestoinResult res = questionModel.delelteQuestoin(id);
      return  res;
   }
   
}
