package com.outdooractive.example.magicOfWinter

import java.util.ArrayList

import org.scaloid.common.runOnUiThread
import org.scaloid.support.v4.SFragment

import com.outdooractive.api.Implicits
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.TourHeader
import com.outdooractive.api.TourList

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class TourListFragment extends SFragment with Implicits {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val header = getArguments.getString("header")
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.setTitle(header)
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.show
    inflater.inflate(R.layout.tour_list_fragment, container, false)
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val item = tourList.get(position)
        (getActivity.asInstanceOf[IActionListener]).onOpenTourDetailsRequest(item)
      }
    })

    val objectLoader = new ObjectLoader(this.getActivity)
    objectLoader.loadTourList(getArguments.getString("categoryId")) onSuccess {
      case result: Any => runOnUiThread(setListItems(new TourList(result)))
    }
  }

  private def setListItems(tours: TourList) {
    if (getActivity == null) {
      return
    }
    tourList.clear
    tourList.addAll(tours.tours)
    val adapter = new ArrayAdapter[TourHeader](getActivity, R.layout.tour_list_item, tourList)
    listView.setAdapter(adapter)
  }

  private final val tourList = new ArrayList[TourHeader]
  private def listView = view.findViewById(R.id.tour_list_view).asInstanceOf[ListView]
}
