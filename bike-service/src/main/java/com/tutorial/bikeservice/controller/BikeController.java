package com.tutorial.bikeservice.controller;

import com.tutorial.bikeservice.entity.Bike;
import com.tutorial.bikeservice.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Bike>> getAll() {
        List<Bike> users = bikeService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getBikeById/{id}")
    public ResponseEntity<Bike> getBikeById(@PathVariable("id") Integer id) {
        Bike bike = bikeService.getBikeById(id);
        if (bike == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bike);
    }

    @PostMapping("/save")
    public ResponseEntity<Bike> save(@RequestBody Bike bike) {
        return ResponseEntity.ok(bikeService.save(bike));
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") int userId) {
        List<Bike> users = bikeService.byUserId(userId);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
}
