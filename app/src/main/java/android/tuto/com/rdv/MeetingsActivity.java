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

public class MeetingsActivity extends AppCompatActivity {

    private static MeetingsActivity activity;
    private ArrayList<String> meetingList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView listView;

    public static MeetingsActivity instance() {
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        listView = (ListView) findViewById(R.id.meetingsList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meetingList);
        listView.setAdapter(adapter);

        DatabaseHandler db = new DatabaseHandler(this);

        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();

        List<Meeting> meetings = db.getAllMeetingsByPhoneNumber(mPhoneNumber);

        if (meetings != null) {
            for (Meeting meeting : meetings) {
                adapter.insert(meeting.getTimestamp() + ": " + meeting.getMessage(), 0);
            }
            adapter.notifyDataSetChanged();
        }

        // TODO : put button under each meeting

    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
