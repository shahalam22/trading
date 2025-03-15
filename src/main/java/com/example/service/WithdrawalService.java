package com.example.service;

import com.example.modal.User;
import com.example.modal.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(Long amount, User user);
    Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception;
    List<Withdrawal> getUsersWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequests();
}
