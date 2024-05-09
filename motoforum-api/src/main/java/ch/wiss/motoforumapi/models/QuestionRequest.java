package ch.wiss.motoforumapi.models;

public class QuestionRequest {
    public String question;
    public String token;

    public QuestionRequest() {

    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
