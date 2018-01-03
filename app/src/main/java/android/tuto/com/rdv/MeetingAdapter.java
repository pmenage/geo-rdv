package android.tuto.com.rdv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeetingAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public MeetingAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.meeting_item, null);
        }

        TextView textView = view.findViewById(R.id.list_item_string);
        String message = list.get(position);
        Pattern pattern = Pattern.compile("(.*)Latitude:.*");
        Matcher matcher = pattern.matcher(message);
        String viewMessage = "No message was found";
        if (matcher.find()) {
            viewMessage = matcher.group(1);
        }
        textView.setText(viewMessage);

        DatabaseHandler db = new DatabaseHandler(context);
        final Meeting meeting = db.getMeetingByMessage(message);

        Button button = view.findViewById(R.id.see_map_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoogleMapActivity.class);
                intent.putExtra("latitude", Double.parseDouble(meeting.getLatitude()));
                intent.putExtra("longitude", Double.parseDouble(meeting.getLongitude()));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
