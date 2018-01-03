package android.tuto.com.rdv;

import java.util.Date;

public class Notification {

    private int id;
    private String message;
    private String timestamp;

    public Notification() {

    }

    public Notification(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public Notification(int id, String message, String timestamp) {
        this.id = id;
        this.message = message;
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


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
