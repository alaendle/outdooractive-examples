package com.outdooractive.example.magicOfWinter

import java.util.ArrayList

import org.scaloid.support.v4.SFragment

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

class IntroFragment extends SFragment {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.hide
    val view: View = inflater.inflate(R.layout.intro_fragment, container, false)
    val listItems: ArrayList[String] = new ArrayList[String]
    listItems.add(getActivity.getString(R.string.action_map))
    listItems.add(getActivity.getString(R.string.action_tours))
    val listView: ListView = view.findViewById(R.id.intro_list_view).asInstanceOf[ListView]
    val adapter: ArrayAdapter[String] = new ArrayAdapter[String](this.getActivity, R.layout.default_list_item, listItems)
    listView.setAdapter(adapter)
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val name: String = listItems.get(position)
        if (name eq getActivity.getString(R.string.action_map)) {
          (getActivity.asInstanceOf[IActionListener]).onOpenMapRequest(None)
        } else {
          (getActivity.asInstanceOf[IActionListener]).onOpenTourCategoriesRequest
        }
      }
    })
    view
  }
}
