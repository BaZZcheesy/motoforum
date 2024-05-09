package ch.wiss.motoforumapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.wiss.motoforumapi.models.Question;
import ch.wiss.motoforumapi.models.QuestionRequest;
import ch.wiss.motoforumapi.repository.QuestionRepository;
import ch.wiss.motoforumapi.repository.UserRepository;
import ch.wiss.motoforumapi.security.JwtUtils;
import ch.wiss.motoforumapi.security.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    public QuestionRepository qr;
    @Autowired
    public UserRepository ur;
    @Autowired
    private JwtUtils jwtUtils;

    ObjectMapper om = new ObjectMapper();

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(HttpServletRequest request, @RequestBody String question) {
        try {
            System.out.println(question);
            QuestionRequest questionRequest = om.readValue(question, QuestionRequest.class);
            String username = jwtUtils.getUserNameFromJwtToken(questionRequest.getToken());
            var user = ur.findByUsername(username);
            if (user == null) {
                return ResponseEntity
                        .internalServerError()
                        .body(new MessageResponse("We could not get your username"));
            }
            Question questionToBeInserted = new Question(questionRequest.getQuestion(), user.get());
            qr.save(questionToBeInserted);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Question is online! Get ready for answers"));
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponse("Something went wrong while processing your request"));
        }
    }

    @PostMapping("/get")
    public List<Question> getQuestions() {
        return qr.findAll();
    }
}
