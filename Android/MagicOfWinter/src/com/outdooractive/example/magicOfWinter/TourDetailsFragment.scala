package com.outdooractive.example.magicOfWinter

import com.outdooractive.api.ImageLoaderTask
import com.outdooractive.api.Implicits
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.Tour
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.scaloid.support.v4.SFragment
import android.support.v7.app.ActionBarActivity

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
    val objectLoader: ObjectLoader = new ObjectLoader(this.getActivity)
    objectLoader.loadTour(getArguments.getString("tourId")) onSuccess {
      case result: Any => this.getActivity runOnUiThread { new Runnable { def run { setTour(new Tour(result)) } } }
    }
  }

  private def setTour(tour: Tour) {
    ImageLoaderTask loadFromWeb (tour.imageId) onSuccess {
      case image: Drawable => this.getActivity runOnUiThread { new Runnable { def run { imageView.setImageDrawable(image) } } }
    }

    openMapButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
        (getActivity.asInstanceOf[IActionListener]).onOpenMapRequest(Some(tour))
      }
    })
    descriptionTextView.setText(tour.longText)
    authorTextView.setText(tour.author)
    sourceTextView.setText(tour.source)
  }

  private lazy val imageView = view.findViewById(R.id.tour_image).asInstanceOf[ImageView]
  private lazy val descriptionTextView = view.findViewById(R.id.details_text_view).asInstanceOf[TextView]
  private lazy val authorTextView = view.findViewById(R.id.author_text_view).asInstanceOf[TextView]
  private lazy val sourceTextView = view.findViewById(R.id.source_text_view).asInstanceOf[TextView]
  private lazy val openMapButton = view.findViewById(R.id.btn_map_with_tour).asInstanceOf[Button]
}
