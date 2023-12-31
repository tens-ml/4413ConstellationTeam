package com.constellation.catalogservice.controllers;

import com.constellation.catalogservice.model.Item;
import com.constellation.catalogservice.repository.ItemRepository;
import com.constellation.catalogservice.requests.SellItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class CatalogServiceController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Integer id) {
        System.out.println("Getting item with id=" + id);
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isEmpty()) throw new Exception();
            return ResponseEntity.ok(item.get());
        } catch (Exception e) {
            System.out.println("Error while getting item");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found", e);
        }
    }

    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getItems(@RequestParam(required = false) String search) {
        System.out.println("Getting items for search=" + search);
        try {
            return ResponseEntity.ok(itemRepository.findByOptionalSearch(search));
        } catch (Exception e) {
            System.out.println("Error while getting items");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting items", e);
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody SellItemRequest sellItemRequest) {
        System.out.println("Creating item");
        try {
            itemRepository.save(Item.fromSellItemRequest(sellItemRequest));
            return ResponseEntity.ok("Item created");
        } catch (Exception e) {
            System.out.println("Error while creating item");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while creating item", e);
        }
    }
}
