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

	private GeneralOutlook(Builder builder) {
		setCity(builder.city);
		setDate(builder.date);
		setState(builder.state);
		setWeatherStation(builder.weatherStation);
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static final class Builder {
		private String city;
		private String state;
		private String weatherStation;
		private Date date;

		private Builder() {}

		public Builder inCity(String city) {
			this.city = city;
			return this;
		}

		public Builder inState(String state) {
			this.state = state;
			return this;
		}

		public Builder withDate(Date date) {
			this.date = date;
			return this;
		}

		public Builder withWeatherStation(String weatherStation) {
			this.weatherStation = weatherStation;
			return this;
		}

		public GeneralOutlook build() {
			return new GeneralOutlook(this);
		}
	}
}
