package ch.wiss.motoforumapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank
    public String reply;
    
    @NotBlank
    public Integer replier;

    @NotBlank
    public Integer question;

    public Reply() {
        
    }

    public Integer getId() {
        return id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Integer getReplier() {
        return replier;
    }

    public void setReplier(Integer replier) {
        this.replier = replier;
    }

    public Integer getQuestion() {
        return question;
    }

    public void setQuestion(Integer question) {
        this.question = question;
    }

    
}
