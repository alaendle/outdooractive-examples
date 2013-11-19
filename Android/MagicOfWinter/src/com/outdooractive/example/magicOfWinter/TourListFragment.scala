package com.outdooractive.example.magicOfWinter

import java.util.ArrayList

import com.outdooractive.api.Implicits
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.TourHeader
import com.outdooractive.api.TourList

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class TourListFragment extends Fragment with Implicits {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val header: String = getArguments.getString("header")
    getActivity.getActionBar.setTitle(header)
    getActivity.getActionBar.show
    val view: View = inflater.inflate(R.layout.tour_list_fragment, container, false)
    listView = view.findViewById(R.id.tour_list_view).asInstanceOf[ListView]
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val item: TourHeader = tourList.get(position)
        (getActivity.asInstanceOf[IActionListener]).onOpenTourDetailsRequest(item)
      }
    })
    view
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    val objectLoader: ObjectLoader = new ObjectLoader(this.getActivity)
    objectLoader.loadTourList(getArguments.getString("categoryId")) onSuccess {
      case result: Any => this.getActivity runOnUiThread { new Runnable { def run { setListItems(new TourList(result)) } } }
    }
  }

  private def setListItems(tours: TourList) {
    if (getActivity == null) {
      return
    }
    tourList.clear
    tourList.addAll(tours.tours)
    val adapter: ArrayAdapter[TourHeader] = new ArrayAdapter[TourHeader](getActivity, R.layout.tour_list_item, tourList)
    listView.setAdapter(adapter)
  }

  private final val tourList: ArrayList[TourHeader] = new ArrayList[TourHeader]
  private var listView: ListView = null
}
