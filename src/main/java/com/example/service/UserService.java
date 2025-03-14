package com.example.service;

import com.example.domain.VerificationType;
import com.example.modal.User;

public interface UserService {

    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserProfileByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(
            VerificationType verificationType,
            String sentTo,
            User user
    );

    User updatePassword(
            User user,
            String newPassword
    );
}
