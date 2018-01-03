package android.tuto.com.rdv;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeetingsActivity extends AppCompatActivity {

    // TODO : Meetings are in the wrong order, put the most recent first

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);

        ArrayList<String> list = new ArrayList<String>();
        DatabaseHandler db = new DatabaseHandler(this);
        List<Meeting> meetings = db.getAllMeetings();

        if (meetings != null) {
            for (Meeting meeting : meetings) {
                list.add(meeting.getMessage());
            }
        }

        MeetingAdapter adapter = new MeetingAdapter(list, this);
        ListView listView = (ListView) findViewById(R.id.meetingsList);
        listView.setAdapter(adapter);

    }

    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
