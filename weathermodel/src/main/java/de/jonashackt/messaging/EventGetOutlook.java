package de.jonashackt.messaging;

import com.fasterxml.jackson.annotation.JsonRootName;
import de.jonashackt.model.Weather;

@JsonRootName(value="EventGetOutlook")
public class EventGetOutlook {

    private Weather weather;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
