package android.tuto.com.rdv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private static NotificationsActivity activity;
    private ArrayList<String> notificationList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listView;

    public static NotificationsActivity instance() {
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        listView = (ListView) findViewById(R.id.notificationsList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, notificationList);
        listView.setAdapter(adapter);

        DatabaseHandler db = new DatabaseHandler(this);
        List<User> users = db.getAllUsers();

        for (User user : users) {
            adapter.insert(user.getName(), 0);
        }
        adapter.notifyDataSetChanged();

    }

}
