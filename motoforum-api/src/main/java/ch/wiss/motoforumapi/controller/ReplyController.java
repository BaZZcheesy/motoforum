package ch.wiss.motoforumapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.motoforumapi.repository.ReplyRepository;
import ch.wiss.motoforumapi.repository.UserRepository;
import ch.wiss.motoforumapi.security.JwtUtils;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    public ReplyRepository rr;
    @Autowired
    public UserRepository ur;
    @Autowired
    public JwtUtils ju;

    
    // replyToPost(Get question, maybe another function in questioncontroller) {
    //     Get user from jwt_token
    //     Reply reply = new Reply( question.id , reply , user)
    //     save(reply)
    // }

    //deletereply() {
    //    Get user and reply from token/request
    //    If ( request.user == replyToDelete.user || request.user.role == admin) {
    //    try {
    //    delete()
    //    return responsebody.ok
    //    }
    //    catch (ex) {
    //    Syso(ex)
    //    Return responsebody.badRequest
    //    }
    //    }
        
    
}
