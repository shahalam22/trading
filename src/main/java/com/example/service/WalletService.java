package com.example.service;

import com.example.modal.Order;
import com.example.modal.User;
import com.example.modal.Wallet;

public interface WalletService {
    Wallet getUserWallet (User user);
    Wallet addBalance (Wallet wallet, Long money);
    Wallet findWalletById(Long id) throws Exception;
    Wallet walletToWalletTransfer(User user, Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order, User user) throws Exception;
}
