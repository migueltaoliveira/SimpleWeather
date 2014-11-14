package utils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by migueloliveira on 11/11/14.
 */
public class Temperature {
    @SerializedName("summary")
    public String summary;
    @SerializedName("icon")
    public String icon;
    @SerializedName("hour")
    public List<TemperatureTime> listTemperature;


    public Temperature(String summary, String icon, List<TemperatureTime> listTemperature) {
        super();
        this.summary = summary;
        this.icon = icon;
        this.listTemperature = listTemperature;
    }

    public String toString() {
        return "Temperature [summary=" + summary + ", icon=" + icon + ", hour=" + listTemperature + "]";
    }

}
