package com.elmenus.task.service;

import com.elmenus.task.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ItemServiceImpl implements ItemService {


    private static Map<String, Item> cachedItems = Collections.synchronizedMap(new HashMap<>());
    private static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);


    @Override
    public Item getItemByName(String itemName) {
        return cachedItems.get(itemName);
    }

    @Override
    public Boolean getItemAvailability(String itemName) {
        Item item = getItemByName(itemName);
        if (item != null) {
            if (item.getStock()>0) {
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Item addItem(Item item) {
        return cachedItems.put(item.getName(), item);
    }

    @Override
    public Item removeItem(String itemName) {
        Item item = getItemByName(itemName);
        if (item != null) {
            return cachedItems.remove(itemName);
        } else {
            return null;
        }

    }

    @Override
    public Item updateItem(Item item) {
        if (item.getName()!=null){
            Item oldItem=cachedItems.get(item.getName());
            if (oldItem!=null){
                oldItem.setName(item.getName());
                oldItem.setPrice(item.getPrice());
                oldItem.setStock(item.getStock());
                cachedItems.put(oldItem.getName(),item);
            }else {
                cachedItems.put(item.getName(),item);
            }
            return item;
        }else {
            return null;
        }
    }

    @PostConstruct
    private void loadAllItems() {
        // assume getting from database
        // at this task will get from orderConfigurations.json file
        TypeReference<List<Item>> typeReference = new TypeReference<List<Item>>() {
        };

        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/items.json");
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
            List<Item> items = mapper.readValue(inputStream, typeReference);
            for (Item item : items) {
                cachedItems.put(item.getName(), item);
            }
            logger.info("items is added ");
        } catch (IOException e) {
            logger.error("Unable to add items : {}", e.getMessage());
        }

    }
}
