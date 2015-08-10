package com.outdooractive.example.magicOfWinter

import android.app.Fragment
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.outdooractive.api.ImageLoaderTask
import com.outdooractive.api.Implicits
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.Tour
import macroid.Ui

class TourDetailsFragment extends Fragment with Implicits {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    getActivity.getActionBar.setTitle(R.string.tour_details)
    getActivity.getActionBar.show()
    val view = inflater.inflate(R.layout.tour_details_fragment, container, false)
    view
      .findViewById(R.id.details_title_view).asInstanceOf[TextView]
      .setText(getArguments.getString("tourTitle"))
    view
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    ObjectLoader.loadTour(this.getActivity, getArguments.getString("tourId")) onSuccess {
      case result: String => Ui { setTour(new Tour(result)) }.run
    }
  }

  private def setTour(tour: Tour) {
    lazy val imageView = getView.findViewById(R.id.tour_image).asInstanceOf[ImageView]
    ImageLoaderTask loadFromWeb tour.imageId onSuccess {
      case image: Drawable => Ui { imageView.setImageDrawable(image) }.run
    }

    val openMapButton = getView.findViewById(R.id.btn_map_with_tour).asInstanceOf[Button]
    openMapButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
        getActivity.asInstanceOf[IActionListener].onOpenMapRequest(Some(tour))
      }
    })

    val descriptionTextView = getView.findViewById(R.id.details_text_view).asInstanceOf[TextView]
    val authorTextView = getView.findViewById(R.id.author_text_view).asInstanceOf[TextView]
    val sourceTextView = getView.findViewById(R.id.source_text_view).asInstanceOf[TextView]
    descriptionTextView.setText(tour.longText)
    authorTextView.setText(tour.author)
    sourceTextView.setText(tour.source)
  }
}
