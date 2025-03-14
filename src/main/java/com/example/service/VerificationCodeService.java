package com.example.service;

import com.example.domain.VerificationType;
import com.example.modal.User;
import com.example.modal.VerificationCode;

public interface VerificationCodeService {
     VerificationCode sendVerificationCode(User user, VerificationType verificationType);
     VerificationCode getVerificationCodeById(Long id) throws Exception;
     VerificationCode getVerificationCodeByUser(Long userId);
     void deleteVerificationCodeById(VerificationCode verificationCode);
}
