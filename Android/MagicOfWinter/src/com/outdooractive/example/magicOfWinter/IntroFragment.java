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

public class IntroFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().getActionBar().hide();

		View view = inflater.inflate(R.layout.intro_fragment, container, false);

		// add two list items (map and tours)
		final ArrayList<String> listItems = new ArrayList<String>();
		listItems.add(getActivity().getString(R.string.action_map));
		listItems.add(getActivity().getString(R.string.action_tours));

		ListView listView = (ListView) view.findViewById(R.id.intro_list_view);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this.getActivity(), R.layout.default_list_item, listItems);
		listView.setAdapter(adapter);

		// handle list item click
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String name = listItems.get(position);
				if (name == getActivity().getString(R.string.action_map)) {
					// open map
					((IActionListener) getActivity()).onOpenMapRequest();
				} else {
					// open tours
					((IActionListener) getActivity())
							.onOpenTourCategoriesRequest();
				}
			}
		});

		return view;
	}
}
