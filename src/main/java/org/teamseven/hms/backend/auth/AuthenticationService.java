package org.teamseven.hms.backend.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.config.JwtService;
import org.teamseven.hms.backend.user.Role;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.UserRepository;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public HashMap<String, Object> register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PATIENT)
                .date_of_birth(request.getDate_of_birth())
                .address(request.getAddress())
                .type(request.getType())
                .gender(request.getGender())
                .phone(request.getPhone())
                .nric(request.getNric())
                .build();
        User userExists = repository.findByEmail(request.getEmail());
        if (userExists != null && userExists.getIsactive() == 1) {
           throw new IllegalStateException("User already exists!");
        }
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        HashMap<String, Object> response = new HashMap<>();
        response.put("token", AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build()
                .getToken());
        response.put("user", savedUser);
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail());
        if(user == null) {
            throw new IllegalStateException("User not found!");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
