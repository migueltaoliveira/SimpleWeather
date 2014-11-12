package uc.pgi.coimbraweather;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import butterknife.ButterKnife;
import butterknife.InjectView;
import utils.Temperature;


public class WeatherActivity extends Activity {
    @InjectView(R.id.header) TextView header;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        ButterKnife.inject(this);
        Ion.getDefault(this).configure().setGson(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create());
        Ion.with(this)
                .load("https://aqueous-chamber-4634.herokuapp.com/meteo?lat=40.2&lng=-8.4166667")
                .as(new TypeToken<Temperature>() {
                })
                .setCallback(new FutureCallback<Temperature>() {
                    @Override
                    public void onCompleted(Exception e, Temperature result) {
                        // do stuff with the result or error
                        if (result != null) Log.i("Result", "" + result.toString());
                        else {
                            e.printStackTrace();
                            Log.e("Error", "" + e.getMessage());
                        }
                    }
                });

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
