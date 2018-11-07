package de.jonashackt.businesslogic;

import java.time.Instant;
import java.util.Date;

import de.jonashackt.model.GeneralOutlook;

public class IncredibleLogic {

    public static GeneralOutlook generateGeneralOutlookNeeds3Seconds() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        GeneralOutlook generalOutlook = new GeneralOutlook();
        generalOutlook.setCity("Weimar");
        generalOutlook.setDate(Date.from(Instant.now()));
        generalOutlook.setState("Germany");
        generalOutlook.setWeatherStation("BestStationInTown");
        return generalOutlook;
    }
}
