package ch.wiss.motoforumapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.wiss.motoforumapi.dto.PublicUserDto;
import ch.wiss.motoforumapi.models.User;
import ch.wiss.motoforumapi.repository.UserRepository;
import ch.wiss.motoforumapi.request.EditRequest;
import ch.wiss.motoforumapi.security.JwtUtils;
import ch.wiss.motoforumapi.security.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

// Controller um user zu editieren, löschen und hohlen
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository ur;
    @Autowired
    private JwtUtils ju;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    AuthenticationManager authenticationManager;

    // Persönlicher User anhand des JWT Token identifizieren und dem frontend die passenden daten senden
    @GetMapping("/me")
    public ResponseEntity<?> getUserByToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = ju.getUserNameFromJwtToken(token);
        var user = ur.findByUsername(username);
        if (user.isPresent()) {
            return ResponseEntity
                    .ok()
                    .body(user);
        }

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("We could not validate your jwt token"));
    }

    // User anhand der ID im path identifizieren
    @GetMapping("/byId/{userId}")
    public ResponseEntity<?> getUser(HttpServletRequest request, @PathVariable Long userId) {
        try {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            String username = ju.getUserNameFromJwtToken(token);
            Optional<User> actor = ur.findByUsername(username);
            if (!actor.isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("We could not validate your JWT Token"));
            }
            Optional<User> userToFetch = ur.findById(userId);
            if (userToFetch.isPresent()) {
                if (userToFetch.get().equals(actor.get())) {
                    User user = userToFetch.get();
                    return ResponseEntity
                            .ok()
                            .body(user);
                }
                PublicUserDto publicUserAccount = new PublicUserDto(userToFetch.get().getId(),
                        userToFetch.get().getUsername(), userToFetch.get().getMotorcycle(),
                        userToFetch.get().getRoles());
                return ResponseEntity
                        .ok()
                        .body(publicUserAccount);
            }
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("We could no find the specified user in our database"));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponse("Something internal happened while processing your request"));
        }
    }

    // Update eines User properties
    @Transactional
    @PutMapping("/{userId}/{valueToUpdate}")
    public ResponseEntity<?> updateValue(HttpServletRequest request, @RequestBody String requestBody,
            @PathVariable("userId") Long id,
            @PathVariable("valueToUpdate") String valueToUpdate) {
        try {
            ObjectMapper om = new ObjectMapper();
            EditRequest editRequest = om.readValue(requestBody, EditRequest.class);
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            String actorUsername = ju.getUserNameFromJwtToken(token);
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
            if (!actor.get().isAdmin() && !actor.get().isModerator()) {
                if (!actor.get().equals(findUserToUpdate.get())) {
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
                    userToUpdate.setPassword(encoder.encode(editRequest.getProperty()));
                    ur.save(userToUpdate);
                    break;

                default:
                    break;
            }
            // Return new token
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userToUpdate.getUsername(), editRequest.getPw()));
            String jwt = ju.generateJwtToken(authentication);
            return ResponseEntity
                    .ok()
                    .body(jwt);
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body("Something went wrong while processing your request\nYou might need to login again with your new username: " + ex);
        }
    }

    // Delete User
    @Transactional
    @DeleteMapping("/delete/{userToDeleteId}")
    public ResponseEntity<?> deleteUser(HttpServletRequest request, @PathVariable Long userToDeleteId) {
        try {
            String token = request.getHeader("Authorization").replace("Bearer ", "");
            String username = ju.getUserNameFromJwtToken(token);
            Optional<User> actor = ur.findByUsername(username);
            if (actor.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("User not found"));
            }

            Optional<User> userToDelete = ur.findById(userToDeleteId);
            if (userToDelete.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("User to delete not found"));
            }

            System.out.println(actor.get().getRoles() + "\n" + userToDelete.get().getRoles());

            if (!actor.get().isAdmin() && !actor.get().isModerator()) {
                if (!actor.get().getPassword().equals(userToDelete.get().getPassword())
                        && !actor.get().getUsername().equals(userToDelete.get().getUsername()))
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("You are not authorized to delete this user"));
            }

            ur.delete(userToDelete.get());
            return ResponseEntity
                    .ok()
                    .body(new MessageResponse("User deleted successfully"));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(new MessageResponse("Error deleting reply: " + ex.getMessage()));
        }
    }
}
