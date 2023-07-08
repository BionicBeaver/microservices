package com.tutorial.userservice.feignclients;

import com.tutorial.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="car-service")
public interface CarFeignClient {

    @PostMapping("/car/save")
    public Car save(@RequestBody Car car);

    @GetMapping("/car/getByUserId/{userId}")
    public List<Car> getCars(@PathVariable("userId") int userId);
}
