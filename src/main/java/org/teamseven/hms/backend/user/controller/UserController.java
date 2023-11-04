package org.teamseven.hms.backend.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.teamseven.hms.backend.shared.ResponseWrapper;
import org.teamseven.hms.backend.user.UserRequest;
import org.teamseven.hms.backend.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user/profile")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<ResponseWrapper> getUserProfile(
            @NonNull HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(userService.getProfile(request))
        );
    }

    @PatchMapping()
    public ResponseEntity<ResponseWrapper> updateUserProfile(
            @NonNull HttpServletRequest request,
            @RequestBody UserRequest userRequest
            ) {
        return ResponseEntity.ok(
                new ResponseWrapper.Success<>(userService.updateUserProfile(request, userRequest))
        );
    }
}
