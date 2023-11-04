package com.constellation.backend.itemService;

import java.util.ArrayList;
import java.util.List;

public class ItemDao {
	private static List<Item> items = new ArrayList<Item>();
	public void addItem(Item item) {
		items.add(item);
	}
	public List<Item> getItems() {
		if (items.size() == 0) {
			Item newItem = new Item(1, "Bob");
			items.add(newItem);
		}
		return items;
	}
}
