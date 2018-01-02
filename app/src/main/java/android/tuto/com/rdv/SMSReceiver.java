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
                // TODO : enlever la vérification de version d'API et voir si ça marche quand même
                // (surtout que la fonction createFromPdu est deprecated s'il manque un argument
                String senderPhoneNo = messages[i].getDisplayOriginatingAddress();
                Toast.makeText(context, "Message: " + messages[0].getMessageBody() + ", from " + senderPhoneNo, Toast.LENGTH_LONG).show();

                // TODO : Show message directly in the map - popup
                // TODO : Prevent message from appearing in SMS ?
                // TODO : Get contact from phone number if contact ?

                if (isResponseToMeeting(messages[0].getMessageBody())) {
                    startResponseToMeetingDialog(senderPhoneNo, messages[0].getMessageBody());
                } else {
                    startIncomingMeetingDialog(context, senderPhoneNo, messages[0].getMessageBody());
                }

            }
        }
    }

    private boolean isIncomingMeeting() {
        // TODO : find a way to filter messages
        // IDEA : pour chaque demande de rdv, mettre dans la base un booléen disant si c'est la réception
        // d'une réponse ou pas
        return true;
    }

    private void startIncomingMeetingDialog(Context context, String phoneNumber, String message) {
        Intent intent = new Intent(context, SMSReceivedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("message", message);
        context.startActivity(intent);
    }

    private boolean isResponseToMeeting(String message) {
        return message.contains("Response:");
    }

    private void startResponseToMeetingDialog(String phoneNumber, String message) {
        NotificationsActivity instance = NotificationsActivity.instance();
        // Pas grave si ça plante car on va changer et mettre dans la base de données
        if (message.contains("Accepted")) {
            // Todo : replace phone number by name of person (with database)
            instance.updateList(phoneNumber + " accepted your invitation");
        } else {
            // Todo : replace phone number by name of person (with database)
            instance.updateList(phoneNumber + " declinde your invitation");
        }
    }

}
