package ch.wiss.motoforumapi.request;

// Objekt um ein Editrequest Body abzufüllen
public class EditRequest {
    private String property;
    private String pw;
    
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
