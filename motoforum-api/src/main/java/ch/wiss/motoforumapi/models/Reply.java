package ch.wiss.motoforumapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank
    public String reply;
    
    @OneToOne
    @JoinColumn( name = "user_id" )
    public User replier;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

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

    public User getReplier() {
        return replier;
    }

    public void setReplier(User replier) {
        this.replier = replier;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
