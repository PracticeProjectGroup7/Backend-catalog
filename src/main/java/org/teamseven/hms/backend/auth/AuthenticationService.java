package org.teamseven.hms.backend.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.config.JwtService;
import org.teamseven.hms.backend.shared.exception.ResourceNotFoundException;
import org.teamseven.hms.backend.shared.exception.UnauthorizedAccessException;
import org.teamseven.hms.backend.user.Role;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.entity.DoctorRepository;
import org.teamseven.hms.backend.user.entity.Patient;
import org.teamseven.hms.backend.user.UserRepository;
import org.teamseven.hms.backend.user.entity.PatientRepository;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Transactional
    public HashMap<String, Object> register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PATIENT)
                .dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress())
                .type(request.getType())
                .gender(request.getGender())
                .phone(request.getPhone())
                .nric(request.getNric())
                .build();
        User userExists = userRepository.findByEmail(request.getEmail());
        if (userExists != null) {
           throw new IllegalArgumentException("User already exists!");
        }
        var savedUser = userRepository.save(user);
        var patient = Patient.builder()
                .user(savedUser)
                .bloodGroup(request.getBloodGroup())
                .medicalCondition(request.getMedicalCondition())
                .build();
        var savedPatient = patientRepository.save(patient);
        var jwtToken = jwtService.generateToken(
                savedUser,
                savedUser.getUserId(),
                Role.PATIENT,
                savedPatient.getPatientId(),
                savedUser.getName()
        );
        HashMap<String, Object> response = new HashMap<>();
        response.put("token", AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build()
                .getToken());
        response.put("patient", savedPatient);
        return response;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail());
        if(user == null) {
            throw new ResourceNotFoundException("User not found!");
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedAccessException();
        }

        UUID genericRoleId = null;
        if(user.getRole() == Role.DOCTOR) {
            var doctor = doctorRepository.findByUserId(user.getUserId()).orElseThrow(NoSuchElementException::new);
            genericRoleId = doctor.getDoctorId();
        } else if (user.getRole() == Role.PATIENT) {
            var patient = patientRepository.findByUser(user);
            genericRoleId = patient.getPatientId();
        }
        var jwtToken = jwtService.generateToken(
                user,
                user.getUserId(),
                user.getRole(),
                genericRoleId,
                user.getName()
        );
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
