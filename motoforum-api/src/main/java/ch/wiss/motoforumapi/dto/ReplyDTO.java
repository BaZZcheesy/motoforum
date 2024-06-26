package ch.wiss.motoforumapi.dto;

public class ReplyDTO {
    private Integer id;
    private String replyText;
    private boolean isSolution;

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

    public boolean isSolution() {
        return isSolution;
    }

    public void setSolution(boolean isSolution) {
        this.isSolution = isSolution;
    }
    
}
