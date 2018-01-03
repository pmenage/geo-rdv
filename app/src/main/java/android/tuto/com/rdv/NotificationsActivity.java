package android.tuto.com.rdv;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private ArrayList<String> notificationList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        listView = (ListView) findViewById(R.id.notificationsList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notificationList);
        listView.setAdapter(adapter);

        DatabaseHandler db = new DatabaseHandler(this);

        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        List<Notification> notifications = db.getAllNotificationsByPhoneNumber(mPhoneNumber);

        if (notifications != null) {
            for (Notification notification : notifications) {
                adapter.insert(notification.getTimestamp() + ": " + notification.getMessage(), 0);
            }
            adapter.notifyDataSetChanged();
        }

    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
