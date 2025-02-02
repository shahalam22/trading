package com.example.modal;

import com.example.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean inEnabled = false;
    private VerificationType sendTo;

    public boolean isInEnabled() {
        return inEnabled;
    }

    public void setInEnabled(boolean inEnabled) {
        this.inEnabled = inEnabled;
    }

    public VerificationType getSendTo() {
        return sendTo;
    }

    public void setSendTo(VerificationType sendTo) {
        this.sendTo = sendTo;
    }
}
