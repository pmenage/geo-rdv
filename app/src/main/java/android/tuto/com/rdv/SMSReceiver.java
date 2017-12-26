package android.tuto.com.rdv;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");

            final SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                // TODO : enlever la vérification de version d'API et voir si ça marche quand même
                // (surtout que la fonction createFromPdu est deprecated s'il manque un argument
                String senderPhoneNo = messages[i].getDisplayOriginatingAddress();
                Toast.makeText(context, "Message: " + messages[0].getMessageBody() + ", from " + senderPhoneNo, Toast.LENGTH_LONG).show();

                // TODO : Show message directly in the map - popup
                // TODO : Prevent message from appearing in SMS ?
                // TODO : Get contact from phone number if contact ?

                if (isIncomingMeeting()) {
                    startIncomingMeetingDialog();
                }

                if (isResponseToMeeting()) {
                    startResponseToMeetingDialog();
                }

            }
        }
    }

    private boolean isIncomingMeeting() {
        // TODO : find a way to filter messages
        return true;
    }

    private void startIncomingMeetingDialog() {
        PopupWindow popupWindow = new PopupWindow();
    }

    private boolean isResponseToMeeting() {
        // TODO : find a way to filter messages
        return false;
    }

    private void startResponseToMeetingDialog() {
        // TODO : Show response in a popup
        // Daniel accepted your meeting
        // Daniel declined your meeting
    }

}
