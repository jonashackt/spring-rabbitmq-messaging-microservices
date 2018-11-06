package de.jonashackt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jonashackt.businesslogic.IncredibleLogic;
import de.jonashackt.model.GeneralOutlook;
import de.jonashackt.model.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

public class WeatherBackendController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherBackendController.class);

    public @ResponseBody GeneralOutlook generateGeneralOutlook(@RequestBody Weather weather) throws JsonProcessingException {

        LOG.info("Called WeatherBackend with Json: " + new ObjectMapper().writeValueAsString(weather));
        return IncredibleLogic.generateGeneralOutlook();
    }
}
