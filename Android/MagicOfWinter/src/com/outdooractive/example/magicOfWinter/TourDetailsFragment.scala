package com.outdooractive.example.magicOfWinter

import com.outdooractive.api.IImageResultListener
import com.outdooractive.api.IStringResultListener
import com.outdooractive.api.ImageLoaderTask
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.Tour

import android.app.Fragment
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class TourDetailsFragment extends Fragment {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    getActivity.getActionBar.setTitle(R.string.tour_details)
    getActivity.getActionBar.show
    val view: View = inflater.inflate(R.layout.tour_details_fragment, container, false)
    val titleView: TextView = view.findViewById(R.id.details_title_view).asInstanceOf[TextView]
    titleView.setText(getArguments.getString("tourTitle"))
    imageView = view.findViewById(R.id.tour_image).asInstanceOf[ImageView]
    openMapButton = view.findViewById(R.id.btn_map_with_tour).asInstanceOf[Button]
    descriptionTextView = view.findViewById(R.id.details_text_view).asInstanceOf[TextView]
    authorTextView = view.findViewById(R.id.author_text_view).asInstanceOf[TextView]
    sourceTextView = view.findViewById(R.id.source_text_view).asInstanceOf[TextView]
    return view
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    val objectLoader: ObjectLoader = new ObjectLoader(this.getActivity, new IStringResultListener {
      def onResult(`object`: String) {
        TourDetailsFragment.this.setTour(new Tour(`object`))
      }
    })
    objectLoader.loadTour(getArguments.getString("tourId"))
  }

  private def setTour(tour: Tour) {
    new ImageLoaderTask(new IImageResultListener {
      def onImageLoaded(image: Drawable) {
        imageView.setImageDrawable(image)
      }
    }).loadFromWeb(tour.imageId)
    openMapButton.setOnClickListener(new View.OnClickListener {
      def onClick(v: View) {
        (getActivity.asInstanceOf[IActionListener]).onOpenMapRequest(tour)
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
