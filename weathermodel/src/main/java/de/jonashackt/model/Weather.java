package de.jonashackt.model;


public class Weather {

	private String postalCode;
	private String flagColor;
	private Product product;
	private User user;

	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String zipCode) {
		this.postalCode = zipCode;
	}
	public String getFlagColor() {
		return flagColor;
	}
	public void setFlagColor(String flagColor) {
		this.flagColor = flagColor;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	private Weather(Builder builder) {
		setPostalCode(builder.postalCode);
		setFlagColor(builder.flagColor);
		setProduct(builder.product);
		setUser(builder.user);
	}

	public static final class Builder {

		private String postalCode;
		private String flagColor;
		private Product product;
		private User user;

		private Builder() {}

		public Builder withPostalCode(String postalCode) {
			this.postalCode = postalCode;
			return this;
		}

		public Builder withFlagColor(String flagColor) {
			this.flagColor = flagColor;
			return this;
		}

		public Builder usingProduct(Product product) {
			this.product = product;
			return this;
		}

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public Weather build() {
			return new Weather(this);
		}
	}

}
