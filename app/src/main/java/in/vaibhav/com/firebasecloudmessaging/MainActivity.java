package in.vaibhav.com.firebasecloudmessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private BroadcastReceiver broadcastReceiver;
    private Button button;
    private String keyFromuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.uniqNo);
        button = (Button) findViewById(R.id.submtBtn);
        textView = (TextView) findViewById(R.id.text);

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("FCM");

        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                textView.setText(SharedPrefManager.getInstance(MainActivity.this).getToken());

            }
        };

        if (SharedPrefManager.getInstance(this).getToken() != null) {


            textView.setText(SharedPrefManager.getInstance(this).getToken());

        }
        registerReceiver(broadcastReceiver, new IntentFilter(MyfirebaseInstanceServices.TOKEN_BROADCAST));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyFromuser = String.valueOf(editText.getText());
                database.child(keyFromuser).setValue(SharedPrefManager.getInstance(MainActivity.this).getToken());
            }
        });


    }


}
