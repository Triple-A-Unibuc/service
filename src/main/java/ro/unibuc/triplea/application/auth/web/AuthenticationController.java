package ro.unibuc.triplea.application.auth.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.unibuc.triplea.application.auth.dto.request.AuthenticationRequest;
import ro.unibuc.triplea.application.auth.dto.request.RegisterRequest;
import ro.unibuc.triplea.application.auth.dto.response.AuthenticationResponse;
import ro.unibuc.triplea.application.auth.dto.response.StandardResponse;
import ro.unibuc.triplea.domain.auth.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth management APIs")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request) {
        logger.info("Received registration request for user");
        return new ResponseEntity(new StandardResponse("200", "Done", service.register(request)), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        logger.info("Received authentication request");
        return new ResponseEntity(new StandardResponse("200", "Done", service.authenticate(request)), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Received refresh token request");
        service.refreshToken(request, response);
    }

}
