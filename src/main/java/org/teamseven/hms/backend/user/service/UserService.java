package org.teamseven.hms.backend.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.teamseven.hms.backend.config.JwtService;
import org.teamseven.hms.backend.user.UserRequest;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.UserRepository;

@Service
public class UserService {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;

    public User getUserProfile(HttpServletRequest request) {
        var email = request.getAttribute("email");
        User user = userRepository.findByEmail((String) email);
        if(user == null) {
            throw new IllegalStateException("User not found!");
        }
        return user;
    }

    public User updateUserProfile(
            HttpServletRequest request,
            UserRequest userRequest
    ) {
        User user = this.getUserProfile(request);

        if (userRequest.getFirstName() != null && !userRequest.getFirstName().isEmpty()) {
            user.setFirstName(userRequest.getFirstName());
        }

        if (userRequest.getLastName() != null && !userRequest.getLastName().isEmpty()) {
            user.setLastName(userRequest.getLastName());
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
        System.out.println("end here");

        userRepository.save(user);
        return user;
    }
}
