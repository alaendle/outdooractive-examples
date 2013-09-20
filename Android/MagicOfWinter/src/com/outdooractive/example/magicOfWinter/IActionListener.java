package com.outdooractive.example.magicOfWinter;

import com.outdooractive.api.Tour;
import com.outdooractive.api.TourList.TourHeader;

public interface IActionListener {

	public void onOpenMapRequest();

	public void onOpenMapRequest(Tour tour);

	public void onOpenTourCategoriesRequest();

	public void onOpenCategoryRequest(int categoryId);

	public void onOpenTourDetailsRequest(TourHeader tourHeader);
}
