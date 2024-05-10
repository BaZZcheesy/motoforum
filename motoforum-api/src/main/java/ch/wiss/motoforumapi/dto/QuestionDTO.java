package ch.wiss.motoforumapi.dto;

import java.util.List;

import ch.wiss.motoforumapi.models.User;

public class QuestionDTO {
    private Integer id;
    private String question;
    private User questioner;
    private List<ReplyDTO> replies;

    public QuestionDTO() {

    }
    
    public QuestionDTO(Integer id, String question, List<ReplyDTO> replies) {
        this.id = id;
        this.question = question;
        this.replies = replies;
    }

    public void setId(Integer id) {
        this.id = id;
    }   

    public Integer getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ReplyDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyDTO> replies) {
        this.replies = replies;
    }

    public User getQuestioner() {
        return questioner;
    }

    public void setQuestioner(User questioner) {
        this.questioner = questioner;
    } 
}
