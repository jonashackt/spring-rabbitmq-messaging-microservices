package de.jonashackt.endpoint;

import de.codecentric.namespace.weatherservice.WeatherService;
import de.codecentric.namespace.weatherservice.general.ForecastRequest;
import de.codecentric.namespace.weatherservice.general.ForecastReturn;
import de.codecentric.namespace.weatherservice.general.WeatherInformationReturn;
import de.codecentric.namespace.weatherservice.general.WeatherReturn;
import de.jonashackt.adapter.WeatherBackendAdapter;
import de.jonashackt.model.GeneralOutlook;
import de.jonashackt.model.Weather;
import de.jonashackt.transformation.GetCityForecastByZIPInMapper;
import de.jonashackt.transformation.GetCityForecastByZIPOutMapper;
import de.jonashackt.transformation.GetCityWeatherByZIPOutMapper;
import de.jonashackt.transformation.GetWeatherInformationOutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WeatherServiceEndpoint implements WeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherServiceEndpoint.class);

    @Autowired
    private WeatherBackendAdapter weatherBackendAdapter;

    @Override
    public ForecastReturn getCityForecastByZIP(ForecastRequest forecastRequest) {
        LOG.info("Transformation of incoming JAXB-Bind Objects to internal Model");
        Weather weather = GetCityForecastByZIPInMapper.mapRequest2Weather(forecastRequest);

        LOG.info("Call Backend with internal Model");
        GeneralOutlook generalOutlook = weatherBackendAdapter.generateGeneralOutlook(weather);

        LOG.info("Transformation internal Model to outgoing JAXB-Bind Objects");
        return GetCityForecastByZIPOutMapper.mapGeneralOutlook2Forecast(generalOutlook);
    }

    @Override
    public WeatherReturn getCityWeatherByZIP(ForecastRequest forecastRequest) {
        LOG.info("Transformation of incoming JAXB-Bind Objects to internal Model");
        Weather site = GetCityForecastByZIPInMapper.mapRequest2Weather(forecastRequest);

        LOG.info("Call Backend with internal Model");
        GeneralOutlook generalOutlook = weatherBackendAdapter.generateGeneralOutlook(site);

        LOG.info("Transformation internal Model to outgoing JAXB-Bind Objects");
        return GetCityWeatherByZIPOutMapper.mapGeneralOutlook2Weather(generalOutlook);
    }

    @Override
    public WeatherInformationReturn getWeatherInformation(String zip) {

        LOG.info("Call Backend with internal Model");
        byte[] pdf = weatherBackendAdapter.getWeatherInformationPdf(zip);

        LOG.info("Transformation internal Model to outgoing JAXB-Bind Objects");
        return GetWeatherInformationOutMapper.mapPdf2WeatherOverviewPdf(pdf);
    }


}
