package ch.wiss.motoforumapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.motoforumapi.models.User;
import ch.wiss.motoforumapi.repository.UserRepository;
import ch.wiss.motoforumapi.security.JwtUtils;
import ch.wiss.motoforumapi.security.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository ur;
    @Autowired
    private JwtUtils ju;

    @GetMapping()
    public String getUsernameByToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return ju.getUserNameFromJwtToken(token);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            Optional<User> userToFetch = ur.findByUsername(username);
            if (userToFetch.isPresent()) {
                User user = userToFetch.get();
                return ResponseEntity
                        .ok()
                        .body(user);
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("We could no find the specified user in our database"));
        }
        catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponse("Something internal happened while processing your request"));
        }
    }

    
}
