package ch.wiss.motoforumapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.wiss.motoforumapi.models.User;
import ch.wiss.motoforumapi.models.Question;
import ch.wiss.motoforumapi.models.Reply;
import ch.wiss.motoforumapi.repository.QuestionRepository;
import ch.wiss.motoforumapi.repository.ReplyRepository;
import ch.wiss.motoforumapi.repository.UserRepository;
import ch.wiss.motoforumapi.request.ReplyRequest;
import ch.wiss.motoforumapi.security.JwtUtils;
import ch.wiss.motoforumapi.security.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    public ReplyRepository rr;
    @Autowired
    public UserRepository ur;
    @Autowired
    public QuestionRepository qr;
    @Autowired
    public JwtUtils ju;

    @PostMapping("/insert")
    public ResponseEntity<?> insertReply(HttpServletRequest request, @RequestBody String replyData) {
        try {
            ObjectMapper om = new ObjectMapper();
            ReplyRequest replyRequest = om.readValue(replyData, ReplyRequest.class);
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            String username = ju.getUserNameFromJwtToken(token);
            var user = ur.findByUsername(username);
            if (user == null) {
                return ResponseEntity
                        .internalServerError()
                        .body(new MessageResponse("User not found"));
            }
            Question question = qr.findById(replyRequest.getQuestionId()).orElse(null);
            if (question == null) {
                return ResponseEntity
                        .internalServerError()
                        .body(new MessageResponse("Question not found"));
            }
            Reply replyToBeInserted = new Reply(replyRequest.getReply(), user.get(), question);
            rr.save(replyToBeInserted);
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Reply added successfully"));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponse("Error adding reply: " + ex.getMessage()));
        }
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<?> deleteReply(HttpServletRequest request, @PathVariable Long replyId) {
        try {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            String username = ju.getUserNameFromJwtToken(token);
            Optional<User> user = ur.findByUsername(username);
            if (user.isEmpty()) {
                return ResponseEntity
                        .internalServerError()
                        .body(new MessageResponse("User not found"));
            }

            Optional<Reply> reply = rr.findById(replyId);
            if (reply.isEmpty()) {
                return ResponseEntity
                        .internalServerError()
                        .body(new MessageResponse("Reply not found"));
            }

            if (!reply.get().getReplier().equals(user.get()) || !user.get().isAdmin() || !user.get().isModerator() ) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("You are not authorized to delete this reply"));
            }

            rr.delete(reply.get());
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Reply deleted successfully"));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponse("Error deleting reply: " + ex.getMessage()));
        }
    }
    
    // Mark as correct
    

    // (Like and dislike)
}