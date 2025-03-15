package com.example.service;

import com.example.modal.Coin;
import com.example.modal.User;
import com.example.modal.Watchlist;

public interface WatchlistService {
    Watchlist findUserWatchlist(Long UserId) throws Exception;
    Watchlist createWatchlist(User user);
    Watchlist findById(Long id) throws Exception;
    Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}
