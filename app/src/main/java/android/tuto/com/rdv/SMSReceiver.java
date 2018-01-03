package android.tuto.com.rdv;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            // PDU : "protocol data unit" -> This is the industrial standard for SMS message.
            // All the SMS data will be bundled in a PDU
            Object[] pdus = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");

            final SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                String senderPhoneNo = messages[i].getDisplayOriginatingAddress();
                Toast.makeText(context, "Message: " + messages[0].getMessageBody() + ", from " + senderPhoneNo, Toast.LENGTH_LONG).show();

                // TODO : Prevent message from appearing in SMS ?

                if (isResponseToMeeting(messages[0].getMessageBody())) {
                    startResponseToMeetingDialog(context, senderPhoneNo,  messages[0].getMessageBody());
                } else {
                    startIncomingMeetingDialog(context, senderPhoneNo, messages[0].getMessageBody());
                }

            }
        }
    }

    private void startIncomingMeetingDialog(Context context, String phoneNumber, String message) {

        DatabaseHandler db = new DatabaseHandler(context);

        Pattern pattern = Pattern.compile("Latitude:(.*)Longitude:");
        Matcher matcher = pattern.matcher(message);
        String latitude = "0";
        if (matcher.find()) {
            latitude = matcher.group(1);
        }

        Pattern pattern2 = Pattern.compile("Longitude:(.*)");
        Matcher matcher2 = pattern2.matcher(message);
        String longitude = "0";
        if (matcher2.find()) {
            longitude = matcher2.group(1);
        }

        db.addMeeting(new Meeting(latitude, longitude, message, (new Date()).toString()));

        Intent intent = new Intent(context, SMSReceivedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("message", message);
        context.startActivity(intent);

    }

    private boolean isResponseToMeeting(String message) {
        return message.contains("Response:");
    }

    private void startResponseToMeetingDialog(Context context, String phoneNumber, String message) {

        DatabaseHandler db = new DatabaseHandler(context);
        User userSender = db.getUserByPhoneNumber(phoneNumber);

        if (message.contains("Accepted")) {
            db.addNotification(new Notification(userSender.getName() + " accepted your invitation", (new Date()).toString()));
        } else {
            db.addNotification(new Notification(userSender.getName() + " declined your invitation", (new Date()).toString()));
        }

        Intent intent = new Intent(context, MainActivity.class);;
        context.startActivity(intent);

    }

}
