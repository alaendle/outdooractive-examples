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
    val view: View = inflater.inflate(R.layout.tour_details_fragment, container, false)
    val titleView: TextView = view.findViewById(R.id.details_title_view).asInstanceOf[TextView]
    titleView.setText(getArguments.getString("tourTitle"))
    imageView = view.findViewById(R.id.tour_image).asInstanceOf[ImageView]
    openMapButton = view.findViewById(R.id.btn_map_with_tour).asInstanceOf[Button]
    descriptionTextView = view.findViewById(R.id.details_text_view).asInstanceOf[TextView]
    authorTextView = view.findViewById(R.id.author_text_view).asInstanceOf[TextView]
    sourceTextView = view.findViewById(R.id.source_text_view).asInstanceOf[TextView]
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

  private var imageView: ImageView = null
  private var descriptionTextView: TextView = null
  private var authorTextView: TextView = null
  private var sourceTextView: TextView = null
  private var openMapButton: Button = null
}
