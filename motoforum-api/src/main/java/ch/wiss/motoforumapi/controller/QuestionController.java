package ch.wiss.motoforumapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.motoforumapi.repository.QuestionRepository;
import ch.wiss.motoforumapi.repository.UserRepository;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    public QuestionRepository qr;
    
    @Autowired
    public UserRepository ur;

    @PostMapping
    public void askQuestion(@RequestBody String question) {
        System.out.println(question);
    }
}
