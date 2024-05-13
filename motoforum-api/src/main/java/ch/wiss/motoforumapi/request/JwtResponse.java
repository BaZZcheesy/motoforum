package ch.wiss.motoforumapi.request;

// Objekt um ein JwtResponse Body abzufüllen
public class JwtResponse {
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
