package com.example.service;

import com.example.domain.VerificationType;
import com.example.modal.ForgotPasswordToken;
import com.example.modal.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(User user,
                                    String id,
                                    String otp,
                                    VerificationType verificationType,
                                    String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
