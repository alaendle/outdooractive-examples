package com.outdooractive.example.magicOfWinter

import java.util.ArrayList

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.outdooractive.api.Implicits
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.TourHeader
import com.outdooractive.api.TourList
import org.scaloid.common.runOnUiThread

class TourListFragment extends Fragment with Implicits {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val header = getArguments.getString("header")
    getActivity.getActionBar.setTitle(header)
    getActivity.getActionBar.show()
    inflater.inflate(R.layout.tour_list_fragment, container, false)
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val item = tourList.get(position)
        getActivity.asInstanceOf[IActionListener].onOpenTourDetailsRequest(item)
      }
    })

    ObjectLoader.loadTourList(this.getActivity, getArguments.getString("categoryId")) onSuccess {
      case result => runOnUiThread(setListItems(new TourList(result)))
    }
  }

  private def setListItems(tours: TourList) {
    if (getActivity == null) {
      return
    }
    tourList.clear()
    tourList.addAll(tours.tours)
    val adapter = new ArrayAdapter[TourHeader](getActivity, R.layout.tour_list_item, tourList)
    listView.setAdapter(adapter)
  }

  private final val tourList = new ArrayList[TourHeader]
  private def listView = getView.findViewById(R.id.tour_list_view).asInstanceOf[ListView]
}
