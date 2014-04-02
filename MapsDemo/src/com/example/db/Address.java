package com.example.db;

public class Address {
	private long id;
	private String street;
	private String city;
	private String country;
	private long wasteId;
	
	public long getWasteId() {
		return wasteId;
	}
	public void setWasteId(long wasteId) {
		this.wasteId = wasteId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
}
