package com.tutorial.userservice.controller;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.service.UserService;
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

    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId) {
        User user=userService.getUserById(userId);
        if(user== null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars=userService.getCars(userId);
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/bikes/{userId}")
    public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId) {
        User user=userService.getUserById(userId);
        if(user== null) {
            return ResponseEntity.notFound().build();
        }
        List<Bike> bikes=userService.getBikes(userId);
        return ResponseEntity.ok(bikes);
    }

    @PostMapping("/savecar/{userId}")
    public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId,@RequestBody Car car) {
       if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok(userService.saveCar(userId,car));
    }

    @PostMapping("/savebike/{userId}")
    public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId,@RequestBody Bike bike) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.saveBike(userId,bike));
    }

    @GetMapping("/getAll/{userId}")
    public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable("userId") int userId) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userService.getUserAndVehicles(userId));
    }
}
