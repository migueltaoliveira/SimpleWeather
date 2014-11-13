package uc.pgi.coimbraweather;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import utils.Temperature;


public class WeatherActivity extends Activity {
    @InjectView(R.id.header) TextView header;
    @InjectView(R.id.imageView) ImageView imageView;
    @InjectView(R.id.label) TextView label;

    private String TAG = getClass().getName();
    private String url = "https://aqueous-chamber-4634.herokuapp.com";
    private LocationManager mLocationManager;

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            Log.i(TAG, "Latitude: " + location.getLatitude() + ", Longitude:" + location.getLongitude());

            stopCheckingLocation();
            updateInformation(location.getLatitude(), location.getLongitude());
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.inject(this);

        // Check http://developer.android.com/guide/topics/location/strategies.html
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // or: LocationManager.GPS_PROVIDER
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                0, locationListener);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
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

    private void updateInformation(double latitude, double longitude) {
        Ion.getDefault(this).configure().setGson(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create());

        ResponseFuture<Temperature> json = Ion.with(this)
                .load(url + "/meteo?lat=" + latitude + "&lng=" + longitude)
                .as(new TypeToken<Temperature>() {
                });

        try {
            Log.i("Result", "" + json.get().toString());
            Ion.with(this)
                    .load(url + "/static/"+json.get().icon+".png")
                    .withBitmap()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .intoImageView(imageView);

            label.setText(json.get().summary);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /*
     * The logic of deciding when new fixes are no longer necessary might range from
     * very simple to very complex depending on your application. A short gap between
     * when the location is acquired and when the location is used, improves the accuracy
     * of the estimate. Always beware that listening for a long time consumes a lot of battery
     * power, so as soon as you have the information you need, you should stop listening for updates
     *
     * Check: http://developer.android.com/guide/topics/location/strategies.html#StopListening
     *  */

    private void stopCheckingLocation() {
        if (mLocationManager != null) {
            // Remove the listener you previously added
            mLocationManager.removeUpdates(locationListener);

            Log.d(TAG, "Location Manager was removed ");
        }
    }

}
