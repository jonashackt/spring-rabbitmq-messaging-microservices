package de.jonashackt.messaging;

import com.fasterxml.jackson.annotation.JsonRootName;
import de.jonashackt.model.GeneralOutlook;

@JsonRootName(value="EventGeneralOutlook")
public class EventGeneralOutlook {

    private GeneralOutlook generalOutlook;

    public void setGeneralOutlook(GeneralOutlook generalOutlook) {
        this.generalOutlook = generalOutlook;
    }

    public GeneralOutlook getGeneralOutlook() {
        return generalOutlook;
    }
}
