package de.jonashackt.common;

import de.jonashackt.messaging.EventGetOutlook;
import de.jonashackt.model.MethodOfPayment;
import de.jonashackt.model.Product;
import de.jonashackt.model.User;
import de.jonashackt.model.Weather;

public class ModelUtil {

    public static EventGetOutlook exampleEventGetOutlook() {
        EventGetOutlook eventGetOutlook = new EventGetOutlook();

        Weather weather = new Weather();
        weather.setFlagColor("blue");
        weather.setPostalCode("99425");
        weather.setProduct(Product.ForecastBasic);
        weather.setUser(new User(27, 4300, MethodOfPayment.Bitcoin));

        eventGetOutlook.setWeather(weather);
        return eventGetOutlook;
    }
}
