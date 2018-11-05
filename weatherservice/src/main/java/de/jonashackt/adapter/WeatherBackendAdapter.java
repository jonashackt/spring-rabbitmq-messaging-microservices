package de.jonashackt.adapter;

import de.jonashackt.model.GeneralOutlook;
import de.jonashackt.model.Weather;

public interface WeatherBackendAdapter {
    GeneralOutlook generateGeneralOutlook(Weather weather);

    byte[] getWeatherInformationPdf(String zip);
}
