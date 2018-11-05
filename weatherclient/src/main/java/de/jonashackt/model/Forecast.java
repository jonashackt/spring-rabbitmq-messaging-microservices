package de.jonashackt.model;

public class Forecast {

    private Temperature temperature;
    private String rainfallChance;
    private String city;

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getRainfallChance() {
        return rainfallChance;
    }

    public void setRainfallChance(String rainfallChance) {
        this.rainfallChance = rainfallChance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
