package ch.wiss.motoforumapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.wiss.motoforumapi.dto.QuestionDTO;
import ch.wiss.motoforumapi.dto.ReplyDTO;
import ch.wiss.motoforumapi.models.Question;
import ch.wiss.motoforumapi.models.Reply;
import ch.wiss.motoforumapi.models.User;
import ch.wiss.motoforumapi.repository.QuestionRepository;
import ch.wiss.motoforumapi.repository.ReplyRepository;
import ch.wiss.motoforumapi.repository.UserRepository;
import ch.wiss.motoforumapi.request.QuestionRequest;
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
    public ReplyRepository rr;
    @Autowired
    private JwtUtils ju;

    ObjectMapper om = new ObjectMapper();

    @PostMapping("/ask")
    public ResponseEntity<?> askQuestion(HttpServletRequest request, @RequestBody String question) {
        try {
            System.out.println(question);
            QuestionRequest questionRequest = om.readValue(question, QuestionRequest.class);
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            String username = ju.getUserNameFromJwtToken(token);
            var user = ur.findByUsername(username);
            if (!user.isPresent()) {
                return ResponseEntity
                        .internalServerError()
                        .body(new MessageResponse("We could not get your username"));
            }
            Question questionToBeInserted = new Question(questionRequest.getQuestion(), user.get());
            qr.save(questionToBeInserted);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Question is online! Get ready for answers"));
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponse("Something went wrong while processing your request"));
        }
    }

    @GetMapping("/getall")
    public List<QuestionDTO> getAllQuestionsWithReplies() {
        List<Question> questions = qr.findAll(); // Fetch all questions
        List<QuestionDTO> questionDTOs = new ArrayList<>();

        for (Question question : questions) {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestion(question.getQuestion());
            questionDTO.setQuestioner(question.getQuestioner());
            questionDTO.setSolved(question.isSolved());
            List<ReplyDTO> replyDTOs = new ArrayList<>();
            for (Reply reply : question.getReplies()) {
                ReplyDTO replyDTO = new ReplyDTO();
                replyDTO.setId(reply.getId());
                replyDTO.setReplyText(reply.getReply());
                replyDTO.setSolution(reply.isSolution());
                // Set other reply fields if needed
                replyDTOs.add(replyDTO);
            }
            questionDTO.setReplies(replyDTOs);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }

    @GetMapping("/getone/{questionId}")
    public QuestionDTO getOneQuestionWithReplies(@PathVariable int questionId) {
        List<Question> questions = qr.findAll(); // Fetch all questions

        // Choose one question from the list (You can implement any logic here to choose
        // the question)
        Question question = questions.get(questionId-1); // For simplicity, let's just choose the first question

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setQuestion(question.getQuestion());
        questionDTO.setQuestioner(question.getQuestioner());
        questionDTO.setSolved(question.isSolved());
        List<ReplyDTO> replyDTOs = new ArrayList<>();
        for (Reply reply : question.getReplies()) {
            ReplyDTO replyDTO = new ReplyDTO();
            replyDTO.setId(reply.getId());
            replyDTO.setReplyText(reply.getReply());
            replyDTO.setSolution(reply.isSolution());
            // Set other reply fields if needed
            replyDTOs.add(replyDTO);
        }
        questionDTO.setReplies(replyDTOs);

        return questionDTO;
    }

    // Delete Question
    @DeleteMapping("/delete/{questionToDeleteId}")
    public ResponseEntity<?> deleteQuestion(HttpServletRequest request, @PathVariable Long questionToDeleteId) {
        try {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            String username = ju.getUserNameFromJwtToken(token);
            Optional<User> user = ur.findByUsername(username);
            if (user.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("User not found"));
            }

            Optional<Question> questionToDelete = qr.findById(questionToDeleteId);
            if (questionToDelete.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Reply not found"));
            }

            if (!user.get().isAdmin() && !user.get().isModerator()) {
                if (!questionToDelete.get().getQuestioner().equals(user.get())) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("You are not authorized to delete this reply"));
                }
            }

            qr.delete(questionToDelete.get());
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Reply deleted successfully"));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponse("Error deleting reply: " + ex.getMessage()));
        }
    }
}
