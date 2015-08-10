package com.outdooractive.example.magicOfWinter

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

class CategoryListFragment extends Fragment {
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    val header = getArguments.getString("header")
    getActivity.getActionBar.setTitle(header)
    getActivity.getActionBar.show()
    inflater.inflate(R.layout.category_list_fragment, container, false)
  }

  override def onActivityCreated(savedInstanceState: Bundle) {
    super.onActivityCreated(savedInstanceState)

    val categoryIdList = getArguments.getStringArrayList("categoryIds")
    val categoryNameList = getArguments.getStringArrayList("categoryNames")

    val listView = this.getView.findViewById(R.id.category_list_view).asInstanceOf[ListView]
    listView.setOnItemClickListener((_, _, position, _) => {
      val categoryId = categoryIdList.get(position)
      getActivity.asInstanceOf[IActionListener].onOpenCategoryRequest(categoryId)
    })

    val adapter = new ArrayAdapter[String](this.getActivity, R.layout.default_list_item, categoryNameList)
    listView.setAdapter(adapter)
  }
}
