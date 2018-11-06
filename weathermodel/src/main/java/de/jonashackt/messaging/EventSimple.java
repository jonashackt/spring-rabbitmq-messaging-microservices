package de.jonashackt.messaging;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value="EventSimple")
public class EventSimple {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
