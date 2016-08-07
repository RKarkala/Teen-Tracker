package slowwifi.com.drivingmonitorkid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.HttpURLConnection;


/**
 * Created by Robot on 8/6/2016.
 */
public class Username extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username);
        final String PREFS_NAME = "MyPrefsFile";
        final EditText username = (EditText)findViewById(R.id.user);
        final EditText pin = (EditText)findViewById(R.id.pin);
        final Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = username.getText().toString();
                int num = Integer.parseInt(pin.getText().toString());
                Intent i = new Intent(Username.this, MainScreen.class);
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                settings.edit().putBoolean("my_first_time", false).commit();
                startActivity(i);
                finish();
            }
        });
    }

}
