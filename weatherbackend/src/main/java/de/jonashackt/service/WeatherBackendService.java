package de.jonashackt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jonashackt.businesslogic.IncredibleLogic;
import de.jonashackt.model.GeneralOutlook;
import de.jonashackt.model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class WeatherBackendService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherBackendService.class);

    public @ResponseBody GeneralOutlook generateGeneralOutlook(@RequestBody Weather weather) throws JsonProcessingException {

        LOG.info("Called WeatherBackend with Json: " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(weather));
        return IncredibleLogic.generateGeneralOutlookNeeds3Seconds();
    }
}
