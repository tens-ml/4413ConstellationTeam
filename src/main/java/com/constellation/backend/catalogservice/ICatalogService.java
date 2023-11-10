package com.constellation.backend.catalogservice;

import java.util.List;

public interface ICatalogService {
    CatalogItem createItem(CatalogItem item);
    List<CatalogItem> getItems();
    CatalogItem getItem(int itemId);
    CatalogItem updateItem(CatalogItem updatedItem);
    boolean deleteItem(int itemId);
}
