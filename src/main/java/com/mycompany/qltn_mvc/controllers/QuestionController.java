/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.controllers;

import com.mycompany.qltn_mvc.dtos.OptionDTO;
import com.mycompany.qltn_mvc.dtos.QuestionDTO;
import com.mycompany.qltn_mvc.models.QuestionModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
   
   public Response.QuestoinResult importExcelAndPopulateLists(File selectedFile) {
            Response.QuestoinResult res = new Response.QuestoinResult();
            res.setAnswerList(new ArrayList<>());
            res.setQuestionList(new ArrayList<>());
            res.setIsSuccess(true);
        try {
            FileInputStream fis = new FileInputStream(selectedFile);
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);
            int numberOfRows = sheet.getLastRowNum();
            System.out.println("num"+numberOfRows);
            int numberOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
            System.out.println("run1");
            for (int i = 1; i <= numberOfRows; i++) {
                Row currentRow = sheet.getRow(i);
                
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setQuestionId(i);
                 
                if (numberOfColumns >= 12) { // Kiểm tra số lượng cột
                      System.out.println("run2");
                    Cell topicIdCell = currentRow.getCell(0);
                    if (topicIdCell != null && topicIdCell.getCellType() == CellType.NUMERIC) {
                        questionDTO.setTopicId((int) topicIdCell.getNumericCellValue());
                          System.out.println("run3");
                    }

                    Cell questionTextCell = currentRow.getCell(1);
                    if (questionTextCell != null && questionTextCell.getCellType() == CellType.STRING) {
                        questionDTO.setQuestionText(questionTextCell.getStringCellValue());
                          System.out.println("run4");
                    }

                    Cell imageUrlCell = currentRow.getCell(2);
                    if (imageUrlCell != null && imageUrlCell.getCellType() == CellType.STRING) {
                        questionDTO.setImageUrl(imageUrlCell.getStringCellValue());
                          System.out.println("run5");
                    }

                    Cell difficultyCell = currentRow.getCell(3);
                    if (difficultyCell != null && difficultyCell.getCellType() == CellType.STRING) {
                        questionDTO.setDifficulty(difficultyCell.getStringCellValue());
                          System.out.println("run6");
                    }

                    // Đọc các options
                    for (int j = 4; j < 12; j += 2) {
                        OptionDTO optionDTO = new OptionDTO();
                        optionDTO.setQuestionId(questionDTO.getQuestionId());
                        Cell optionTextCell = currentRow.getCell(j);
                        Cell isCorrectCell = currentRow.getCell(j + 1);

                        if (optionTextCell != null) {
                            if (optionTextCell.getCellType() == CellType.STRING) {
                                optionDTO.setOptionText(optionTextCell.getStringCellValue());
                            } else if (optionTextCell.getCellType() == CellType.NUMERIC) {
                              
                                optionDTO.setOptionText(String.valueOf((int) optionTextCell.getNumericCellValue())); 
                            }
                        }
                        if (isCorrectCell != null && isCorrectCell.getCellType() == CellType.BOOLEAN) {
                            optionDTO.setIsCorrect(isCorrectCell.getBooleanCellValue());
                        }
  System.out.println("run7");
                        res.getAnswerList().add(optionDTO);
                    }

                  

                    res.getQuestionList().add(questionDTO);
                   
                } else {
                 res.setMessage("dữ liệu cảu file không hợp lệ!");
                 res.setIsSuccess(false);
             
                }
            }

            workbook.close();
            fis.close();
             if(res.isIsSuccess()) {
             res.setMessage("đọc file excel thành công!");
             }
            return  res;
        } catch (IOException e) {
          
         res.setIsSuccess(false);
         res.setMessage("Lỗi không đọc được file:"+e.getMessage());
           
        }
        return  res;
    }
   
    public Response.QuestoinResult exportToExcel(ArrayList<QuestionDTO> questions, ArrayList<OptionDTO> answerLists, String filePath) {
         Response.QuestoinResult res = new Response.QuestoinResult();
         res.setIsSuccess(true);
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("Questions");

            
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Question Id", "Topic ID", "Question Text", "Image URL", "Difficulty",
                    "Option 1", "Correct 1", "Option 2", "Correct 2", "Option 3", "Correct 3", "Option 4", "Correct 4"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
             
            // Ghi dữ liệu
            int rowNum = 1;
            for (int i = 0; i < questions.size(); i++) {
                Row row = sheet.createRow(rowNum++);
                QuestionDTO question = questions.get(i);
               ArrayList <OptionDTO> answer = new ArrayList<>();
               for(OptionDTO o:answerLists) {
                 if(o.getQuestionId()==question.getQuestionId()) {
                    answer.add(o);
                 }
               }

                // Ghi dữ liệu câu hỏi
                row.createCell(0).setCellValue(question.getQuestionId());
                row.createCell(1).setCellValue(question.getTopicId());
                row.createCell(2).setCellValue(question.getQuestionText());
                row.createCell(3).setCellValue(question.getImageUrl());
                row.createCell(4).setCellValue(question.getDifficulty());

                // Ghi dữ liệu option
                for (int j = 0; j < answer.size(); j++) {
                    OptionDTO option = answer.get(j);
                    row.createCell(5 + j * 2).setCellValue(option.getOptionText());
                    row.createCell(6 + j * 2).setCellValue(option.isIsCorrect());
                }
            }

            workbook.write(outputStream);
            res.setMessage("Dã lưu file vào hệ thông!");
            return  res;
        } catch (IOException e) {
            res.setMessage("Lỗi xuất file"+e.getMessage());
        }
         return  res;
    }
    
    public Response.QuestoinResult getQuestionDeleted() {
      Response.QuestoinResult res = questionModel.getQuestionDeleted();
      return  res;
    }
    
    public Response.QuestoinResult recoverQuestion(int id) {
      Response.QuestoinResult res = questionModel.recoverQuestion(id);
      return  res;
    }
    
    public Response.QuestoinResult filterQuestionsResult(String difficulty,int TopicId) {
      Response.QuestoinResult res = questionModel.filterQuestionBydifficulty(difficulty, TopicId);
      return  res;
    }
   
}
