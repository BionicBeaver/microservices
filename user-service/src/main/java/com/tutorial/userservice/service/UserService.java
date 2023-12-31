package com.tutorial.userservice.service;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.feignclients.BikeFeignClient;
import com.tutorial.userservice.feignclients.CarFeignClient;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CarFeignClient carFeignClient;
    @Autowired
    private BikeFeignClient bikeFeignClient;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<Car> getCars(int userId) {
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/getByUserId/" + userId, List.class);
        return cars;
    }

    public List<Bike> getBikes(int userId) {
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bike/getByUserId/" + userId, List.class);
        return bikes;
    }

    public Car saveCar(int userId,Car car) {
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Bike saveBike(int userId,Bike bike) {
        bike.setUserId(userId);
        return bikeFeignClient.save(bike);
    }

    public Map<String,Object> getUserAndVehicles(int userId) {
        Map<String,Object> result=new HashMap<>();
        User user=userRepository.findById(userId).orElse(null);
        if(user== null) {
            result.put("Mensaje","No existe el usuario");
        }
        result.put("User",user);
        List<Car> cars=carFeignClient.getCars(userId);
        if(cars.isEmpty()) {
            result.put("Cars","Este usuario no tiene coches");
        }else {
            result.put("Cars",cars);
        }
        List<Bike> bikes=bikeFeignClient.getBikes(userId);
        if(bikes.isEmpty()) {
            result.put("Bikes","Este usuario no tiene Motos");
        }else {
            result.put("Bikes",bikes);
        }
        return result;
    }
}
