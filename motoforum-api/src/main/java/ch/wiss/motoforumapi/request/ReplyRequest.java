package ch.wiss.motoforumapi.request;

// Objekt um ein Replyrequest Body abzufüllen
public class ReplyRequest {
    private String reply;
    private Long questionId;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getReply() {
        return reply;
    }
    
    public void setReply(String reply) {
        this.reply = reply;
    }
}
