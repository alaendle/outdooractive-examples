package com.outdooractive.example.magicOfWinter;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CategoryListFragment extends Fragment {

	private final ArrayList<String> categoryNameList = new ArrayList<String>();
	private final ArrayList<String> categoryIdList = new ArrayList<String>();
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String header = getArguments().getString("header");
		getActivity().getActionBar().setTitle(header);
		getActivity().getActionBar().show();

		View view = inflater.inflate(R.layout.category_list_fragment,
				container, false);

		// handle list item click
		listView = (ListView) view.findViewById(R.id.category_list_view);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String categoryId = categoryIdList.get(position);
				((IActionListener) getActivity())
						.onOpenCategoryRequest(categoryId);
			}
		});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// fill the view with category list items
		this.categoryIdList.clear();
		this.categoryIdList.addAll(getArguments().getStringArrayList(
				"categoryIds"));
		this.categoryNameList.clear();
		this.categoryNameList.addAll(getArguments().getStringArrayList(
				"categoryNames"));

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this.getActivity(), R.layout.default_list_item,
				this.categoryNameList);
		listView.setAdapter(adapter);
	}
}
