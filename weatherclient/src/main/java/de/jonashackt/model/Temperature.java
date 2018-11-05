package de.jonashackt.model;

public class Temperature {

    private String minimun;
    private String maximum;

    public Temperature(String minimun, String maximum) {
        this.minimun = minimun;
        this.maximum = maximum;
    }

    public String getMinimun() {
        return minimun;
    }

    public void setMinimun(String minimun) {
        this.minimun = minimun;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }
}
