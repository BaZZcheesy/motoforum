package ch.wiss.motoforumapi.models;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotBlank
    public String question;

    @NotBlank
    @OneToOne
    @JoinColumn( name = "user_id")
    private User questioner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    public Set<Reply> replies;



    public Question() {

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

    //public User getQuestioner() {
    //    return questioner;
    //}

    //public void setQuestioner(User questioner) {
    //    this.questioner = questioner;
    //}
}
