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

class CategoryListFragment extends SFragment {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val header = getArguments.getString("header")
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.setTitle(header)
    getActivity.asInstanceOf[ActionBarActivity].getSupportActionBar.show
    inflater.inflate(R.layout.category_list_fragment, container, false)
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)

    val listView = view.findViewById(R.id.category_list_view).asInstanceOf[ListView]
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener {
      def onItemClick(parent: AdapterView[_], view: View, position: Int, id: Long) {
        val categoryId: String = categoryIdList.get(position)
        (getActivity.asInstanceOf[IActionListener]).onOpenCategoryRequest(categoryId)
      }
    })

    this.categoryIdList.clear
    this.categoryIdList.addAll(getArguments.getStringArrayList("categoryIds"))
    this.categoryNameList.clear
    this.categoryNameList.addAll(getArguments.getStringArrayList("categoryNames"))
    val adapter: ArrayAdapter[String] = new ArrayAdapter[String](this.getActivity, R.layout.default_list_item, this.categoryNameList)
    listView.setAdapter(adapter)
  }

  private final val categoryNameList = new ArrayList[String]
  private final val categoryIdList = new ArrayList[String]
}