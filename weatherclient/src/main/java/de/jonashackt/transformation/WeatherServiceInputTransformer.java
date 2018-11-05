package de.jonashackt.transformation;

import de.codecentric.namespace.weatherservice.datatypes.ProductName;
import de.codecentric.namespace.weatherservice.general.ForecastCustomer;
import de.codecentric.namespace.weatherservice.general.ForecastRequest;

public class WeatherServiceInputTransformer {

    public static ForecastRequest generateRequest(String zip) {
        ForecastRequest forecastRequest = new ForecastRequest();
        forecastRequest.setFlagcolor("bluewhite");
        forecastRequest.setProductName(ProductName.FORECAST_BASIC);
        forecastRequest.setZIP(zip);
        forecastRequest.setForecastCustomer(generateForecastCustomer());
        return forecastRequest;
    }

    private static ForecastCustomer generateForecastCustomer() {
        ForecastCustomer forecastCustomer = new ForecastCustomer();
        forecastCustomer.setAge(30);
        forecastCustomer.setContribution(5000);
        forecastCustomer.setMethodOfPayment("Paypal");
        return forecastCustomer;
    }
}
