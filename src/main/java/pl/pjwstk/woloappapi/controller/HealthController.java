package pl.pjwstk.woloappapi.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health check")
@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> checkHealth() {

        return new ResponseEntity<>("App is healthy", HttpStatus.OK);
    }
}
