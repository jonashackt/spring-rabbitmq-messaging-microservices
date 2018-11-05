package de.jonashackt.adapter;

import de.jonashackt.model.GeneralOutlook;
import de.jonashackt.model.Weather;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("weatherbackend")
public interface WeatherBackendClient {

    @RequestMapping(path = "/weather/general/outlook", method= RequestMethod.POST, produces="application/json")
    GeneralOutlook generateGeneralOutlook(@RequestBody Weather weather);

    @RequestMapping(path = "/weather/general/outlook", method=RequestMethod.GET, produces="application/json")
    String infoAboutGeneralOutlook();

    @RequestMapping(value = "/weather/{name}", method = RequestMethod.GET, produces = "text/plain")
    String whatsTheSenseInThat(@PathVariable("name") String name);

    @RequestMapping(path = "/weather/general/outlook/{zip}", method=RequestMethod.GET, produces="application/pdf")
    byte[] getWeatherInformationPdf(@PathVariable("zip") String zip);
}
