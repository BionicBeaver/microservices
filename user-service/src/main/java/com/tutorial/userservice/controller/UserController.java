package com.tutorial.userservice.controller;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/save")
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackMethodGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallbackMethodSaveCar")
    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId, @RequestBody Car car) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveCar(userId, car));
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackMethodGetBikes")
    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes = userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }

    @CircuitBreaker(name = "bikesCB", fallbackMethod = "fallbackMethodSaveBike")
    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId, @RequestBody Bike bike) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveBike(userId, bike));
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallbackMethodGetAll")
    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") int userId) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getUserAndVehicles(userId));
    }

    private ResponseEntity<List<Car>> fallbackMethodGetCars(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario: " + userId + " tiene los coches en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Car> fallbackMethodSaveCar(@PathVariable("userId") int userId, @RequestBody Car car, RuntimeException e) {
        return new ResponseEntity("El usuario: " + userId + " no tiene dinero para coches", HttpStatus.OK);
    }

    private ResponseEntity<List<Bike>> fallbackMethodGetBikes(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario: " + userId + " tiene las motos en el taller", HttpStatus.OK);
    }

    private ResponseEntity<Bike> fallbackMethodSaveBike(@PathVariable("userId") int userId, @RequestBody Bike bike, RuntimeException e) {
        return new ResponseEntity("El usuario: " + userId + " no tiene dinero para motos", HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallbackMethodGetAll(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario" + userId + " tiene los vehiculos en el taller", HttpStatus.OK);
    }
}
