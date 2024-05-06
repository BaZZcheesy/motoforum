package ch.wiss.motoforumapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivateController {

    @GetMapping("private")
    public ResponseEntity<String> getPrivatePart() {
        return ResponseEntity.ok("Dies ist der Private Teil ");
    }
}
