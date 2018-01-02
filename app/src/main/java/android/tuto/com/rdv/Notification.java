package android.tuto.com.rdv;

import java.util.Date;

public class Notification {

    private int id;
    private String message;
    private int senderID;
    private int receiverID;
    private String timestamp;

    public Notification() {

    }

    public Notification(String message, int senderID, int receiverID, String timestamp) {
        this.message = message;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.timestamp = timestamp;
    }

    public Notification(int id, String message, int senderID, int receiverID, String timestamp) {
        this.id = id;
        this.message = message;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
