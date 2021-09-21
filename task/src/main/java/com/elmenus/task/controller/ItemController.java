package com.elmenus.task.controller;


import com.elmenus.task.model.Item;
import com.elmenus.task.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items/")
public class ItemController {
    /*
    Item removeItem(String itemName);
     */

    @Autowired
    ItemService itemService;


    @GetMapping("get-item/{itemName}")
    public ResponseEntity<Item> getItemByName(@PathVariable String itemName){
        return ResponseEntity.ok(itemService.getItemByName(itemName));
    }

    @GetMapping("availability/{ItemName}")
    public ResponseEntity<Boolean> getItemAvailability(@PathVariable String ItemName){
        return ResponseEntity.ok(itemService.getItemAvailability(ItemName));
    }

    @PostMapping("/add")
    public ResponseEntity addItem(@RequestBody Item item){
        return ResponseEntity.ok(itemService.addItem(item));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Item> deleteItem(@PathVariable String itemName){
        return ResponseEntity.ok(itemService.removeItem(itemName));
    }

    @PostMapping("/update")
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        return ResponseEntity.ok(itemService.updateItem(item));
    }
}
