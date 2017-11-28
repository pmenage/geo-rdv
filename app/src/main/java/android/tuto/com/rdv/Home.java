package android.tuto.com.rdv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void getList(View v){

        Intent intent = new Intent(this,MyListActivity.class);
        startActivity(intent);
    }

   /* public void addContact(View v){
        Intent intent = new Intent(this,AddContactActivity.class);
        startActivity(intent);

    }*/
}
