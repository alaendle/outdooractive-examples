package com.outdooractive.example.magicOfWinter

import java.util.ArrayList
import com.outdooractive.api.Implicits
import com.outdooractive.api.ObjectLoader
import com.outdooractive.api.TourHeader
import com.outdooractive.api.TourList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import org.scaloid.common.runOnUiThread
import org.scaloid.support.v4.SFragment
import android.support.v7.app.ActionBarActivity

class TourListFragment extends SFragment with Implicits {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val header: String = getArguments.getString("header")
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.setTitle(header)
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.show
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

  private final val tourList: ArrayList[TourHeader] = new ArrayList[TourHeader]
  private var listView: ListView = null
}
