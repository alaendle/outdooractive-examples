package com.outdooractive.example.magicOfWinter

import org.scaloid.common.runOnUiThread
import org.scaloid.support.v4.SFragment

import com.outdooractive.api.ImageLoaderTask
import com.outdooractive.api.Implicits
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.Tour

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class TourDetailsFragment extends SFragment with Implicits {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.setTitle(R.string.tour_details)
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.show
    val view = inflater.inflate(R.layout.tour_details_fragment, container, false)
    view
      .findViewById(R.id.details_title_view).asInstanceOf[TextView]
      .setText(getArguments.getString("tourTitle"))
    view
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    val objectLoader = new ObjectLoader(this.getActivity)
    objectLoader.loadTour(getArguments.getString("tourId")) onSuccess {
      case result: Any => runOnUiThread(setTour(new Tour(result)))
    }
  }

  private def setTour(tour: Tour) {
    lazy val imageView = view.findViewById(R.id.tour_image).asInstanceOf[ImageView]
    ImageLoaderTask loadFromWeb (tour.imageId) onSuccess {
      case image: Drawable => runOnUiThread(imageView.setImageDrawable(image))
    }

    val openMapButton = view.findViewById(R.id.btn_map_with_tour).asInstanceOf[Button]
    openMapButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
        (getActivity.asInstanceOf[IActionListener]).onOpenMapRequest(Some(tour))
      }
    })

    val descriptionTextView = view.findViewById(R.id.details_text_view).asInstanceOf[TextView]
    val authorTextView = view.findViewById(R.id.author_text_view).asInstanceOf[TextView]
    val sourceTextView = view.findViewById(R.id.source_text_view).asInstanceOf[TextView]
    descriptionTextView.setText(tour.longText)
    authorTextView.setText(tour.author)
    sourceTextView.setText(tour.source)
  }
}