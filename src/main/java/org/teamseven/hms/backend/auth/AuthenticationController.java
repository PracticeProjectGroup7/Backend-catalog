package org.teamseven.hms.backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.teamseven.hms.backend.shared.ResponseWrapper;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseWrapper> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(service.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(new ResponseWrapper.Success<>(service.authenticate(request)));
    }
}
