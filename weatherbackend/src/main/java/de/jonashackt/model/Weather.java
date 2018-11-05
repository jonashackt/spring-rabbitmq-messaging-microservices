package de.jonashackt.model;


import java.util.ArrayList;
import java.util.List;

public class Weather {
	
	private String postalCode;
	private String flagColor;
	private Product product;

	private List<User> users = new ArrayList<>();

	public String getPostalCode() {
		return postalCode;
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		this.users.add(user);
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
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}
