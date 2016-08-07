package slowwifi.com.drivingmonitorparent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.awareness.snapshot.internal.Snapshot;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList <Double> Lat = new ArrayList<Double>();
    ArrayList <Double> Long = new ArrayList<Double>();
    Firebase fb;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Firebase.setAndroidContext(this);
        fb = new Firebase("https://drivingmonitor-1470514516877.firebaseio.com/");
        Timer t = new Timer();
//Set the schedule function and rate

        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object temp = dataSnapshot.getValue();
                String k = temp.toString();
                String[] p = k.split("\\s");
                double lat = Double.parseDouble(p[0].substring(p[0].indexOf('=') + 1));
                double lon = Double.parseDouble(p[1].substring(0, p[1].length() - 1));
                if (!Lat.contains(lat) || !Long.contains(lon)){
                    Lat.add(lat);
                    Long.add(lon);
                    LatLng cur = new LatLng(Lat.get(counter), Long.get(counter));
                    int height = 150;
                    int width = 150;
                    BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.car);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                    if (counter > 0) {
                        long speed = Math.round(distance_on_geoid(Lat.get(counter - 1), Long.get(counter - 1), Lat.get(counter), Long.get(counter)));
                        long mph = Math.round((720 * speed) * 0.000621371);
                         Marker test = mMap.addMarker(new MarkerOptions()
                                 .position(cur)
                                 .title("Position #" + (counter + 1))
                                 .snippet("Speed : " + mph + " mph")
                                 .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                            test.showInfoWindow();


                    } else {
                        Marker test = mMap.addMarker(new MarkerOptions()
                                .position(cur)
                                .title("Position #" + (counter + 1))
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                        test.showInfoWindow();
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 10));
                    counter++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    protected void onStart(){
        super.onStart();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);

    }
    double distance_on_geoid(double lat1, double lon1, double lat2, double lon2) {

        // Convert degrees to radians
        lat1 = lat1 * Math.PI / 180.0;
        lon1 = lon1 * Math.PI / 180.0;

        lat2 = lat2 * Math.PI / 180.0;
        lon2 = lon2 * Math.PI / 180.0;

        // radius of earth in metres
        double r = 6378100;

        // P
        double rho1 = r * Math.cos(lat1);
        double z1 = r * Math.sin(lat1);
        double x1 = rho1 * Math.cos(lon1);
        double y1 = rho1 * Math.sin(lon1);

        // Q
        double rho2 = r * Math.cos(lat2);
        double z2 = r * Math.sin(lat2);
        double x2 = rho2 * Math.cos(lon2);
        double y2 = rho2 * Math.sin(lon2);

        // Dot product
        double dot = (x1 * x2 + y1 * y2 + z1 * z2);
        double cos_theta = dot / (r * r);

        double theta = Math.acos(cos_theta);

        // Distance in Metres
        return r * theta;
    }
}