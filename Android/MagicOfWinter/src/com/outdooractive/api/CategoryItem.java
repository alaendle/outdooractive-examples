package com.outdooractive.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoryItem {

  int id;
  String name;
  ArrayList<CategoryItem> children;

  public CategoryItem(String jsonString) {
    JSONObject json;
    try {
      json = new JSONObject(jsonString);
      this.id = json.has("id") ? json.optInt("id") : 0;
      this.name = json.has("name") ? json.optString("name") : "root";
      this.children = new ArrayList<CategoryItem>();

      if (json.has("category")) {
        JSONArray subCategories = json.optJSONArray("category");
        for (int i = 0; i < subCategories.length(); i++) {
          JSONObject category = subCategories.optJSONObject(i);
          this.children.add(new CategoryItem(category.toString()));
        }
      }
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public CategoryItem findById(int id) {
    if (this.id == id) {
      return this;
    }

    for (CategoryItem child : this.children) {
      CategoryItem result = child.findById(id);
      if (result != null) {
        return result;
      }
    }

    return null;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public boolean hasChildren() {
    return this.children.size() > 0;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public ArrayList<Integer> getChildrenIds() {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < this.children.size(); i++) {
      result.add(this.children.get(i).getId());
    }
    return result;
  }

  public ArrayList<String> getChildrenNames() {
    ArrayList<String> result = new ArrayList<String>();
    for (int i = 0; i < this.children.size(); i++) {
      result.add(this.children.get(i).getName());
    }
    return result;
  }
}
