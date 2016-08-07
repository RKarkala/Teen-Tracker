package slowwifi.com.drivingmonitorparent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by Robot on 8/6/2016.
 */
public class Confirm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);
        final String PREFS_NAME = "MyPrefsFile";
        final EditText user = (EditText)findViewById(R.id.user);
        final EditText pin = (EditText)findViewById(R.id.pin);
        final Button submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getText().toString().equals("Lizard") && pin.getText().toString().equals("1234")){
                    Intent i = new Intent(getApplicationContext(), MapsActivity.class);
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    settings.edit().putBoolean("my_first_time", false).commit();
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Child Credentials Not Found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
