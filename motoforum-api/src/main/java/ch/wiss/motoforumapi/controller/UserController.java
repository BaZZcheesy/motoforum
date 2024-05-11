package ch.wiss.motoforumapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.wiss.motoforumapi.models.User;
import ch.wiss.motoforumapi.repository.UserRepository;
import ch.wiss.motoforumapi.request.EditRequest;
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
    @Autowired
    private PasswordEncoder encoder;

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

    // Update property
    @PutMapping("/{userId}}/{valueToUpdate}")
    public ResponseEntity<?> updateValue(@RequestBody String requestBody, @PathVariable Long id, @PathVariable String valueToUpdate) {
        try {
            ObjectMapper om = new ObjectMapper();
            EditRequest editRequest = om.readValue(requestBody, EditRequest.class);
            String actorUsername = ju.getUserNameFromJwtToken(editRequest.getToken());
            var findUserToUpdate = ur.findById(id);
            if (!findUserToUpdate.isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("We could not find the user you are trying to update"));
            }
            var userToUpdate = findUserToUpdate.get();
            Optional<User> actor = ur.findByUsername(actorUsername);
            if (!actor.isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("We cant authenticate the user"));
            }
            if (!actor.get().isAdmin() || !actor.get().isModerator()) {
                if (!userToUpdate.getPassword().equals(encoder.encode(editRequest.getPassword())) || !userToUpdate.getUsername().equals(actorUsername)) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("You are not authorized to edit this user"));
                }
            }

            switch (valueToUpdate) {
                case "username":
                    userToUpdate.setUsername(editRequest.getProperty());
                    ur.save(userToUpdate);
                    break;
                
                case "motorcycle":
                    userToUpdate.setMotorcycle(editRequest.getProperty());
                    ur.save(userToUpdate);
                    break;

                case "email":
                    userToUpdate.setEmail(editRequest.getProperty());
                    ur.save(userToUpdate);
                    break;

                case "password":
                    userToUpdate.setPassword(encoder.encode(editRequest.getPassword()));
                    ur.save(userToUpdate);
                    break;

                default:
                    break;
            }

            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("Successfully updated "+ valueToUpdate));
        }
        catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body("Something went wrong while processing your request: " + ex);
        }  
    }
    // (Update Password)

    // (Ban User)

    // (Unban User)

    // Delete User
}
