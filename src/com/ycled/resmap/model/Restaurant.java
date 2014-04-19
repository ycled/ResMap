package com.ycled.resmap.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Model class representation of a single restaurant
 */
public class Restaurant {
	private String name, address, reference, photoUrl;
	private LatLng location;
	// private Photo thumbNail;
	private double rating;

	// ===========additional member variables, optional =============
	// TODO::
	// private String phone, website;
	// private ArrayList<Review> reviews;

	public Restaurant() {
	}
	
	public Restaurant(String reference, String name, String address,
			LatLng location, double rating) {

		this.name = name;
		this.address = address;
		this.reference = reference;
		this.location = location;
		this.rating = rating;

	}

	

	
	
	/**
	 * Getter and Setter
	 */
	public String getName() {
		return name;
	}

	public double getRating() {
		return rating;
	}

	public String getAddress() {
		return address;
	}

	public String getReference() {
		return reference;
	}

	public LatLng getLocation() {
		return location;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public void setLocation(LatLng location) {
		this.location = location;
	}

	
	/**
	 * Helper functions
	 */
	
	/*Provide a good presentation of the restaurant object*/
	@Override
	public String toString() {
		return String.format("Restaurant [name=%s, address=%s, rating=%f",
				name, address, rating);
	}
}
