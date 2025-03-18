package com.mycompany.qltn.BLL;

import com.mycompany.qltn.dto.OptionDTO;
import com.mycompany.qltn.dto.QuestionDTO;
import com.mycompany.qltn.dto.TestDTO;
import com.mycompany.qltn.DAL.ExamDAL;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ACER
 */
public class ExamBLL {

    private ExamDAL examDAL;

    public ExamBLL() {
        this.examDAL = new ExamDAL();
    }

    public Response.ExamResult getExamResult() {
        Response.ExamResult res = examDAL.getExamResult(0);
        return res;
    }

    public Response.ExamResult search(int status, String title, int topicId) {
        return examDAL.searchExam(status, title, topicId);
    }

    public Response.ExamResult getExamDeletedResult() {
        Response.ExamResult res = examDAL.getExamDeleteResult();
        return res;
    }

    public Response.BaseResponse recoverTest(int id) {
        Response.BaseResponse res = examDAL.recoverTest(id);
        return res;
    }

    public Response.BaseResponse recoverExam(int id) {
        Response.BaseResponse res = examDAL.recoverExam(id);
        return res;
    }

    public Response.ExamResult getTestByIdAndExamCode(int id, String code) {
        Response.ExamResult res = examDAL.getTestByIdAndExamCode(id, code);
        return res;
    }

    public Response.ExamResult getTests() {
        Response.ExamResult res = examDAL.getTestResult();
        return res;
    }

    public Response.ExamResult addQuestionToTheTest(ArrayList<QuestionDTO> questons, int examQuestionCode) {
        Response.ExamResult res = new Response.ExamResult();
        for (QuestionDTO questionDTO : questons) {
            res = examDAL.addQuestionToTheTest(examQuestionCode, questionDTO.getQuestionId());
            if (res.isIsSuccess()) {
            } else {
                return res;
            }
        }
        return res;
    }

    public Response.ExamResult exportExamToDoc(String filePath, String examName, int duration, String examCode, Response.QuestoinResult listQuestions) throws InvalidFormatException {
        Response.ExamResult res = new Response.ExamResult();

        try (XWPFDocument document = new XWPFDocument(); FileOutputStream out = new FileOutputStream(filePath)) {

            // Thêm thông tin bài thi
            addExamTitle(document, "Tên bài thi: " + examName);
            addCenteredParagraph(document, "Thời gian làm bài: " + duration + " phút");
            addCenteredParagraph(document, "Mã đề: " + examCode);
            addParagraph(document, "");

            for (int i = 0; i < listQuestions.getQuestionList().size(); i++) {
                QuestionDTO question = listQuestions.getQuestionList().get(i);
                addParagraph(document, "Câu " + (i + 1) + ": " + question.getQuestionText());
                if (question.getImageUrl() != null && !question.getImageUrl().isEmpty()) {
                    if (question.getImageUrl() != null && !question.getImageUrl().isEmpty()) {
                        try (InputStream is = getClass().getClassLoader().getResourceAsStream("imageQuestion/" + question.getImageUrl())) {
                            if (is != null) {
                                XWPFParagraph pictureParagraph = document.createParagraph();
                                XWPFRun pictureRun = pictureParagraph.createRun();
                                pictureRun.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, question.getImageUrl(), Units.toEMU(200), Units.toEMU(200));
                            } else {
                                addParagraph(document, "Không tìm thấy ảnh: " + question.getImageUrl());
                            }
                        } catch (IOException e) {
                            addParagraph(document, "Lỗi nhúng ảnh: " + e.getMessage());
                        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
                            addParagraph(document, "Lỗi định dạng ảnh: " + e.getMessage());
                        } finally {
                            addParagraph(document, "Không tìm thấy ảnh ");

                        }
                    }
                }
                for (OptionDTO option : listQuestions.getAnswerList()) {
                    if (option.getQuestionId() == question.getQuestionId()) {
                        addParagraph(document, "    A. " + option.getOptionText());

                    }
                }
                addParagraph(document, "");
            }

            document.write(out);
            res.setMessage("Xuất file .docs thành công!");
            res.setIsSuccess(true);
            return res;

        } catch (IOException e) {
            res.setMessage("Lỗi xuất file .docs: " + e.getMessage());
            res.setIsSuccess(false);
        }
        return res;
    }

    private void addCenteredParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER); // Căn giữa
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }

    private void addExamTitle(XWPFDocument document, String title) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText(title);
        run.setBold(true);
        run.setFontSize(24);
    }

    private static void addParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }

    public Response.ExamResult updateNameAndTestTime(String name, int num, int time, int id) {
        Response.ExamResult res = examDAL.updateNametestAndTimeTest(name, num, time, id);
        return res;
    }

    public Response.BaseResponse deleteExam(int id) {
        return examDAL.deleteExam(id);

    }

    public Response.BaseResponse createNewTest(TestDTO test, String topicName, ArrayList<String> examCodeList, int easyQ, int mediQ, int diffQ) {
        Response.BaseResponse res = examDAL.createNewTest(test, topicName, examCodeList, easyQ, mediQ, diffQ);
        return res;
    }

    public Response.BaseResponse addNewExamCode(TestDTO test, ArrayList<String> examCodeList, int easyQ, int mediQ, int diffQ) {
        Response.BaseResponse res = examDAL.addNewExamCode(test, examCodeList, easyQ, mediQ, diffQ);
        return res;
    }
    public Response.BaseResponse deleteQuestionFromExam(int examId,int questionId){
     return  examDAL.deleteQuestionFromExam(examId, questionId);
    }
    public Response.TestResult getTestsResult(int examId) {
        return examDAL.getTestsResult(examId);
    }

    public Response.ExamResult getExamById(int id) {
        return examDAL.getExamById(id);
    }

    public Response.ExamResult getTestById(int id) {
        return examDAL.getTestResultById(0, id);
    }

    public Response.ContestAntResult getUserContestAntResult(int testId) {
        return examDAL.getContestAntResult(testId);
    }

}
