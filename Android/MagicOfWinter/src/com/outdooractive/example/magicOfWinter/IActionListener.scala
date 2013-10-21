package com.outdooractive.example.magicOfWinter

import com.outdooractive.api.Tour
import com.outdooractive.api.TourHeader

trait IActionListener {
  def onOpenMapRequest()

  def onOpenMapRequest(tour: Tour)

  def onOpenTourCategoriesRequest()

  def onOpenCategoryRequest(categoryId: Int)

  def onOpenTourDetailsRequest(tourHeader: TourHeader)
}
