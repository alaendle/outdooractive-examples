package com.outdooractive.example.magicOfWinter

import com.outdooractive.api.Tour
import com.outdooractive.api.TourHeader

trait IActionListener {
  def onOpenMapRequest(tour: Option[Tour])

  def onOpenTourCategoriesRequest()

  def onOpenCategoryRequest(categoryId: String)

  def onOpenTourDetailsRequest(tourHeader: TourHeader)
}
