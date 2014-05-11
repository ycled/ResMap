package com.ycled.resmap.model;


/**
 * Model class of a single auto complete place
 */
public class AutoCompletePlace {

	private String description, id, reference;
	
	public AutoCompletePlace() {}
	
	
	public AutoCompletePlace(String discription, String id, String reference){
		
		this.description = discription;
		this.id = id;
		this.reference = reference;
		
	}


	/**
	 * Getter and Setter
	 */
	public String getDiscription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}

}
