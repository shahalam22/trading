package com.example.response;

import com.example.modal.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse implements Serializable {
    private String jwt;
    private boolean status;
    private String message;
    private boolean isTwoFactorAuthEnabled;

    public void setSession(String session) {
        this.session = session;
    }

    public void setTwoFactorAuthEnabled(boolean twoFactorAuthEnabled) {
        isTwoFactorAuthEnabled = twoFactorAuthEnabled;
    }

    private String session;
    private User userData;

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public User getUserData() {
        return this.userData;
    }

    public String getJwt() {
        return jwt;
    }

    public String getMessage() {
        return message;
    }

    public String getSession() {
        return session;
    }

    public boolean isStatus() {
        return status;
    }

    public boolean isTwoFactorAuthEnabled() {
        return isTwoFactorAuthEnabled;
    }
}
