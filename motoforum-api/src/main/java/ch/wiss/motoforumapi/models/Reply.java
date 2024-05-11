package ch.wiss.motoforumapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String reply;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User replier;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private boolean isSolution;

    public Reply() {

    }

    public Reply(String reply, User replier, Question replyToQuestion) {
        this.reply = reply;
        this.replier = replier;
        this.question = replyToQuestion;
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

    public boolean isSolution() {
        return isSolution;
    }

    public void setSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }
}
