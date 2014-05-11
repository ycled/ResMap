package com.ycled.resmap.model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class AutoCompletePlaceList  extends ArrayList<AutoCompletePlace>{
	
	private ArrayList<AutoCompletePlace> placeList;
	
	public AutoCompletePlaceList() {
		this.placeList = new ArrayList<AutoCompletePlace>();
	}
	
	
	/**
	 * return description list of the places
	 */
	public String[] getPlaceDescriptionList() {

		String[] discriptList = new String[placeList.size()];

		for (int i = 0; i < placeList.size(); i++) {
			discriptList[i] = placeList.get(i).getDiscription();
		}
		return discriptList;
	}
	

}
