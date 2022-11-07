package com.example.whatsappclone1.userModel;

public class MessageDb {
    String message;
    String senderPhoneNumber;
    long timestamp;
    String currenttime;

    public MessageDb() {
    }

    public MessageDb(String message, String senderPhoneNumber, long timestamp, String currenttime) {
        this.message = message;
        this.senderPhoneNumber = senderPhoneNumber;
        this.timestamp = timestamp;
        this.currenttime = currenttime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public void setCurrenttime(String currenttime) {
        this.currenttime = currenttime;
    }
}
