package slowwifi.com.drivingmonitorkid;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Robot on 8/6/2016.
 */
public class MainScreen extends Activity {
    Firebase db;
    LocationManager lm;
    double latitude, longitude;
   // Location location;
   LocationListener locationListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        Firebase.setAndroidContext(this);
        Toast.makeText(this, "Data Collection Started", Toast.LENGTH_LONG).show();
        db = new Firebase("https://drivingmonitor-1470514516877.firebaseio.com");
        doit();






    }
    public void doit() {

        Timer t = new Timer();
//Set the schedule function and rate

            t.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {
                                          try {
                                              LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                              Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                              longitude = location.getLongitude();
                                              latitude = location.getLatitude();
                                              Log.e("Lat and Long", latitude + " " + longitude);
                                              db.child("lat").setValue(latitude);
                                              db.child("long").setValue(longitude);
                                              db.child("latlng").setValue(latitude + " " + longitude);
                                          }catch (Exception e){
                                              Log.e("OK", e.toString());
                                              doit();
                                          }
                                      }

                                  },
//Set how long before to start calling the TimerTask (in milliseconds)
                    0,
//Set the amount of time between each execution (in milliseconds)
                    3000);

    }
    }


