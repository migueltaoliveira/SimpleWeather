package utils;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by migueloliveira on 11/11/14.
 */
public class TemperatureTime {
    @SerializedName("time")
    public Date time;
    @SerializedName("temperature")
    public Double temperature;

    public TemperatureTime(Date time, Double temperature) {
        super();
        this.time = time;
        this.temperature = temperature;
    }

    public String toString() {
        return "Temperature [time="+time+", temperature="+temperature+"]";
    }


}
