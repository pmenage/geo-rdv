package android.tuto.com.rdv;

public class Meeting {

    private int id;
    private String latitude;
    private String longitude;
    private int senderID;
    private int receiverID;
    private String message;
    private String timestamp;

    public Meeting() {

    }

    public Meeting(String latitude, String longitude, String message, String timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Meeting(int id, String latitude, String longitude, String message, String timestamp) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
