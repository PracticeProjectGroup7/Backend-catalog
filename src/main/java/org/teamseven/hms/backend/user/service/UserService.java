package org.teamseven.hms.backend.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.shared.exception.ResourceNotFoundException;
import org.teamseven.hms.backend.user.UserRequest;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.teamseven.hms.backend.user.entity.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private PatientRepository patientRepository;
    @Autowired private DoctorRepository doctorRepository;
    @Autowired private StaffRepository staffRepository;

    public User getUserProfile(HttpServletRequest request) {
        var email = request.getAttribute("email");
        User user = userRepository.findByEmail((String) email);
        if(user == null) {
            throw new ResourceNotFoundException("User not found!");
        }
        return user;
    }

    public Patient updateUserProfile(
            HttpServletRequest request,
            UserRequest userRequest
    ) {
        User user = this.getUserProfile(request);
        Patient patient = patientRepository.findByUser(user);

        user = this.setUpdateFields(user, userRequest);

        if (userRequest.getBloodGroup() != null && !userRequest.getBloodGroup().isEmpty()) {
            patient.setBloodGroup(userRequest.getBloodGroup());
        }

        if (userRequest.getMedicalConditions() != null && !userRequest.getMedicalConditions().isEmpty()) {
            patient.setMedicalCondition(userRequest.getMedicalConditions());
        }

        userRepository.save(user);
        patientRepository.save(patient);
        return patient;
    }

    public User setUpdateFields(User user, UserRequest userRequest) {
        if (userRequest.getFirstName() != null && !userRequest.getFirstName().isEmpty()) {
            user.setFirstName(userRequest.getFirstName());
        }

        if (userRequest.getLastName() != null && !userRequest.getLastName().isEmpty()) {
            user.setLastName(userRequest.getLastName());
        }

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        if (userRequest.getGender() != null && !userRequest.getGender().isEmpty()) {
            user.setGender(userRequest.getGender());
        }

        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
            user.setEmail(userRequest.getEmail());
        }

        if (userRequest.getNric() != null && !userRequest.getNric().isEmpty()) {
            user.setNric(userRequest.getNric());
        }

        if (userRequest.getAddress() != null && !userRequest.getAddress().isEmpty()) {
            user.setAddress(userRequest.getAddress());
        }

        if (userRequest.getDateOfBirth() != null && !userRequest.getDateOfBirth().isEmpty()) {
            user.setDateOfBirth(userRequest.getDateOfBirth());
        }

        if (userRequest.getPhone() != null && !userRequest.getPhone().isEmpty()) {
            user.setPhone(userRequest.getPhone());
        }

        return user;
    }

    public Object getProfile(HttpServletRequest request) {
        var roleId = request.getAttribute("roleId");

        var ROLE = String.valueOf(request.getAttribute("ROLE"));

        if(Objects.equals(ROLE, "PATIENT")) {
            Optional<Patient> patient = patientRepository.findByPatientId((String) roleId);
            if(patient.isEmpty()) {
                throw new ResourceNotFoundException("Patient not found!");
            }
            return patient.get();
        }

        if(Objects.equals(ROLE, "DOCTOR")) {
            Optional<Doctor> doctor = doctorRepository.findByDoctorId(UUID.fromString((String) roleId));
            if(doctor.isEmpty()) {
                throw new ResourceNotFoundException("Doctor not found!");
            }
            return doctor.get();
        }

        if(Objects.equals(ROLE, "STAFF") || Objects.equals(ROLE, "LAB_SUPPORT_STAFF")) {
            User staffUser = this.getUserProfile(request);
            Optional<Staff> staff = staffRepository.findByUser(staffUser);
            if(staff.isEmpty()) {
                throw new ResourceNotFoundException("Staff not found!");
            }
            return staff.get();
        }

        return null;
    }
}
