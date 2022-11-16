package com.example.whatsappclone1.userModel;

import android.net.Uri;

public class WhatsAppStatusModel {
    Uri statusImage;

    public WhatsAppStatusModel() {

    }
    public void setStatusImage(Uri statusImage) {
        this.statusImage = statusImage;
    }
    public WhatsAppStatusModel(Uri statusImage) {
        this.statusImage = statusImage;
    }

    public Uri getStatusImage() {
        return statusImage;
    }


}
