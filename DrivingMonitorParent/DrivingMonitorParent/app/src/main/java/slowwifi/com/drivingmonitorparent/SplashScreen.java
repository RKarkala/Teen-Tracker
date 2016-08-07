package slowwifi.com.drivingmonitorparent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        final String PREFS_NAME = "MyPrefsFile";

        new Handler().postDelayed(new Runnable() {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            @Override
            public void run() {
                    if (settings.getBoolean("my_first_time", true)) {
                        Intent main = new Intent(SplashScreen.this, Confirm.class);
                        startActivity(main);
                        finish();

                }
                else{
                        Intent user = new Intent(SplashScreen.this, MapsActivity.class);
                        startActivity(user);
                        finish();
                }

            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
