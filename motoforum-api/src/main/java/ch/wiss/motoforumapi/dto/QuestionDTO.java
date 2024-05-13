package ch.wiss.motoforumapi.dto;

import java.util.List;

import ch.wiss.motoforumapi.models.User;

public class QuestionDTO {
    private Long id;
    private String question;
    private User questioner;
    private List<ReplyDTO> replies;
    private boolean isSolved;

    public QuestionDTO() {

    }
    
    public QuestionDTO(Long id, String question, List<ReplyDTO> replies) {
        this.id = id;
        this.question = question;
        this.replies = replies;
    }

    public void setId(Long id) {
        this.id = id;
    }   

    public Long getId() {
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

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }
    
}
