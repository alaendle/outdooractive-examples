package com.outdooractive.example.magicOfWinter

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class IntroFragment extends Fragment {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    getActivity.getActionBar.hide()
    val view = inflater.inflate(R.layout.intro_fragment, container, false)
    val listItems = new java.util.ArrayList[String]
    listItems.add(getActivity.getString(R.string.action_map))
    listItems.add(getActivity.getString(R.string.action_tours))
    val listView = view.findViewById(R.id.intro_list_view).asInstanceOf[ListView]
    val adapter = new ArrayAdapter[String](this.getActivity, R.layout.default_list_item, listItems)
    listView.setAdapter(adapter)
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val name = listItems.get(position)
        if (name eq getActivity.getString(R.string.action_map)) {
          getActivity.asInstanceOf[IActionListener].onOpenMapRequest(None)
        } else {
          getActivity.asInstanceOf[IActionListener].onOpenTourCategoriesRequest()
        }
      }
    })
    view
  }
}
