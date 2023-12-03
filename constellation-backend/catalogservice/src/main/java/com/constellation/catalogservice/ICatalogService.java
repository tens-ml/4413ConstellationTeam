package com.constellation.catalogservice;

import java.util.List;

public interface ICatalogService {
    CatalogItem createItem(CatalogItem item);
    List<CatalogItem> getItems(String filter);
    CatalogItem getItem(int itemId);
    CatalogItem updateItem(CatalogItem updatedItem);
    boolean deleteItem(int itemId);
}
