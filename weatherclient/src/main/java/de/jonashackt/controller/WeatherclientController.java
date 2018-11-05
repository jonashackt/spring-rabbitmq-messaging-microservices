package de.jonashackt.controller;

import de.codecentric.namespace.weatherservice.WeatherException;
import de.codecentric.namespace.weatherservice.WeatherService;
import de.codecentric.namespace.weatherservice.general.ForecastRequest;
import de.codecentric.namespace.weatherservice.general.ForecastReturn;
import de.codecentric.namespace.weatherservice.general.WeatherInformationReturn;
import de.jonashackt.model.Forecast;
import de.jonashackt.transformation.WeatherServiceInputTransformer;
import de.jonashackt.transformation.WeatherServiceOutputTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherclientController {

    @Autowired
    private WeatherService weatherServiceClient;

    @GetMapping("/forecast/{zip}")
    @ResponseStatus(HttpStatus.OK)
    public Forecast forecast(@PathVariable("zip") String zip) throws WeatherException {

        ForecastRequest forecastRequest = WeatherServiceInputTransformer.generateRequest(zip);

        ForecastReturn cityForecastByZIP = weatherServiceClient.getCityForecastByZIP(forecastRequest);

        return WeatherServiceOutputTransformer.mapIntoForecast(cityForecastByZIP);
    }

    @RequestMapping(path = "/forecast/pdf/{zip}", method = RequestMethod.GET, produces="application/pdf")
    @ResponseStatus(HttpStatus.OK)
    public byte[] forecastAsPdf(@PathVariable("zip") String zip) throws WeatherException {

        WeatherInformationReturn weatherInformation = weatherServiceClient.getWeatherInformation(zip);

        return WeatherServiceOutputTransformer.extractPdf(weatherInformation);
    }
}
