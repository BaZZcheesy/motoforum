package ch.wiss.motoforumapi.dto;

public class ReplyDTO {
    private Integer id;
    private String replyText;

    public ReplyDTO() {
        
    }

    public ReplyDTO(Integer id, String replyText) {
        this.id = id;
        this.replyText = replyText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }
}
