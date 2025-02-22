/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qltn_mvc.dtos;

/**
 *
 * @author ACER
 */
public class TopicDTO {
     private int topicId;
    private String topicName;

    public TopicDTO() {
    }
 
    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public TopicDTO(int topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
    }
}
