package de.jonashackt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jonashackt.businesslogic.IncredibleLogic;
import de.jonashackt.model.*;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/weather")
public class WeatherBackendController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherBackendController.class);

    @Value(value="classpath:responses/forecast.pdf")
    private Resource forecastPdf;

    @RequestMapping(path = "/general/outlook", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody GeneralOutlook generateGeneralOutlook(@RequestBody Weather weather) throws JsonProcessingException {
        LOG.info("Request for /general/outlook with POST");
        /*
         * Some incredible Businesslogic...
         */
        LOG.info("Called Backend");

        ObjectMapper mapper = new ObjectMapper();
        LOG.info("Object as JSON:" + mapper.writeValueAsString(weather));

        return IncredibleLogic.generateGeneralOutlook();
    }

    @RequestMapping(path = "/general/outlook", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String infoAboutGeneralOutlook() throws JsonProcessingException {
        LOG.info("Request for /general/outlook with GET");

        Weather weather = new Weather();
        weather.setFlagColor("blue");
        weather.setPostalCode("99425");
        weather.addUser(new User(55, 5634500, MethodOfPayment.Bitcoin));
        weather.setProduct(Product.ForecastBasic);

        ObjectMapper mapper = new ObjectMapper();
        String weatherJson = mapper.writeValueAsString(weather);

        return "Try a POST also against this URL! Just send some body with it like: '" + weatherJson + "'";
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "text/plain")
    public String whatsTheSenseInThat(@PathVariable("name") String name) {
        LOG.info("Request for /{name} with GET");
        return "Hello " + name + "! This is a RESTful HttpService written in Spring. Try to use some other HTTP verbs (don´t say 'methods' :P ) :)";
    }

    @RequestMapping(path = "/general/outlook/{zip}", method=RequestMethod.GET, produces="application/pdf")
    @ResponseStatus(HttpStatus.OK)
    public byte[] getWeatherInformationPdf(@PathVariable("zip") String zip) throws IOException {
        LOG.info("Request for /general/outlook/{zip} with GET");

        return IOUtils.toByteArray(forecastPdf.getInputStream());
    }
}
