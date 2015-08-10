package com.outdooractive.example.magicOfWinter

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import com.outdooractive.api.CategoryItem
import com.outdooractive.api.Implicits
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.Tour
import com.outdooractive.api.TourHeader
import com.outdooractive.map.MapViewFragment

class MainActivity extends Activity with IActionListener with Implicits {
  protected override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    //getWindow.requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
    //getWindow.requestFeature(Window.FEATURE_ACTION_BAR)
    getActionBar.setDisplayShowHomeEnabled(false)
    getActionBar.hide()
    setContentView(R.layout.main_activity)
    Option(savedInstanceState) getOrElse getFragmentManager.beginTransaction.add(R.id.fragment_container, new IntroFragment).commit
  }

  def onOpenMapRequest(tour: Option[Tour]) {
    val args = new Bundle
    args.putString("start", tour.map(_.startingPoint).getOrElse(""))
    args.putString("geometry", tour.map(_.geometry).getOrElse(""))
    args.putBoolean("winter", tour.exists(_.isWinterTour))
    this.setFragment(new MapViewFragment, args)
  }

  def onOpenTourCategoriesRequest() {
    categoryRoot onSuccess {
      case root: CategoryItem => this.openCategoryList(root)
    }
  }

  def onOpenCategoryRequest(categoryId: String) {
    categoryRoot map (_.findById(categoryId)) onSuccess {
      case Some(root) => if (root.hasChildren) {
        this.openCategoryList(root)
      } else {
        this.openTourList(root)
      }
      case _ =>
    }
  }

  def onOpenTourDetailsRequest(tourHeader: TourHeader) {
    val args = new Bundle
    args.putString("tourId", tourHeader.id)
    args.putString("tourTitle", tourHeader.title)
    this.setFragment(new TourDetailsFragment, args)
  }

  private def openCategoryList(root: CategoryItem) {
    val header = if (root.id eq "0") getString(R.string.action_tours) else root.name
    val args = new Bundle
    args.putString("header", header)
    args.putStringArrayList("categoryIds", root.getChildrenIds)
    args.putStringArrayList("categoryNames", root.getChildrenNames)
    this.setFragment(new CategoryListFragment, args)
  }

  private def openTourList(parent: CategoryItem) {
    val args = new Bundle
    args.putString("header", parent.name)
    args.putString("categoryId", parent.id)
    this.setFragment(new TourListFragment, args)
  }

  private def setFragment(fragment: Fragment, args: Bundle) {
    fragment.setArguments(args)
    getFragmentManager.beginTransaction.replace(R.id.fragment_container, fragment).addToBackStack("activeFragment").commit
  }

  private lazy val categoryRoot = {
    ObjectLoader.loadTourCategories(this) map (new CategoryItem(_))
  }
}
