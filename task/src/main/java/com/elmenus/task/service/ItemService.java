package com.elmenus.task.service;

import com.elmenus.task.model.Item;

public interface ItemService {

    Item getItemByName(String itemName);
    Boolean getItemAvailability(String itemName);
    Item addItem(Item item);
    Item removeItem(String itemName);

    Item updateItem(Item item);

}
