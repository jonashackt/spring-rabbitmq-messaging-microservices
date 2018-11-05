package de.jonashackt.adapter;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import de.jonashackt.model.GeneralOutlook;
import de.jonashackt.model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherBackendAdapterImpl implements WeatherBackendAdapter {

    @Autowired
    private EurekaClient eurekaClient;

    private static final Logger LOG = LoggerFactory.getLogger(WeatherBackendAdapterImpl.class);
    private final String url = "http://localhost:8090/weather/general/outlook";

    @Autowired
    private WeatherBackendClient weatherBackendClient;

    @Override
    public GeneralOutlook generateGeneralOutlook(Weather weather) {
        logCallerInfo();
        return weatherBackendClient.generateGeneralOutlook(weather);
    }

    @Override
    public byte[] getWeatherInformationPdf(String zip) {
        logCallerInfo();

        return weatherBackendClient.getWeatherInformationPdf(zip);
    }

    private void logCallerInfo() {
        InstanceInfo weatherbackendEurekaInfo = eurekaClient.getApplication("weatherbackend").getInstances().get(0);
        LOG.info(String.format("Calling weatherbackend with Feign: '%s', '%s', '%s', '%s'",
                weatherbackendEurekaInfo.getHostName(),
                weatherbackendEurekaInfo.getPort(),
                weatherbackendEurekaInfo.getStatus(),
                weatherbackendEurekaInfo.getHomePageUrl()));
    }
}
