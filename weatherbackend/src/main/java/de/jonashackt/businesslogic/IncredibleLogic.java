package de.jonashackt.businesslogic;

import java.time.Instant;
import java.util.Date;

import de.jonashackt.model.GeneralOutlook;

public class IncredibleLogic {

    public static GeneralOutlook generateGeneralOutlook() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return GeneralOutlook
                .newBuilder()
                .inCity("Weimar")
                .withDate(Date.from(Instant.now()))
                .inState("Germany")
                .withWeatherStation("BestStationInTown")
                .build();
    }
}
