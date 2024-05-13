package ch.wiss.motoforumapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller um zugriff auf die Public und Private Endpoints zu testen
@RestController
public class PublicController {

    @GetMapping("/public")
    public ResponseEntity<String> getPublicPart() {
        return ResponseEntity.ok("Dies ist der Public Teil ");
    }
}
