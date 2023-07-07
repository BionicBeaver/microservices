package com.tutorial.carservice.controller;

import com.tutorial.carservice.entity.Car;
import com.tutorial.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Car>> getAll() {
        List<Car> users = carService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getCarById/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") Integer id) {
        Car car = carService.getCarById(id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping("/save")
    public ResponseEntity<Car> save(@RequestBody Car car) {
        return ResponseEntity.ok(carService.save(car));
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<List<Car>> getByUserId(@PathVariable("userId") int userId) {
        List<Car> users = carService.byUserId(userId);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }
}
