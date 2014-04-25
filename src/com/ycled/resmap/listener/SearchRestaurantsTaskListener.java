package com.ycled.resmap.listener;

import java.util.ArrayList;

import com.ycled.resmap.model.Restaurant;

public interface SearchRestaurantsTaskListener {
	public void onTaskComplete(ArrayList<Restaurant> result);
}
