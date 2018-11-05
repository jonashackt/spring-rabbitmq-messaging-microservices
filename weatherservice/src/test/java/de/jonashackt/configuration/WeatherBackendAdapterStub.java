package de.jonashackt.configuration;

import de.jonashackt.adapter.WeatherBackendAdapter;
import de.jonashackt.model.GeneralOutlook;
import de.jonashackt.model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class WeatherBackendAdapterStub implements WeatherBackendAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherBackendAdapterStub.class);

    @Override
    public GeneralOutlook generateGeneralOutlook(Weather weather) {
        LOG.info("calling the stub instead of the real weatherbackend");

        GeneralOutlook generalOutlook = new GeneralOutlook();
        generalOutlook.setCity("Weimar");
        generalOutlook.setDate(Date.from(Instant.now()));
        generalOutlook.setState("Germany");
        generalOutlook.setWeatherStation("BestStationInTown");
        return generalOutlook;
    }

    @Override
    public byte[] getWeatherInformationPdf(String zip) {
        LOG.info("calling the stub instead of the real weatherbackend");
        return null;
    }
}
