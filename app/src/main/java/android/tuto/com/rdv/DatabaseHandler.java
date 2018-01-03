package android.tuto.com.rdv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rdvManager";

    private static final String TABLE_USERS = "users";
    private static final String KEY_ID_USERS = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE_NUMBER = "phone_number";

    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String KEY_ID_NOTIF = "id";
    private static final String KEY_MESSAGE_NOTIF = "message";
    private static final String KEY_SENDER_NOTIF = "sender_id";
    private static final String KEY_RECEIVER_NOTIF = "receiver_id";
    private static final String KEY_TIMESTAMP_NOTIF = "timestamp";

    private static final String TABLE_MEETINGS = "meetings";
    private static final String KEY_ID_MEETINGS = "id";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_SENDER_MEETINGS = "sender_id";
    private static final String KEY_RECEIVER_MEETINGS = "receiver_id";
    private static final String KEY_MESSAGE_MEETINGS = "message";
    private static final String KEY_TIMESTAMP_MEETINGS = "timestamp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create all tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID_USERS + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                + KEY_ID_NOTIF + " INTEGER PRIMARY KEY,"
                + KEY_MESSAGE_NOTIF + " TEXT,"
                + KEY_SENDER_NOTIF + " INTEGER,"
                + KEY_RECEIVER_NOTIF + " INTEGER,"
                + KEY_TIMESTAMP_NOTIF + " TEXT" + ")";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);

        String CREATE_MEETINGS_TABLE = "CREATE TABLE " + TABLE_MEETINGS + "("
                + KEY_ID_MEETINGS + " INTEGER PRIMARY KEY,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT,"
                + KEY_SENDER_MEETINGS + " INTEGER,"
                + KEY_RECEIVER_MEETINGS + " INTEGER,"
                + KEY_MESSAGE_MEETINGS + " TEXT,"
                + KEY_TIMESTAMP_MEETINGS + " TEXT" + ")";
        db.execSQL(CREATE_MEETINGS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEETINGS);
        onCreate(db);

    }

    /* USERS */

    // All CRUD operations : Create, Read, Update, Delete

    public void addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PHONE_NUMBER, user.getPhoneNumber());

        db.insert(TABLE_USERS, null, values);
        db.close();

    }

    public User getUser(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {
                        KEY_ID_USERS, KEY_NAME, KEY_PHONE_NUMBER}, KEY_ID_USERS + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return user;

    }

    public User getUserByPhoneNumber(String phoneNumber) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {
                        KEY_ID_USERS, KEY_NAME, KEY_PHONE_NUMBER}, KEY_PHONE_NUMBER + "=?",
                new String[] { phoneNumber }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
            return user;
        }

        return null;

    }

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<User>();
        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setPhoneNumber(cursor.getString(2));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        return userList;

    }

    public int getUsersCount() {

        String countQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();

    }

    public int updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PHONE_NUMBER, user.getPhoneNumber());
        return db.update(TABLE_USERS, values, KEY_ID_USERS + " = ?",
                new String[] { String.valueOf(user.getId()) });

    }

    public void deleteUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_ID_USERS + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();

    }

    /* NOTIFICATIONS */

    public void addNotification(Notification notification) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_NOTIF, notification.getMessage());
        values.put(KEY_SENDER_NOTIF, notification.getSenderID());
        values.put(KEY_RECEIVER_NOTIF, notification.getReceiverID());
        values.put(KEY_TIMESTAMP_NOTIF, notification.getTimestamp());

        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();

    }

    public Notification getNotification(int id)  {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTIFICATIONS, new String[] {
                KEY_ID_NOTIF, KEY_MESSAGE_NOTIF, KEY_SENDER_NOTIF, KEY_RECEIVER_NOTIF, KEY_TIMESTAMP_NOTIF
        }, KEY_RECEIVER_NOTIF + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Notification notification = new Notification(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                cursor.getString(4));
        return notification;

    }

    public List<Notification> getAllNotificationsByPhoneNumber(String phoneNumber) {

        List<Notification> notificationList = new ArrayList<>();

        User user = getUserByPhoneNumber(phoneNumber);

        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATIONS + " WHERE " + KEY_RECEIVER_NOTIF + " = " + user.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification();
                notification.setId(Integer.parseInt(cursor.getString(0)));
                notification.setMessage(cursor.getString(1));
                notification.setSenderID(Integer.parseInt(cursor.getString(2)));
                notification.setReceiverID(Integer.parseInt(cursor.getString(3)));
                notification.setTimestamp(cursor.getString(4));
                notificationList.add(notification);
            } while (cursor.moveToNext());
        }

        return notificationList;

    }

    /* MEETINGS */

    public void addMeeting(Meeting meeting) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LATITUDE, meeting.getLatitude());
        values.put(KEY_LONGITUDE, meeting.getLongitude());
        values.put(KEY_SENDER_MEETINGS, meeting.getSenderID());
        values.put(KEY_RECEIVER_MEETINGS, meeting.getReceiverID());
        values.put(KEY_MESSAGE_MEETINGS, meeting.getMessage());
        values.put(KEY_TIMESTAMP_MEETINGS, meeting.getTimestamp());

        db.insert(TABLE_MEETINGS, null, values);
        db.close();

    }

    public Meeting getMeetingByReceiverID(int id)  {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEETINGS, new String[] {
                        KEY_ID_MEETINGS, KEY_LATITUDE, KEY_LONGITUDE, KEY_SENDER_MEETINGS, KEY_RECEIVER_MEETINGS, KEY_MESSAGE_MEETINGS, KEY_TIMESTAMP_MEETINGS
                }, KEY_RECEIVER_MEETINGS + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Meeting meeting = new Meeting(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                cursor.getString(5),
                cursor.getString(6));
        return meeting;

    }

    public List<Meeting> getAllMeetingsByPhoneNumber(String phoneNumber) {

        List<Meeting> meetingList = new ArrayList<>();

        User user = getUserByPhoneNumber(phoneNumber);

        String selectQuery = "SELECT * FROM " + TABLE_MEETINGS + " WHERE " + KEY_RECEIVER_MEETINGS + " = " + user.getId();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Meeting meeting = new Meeting();
                meeting.setId(Integer.parseInt(cursor.getString(0)));
                meeting.setLatitude(cursor.getString(1));
                meeting.setLongitude(cursor.getString(2));
                meeting.setSenderID(Integer.parseInt(cursor.getString(3)));
                meeting.setReceiverID(Integer.parseInt(cursor.getString(4)));
                meeting.setMessage(cursor.getString(5));
                meeting.setTimestamp(cursor.getString(6));
                meetingList.add(meeting);
            } while (cursor.moveToNext());
        }

        return meetingList;

    }

}
