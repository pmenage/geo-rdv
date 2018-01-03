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
    private static final String KEY_TIMESTAMP_NOTIF = "timestamp";

    private static final String TABLE_MEETINGS = "meetings";
    private static final String KEY_ID_MEETINGS = "id";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
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
                + KEY_TIMESTAMP_NOTIF + " TEXT" + ")";
        db.execSQL(CREATE_NOTIFICATIONS_TABLE);

        String CREATE_MEETINGS_TABLE = "CREATE TABLE " + TABLE_MEETINGS + "("
                + KEY_ID_MEETINGS + " INTEGER PRIMARY KEY,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT,"
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

        return new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

    }

    public User getUserByPhoneNumber(String phoneNumber) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] {
                        KEY_ID_USERS, KEY_NAME, KEY_PHONE_NUMBER}, KEY_PHONE_NUMBER + "=?",
                new String[] { phoneNumber }, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            return new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
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
        values.put(KEY_TIMESTAMP_NOTIF, notification.getTimestamp());

        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();

    }

    public Notification getNotification(int id)  {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTIFICATIONS, new String[] {
                KEY_ID_NOTIF, KEY_MESSAGE_NOTIF, KEY_TIMESTAMP_NOTIF
        }, KEY_ID_NOTIF + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return new Notification(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2));

    }

    public List<Notification> getAllNotifications() {

        List<Notification> notificationList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification();
                notification.setId(Integer.parseInt(cursor.getString(0)));
                notification.setMessage(cursor.getString(1));
                notification.setTimestamp(cursor.getString(2));
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
        values.put(KEY_MESSAGE_MEETINGS, meeting.getMessage());
        values.put(KEY_TIMESTAMP_MEETINGS, meeting.getTimestamp());

        db.insert(TABLE_MEETINGS, null, values);
        db.close();

    }

    public Meeting getMeetingByReceiverID(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEETINGS, new String[] {
                        KEY_ID_MEETINGS, KEY_LATITUDE, KEY_LONGITUDE, KEY_MESSAGE_MEETINGS, KEY_TIMESTAMP_MEETINGS
                }, KEY_ID_MEETINGS + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return new Meeting(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

    }

    public Meeting getMeetingByMessage(String message) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MEETINGS, new String[] {
                        KEY_ID_MEETINGS, KEY_LATITUDE, KEY_LONGITUDE, KEY_MESSAGE_MEETINGS, KEY_TIMESTAMP_MEETINGS
                }, KEY_MESSAGE_MEETINGS + "=?",
                new String[] { message }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return new Meeting(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

    }

    public List<Meeting> getAllMeetings() {

        List<Meeting> meetingList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_MEETINGS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Meeting meeting = new Meeting();
                meeting.setId(Integer.parseInt(cursor.getString(0)));
                meeting.setLatitude(cursor.getString(1));
                meeting.setLongitude(cursor.getString(2));
                meeting.setMessage(cursor.getString(3));
                meeting.setTimestamp(cursor.getString(4));
                meetingList.add(meeting);
            } while (cursor.moveToNext());
        }

        return meetingList;

    }

}
