package de.jonashackt.model;

import java.util.Date;

public class GeneralOutlook {
	
	private String city;
	private String state;
	private String weatherStation;
	private Date date;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWeatherStation() {
		return weatherStation;
	}
	public void setWeatherStation(String weatherStation) {
		this.weatherStation = weatherStation;
	}
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
