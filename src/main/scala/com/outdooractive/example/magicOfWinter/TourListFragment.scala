package com.outdooractive.example.magicOfWinter

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
import macroid.Contexts
import macroid.Ui

class TourListFragment extends Fragment with Implicits with Contexts[Fragment] {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val header = getArguments.getString("header")
    getActivity.getActionBar.setTitle(header)
    getActivity.getActionBar.show()
    inflater.inflate(R.layout.tour_list_fragment, container, false)
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)
    val tourList = new java.util.ArrayList[TourHeader]
    val listView = getView.findViewById(R.id.tour_list_view).asInstanceOf[ListView]

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val item = tourList.get(position)
        getActivity.asInstanceOf[IActionListener].onOpenTourDetailsRequest(item)
      }
    })

    ObjectLoader.loadTourList(this.getActivity, getArguments.getString("categoryId")) onSuccess {
      case result: String => Ui {
        tourList.addAll(new TourList(result).tours)
        setListItems(listView, tourList)
      }.run
    }
  }

  private def setListItems(listView: ListView, tours: java.util.ArrayList[TourHeader]) {
    fragmentActivityContext.activity.get foreach (activity => {
      val adapter = new ArrayAdapter[TourHeader](activity, R.layout.tour_list_item, tours)
      listView.setAdapter(adapter)
    })
  }
}
