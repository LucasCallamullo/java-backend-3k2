package spring_food.demo.controllers;

import org.springframework.web.bind.annotation.*;
import spring_food.demo.entities.Food;
import spring_food.demo.services.FoodService;

import java.util.List;

@RestController
@RequestMapping("/foods")
public class FoodController {

    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @GetMapping
    public List<Food> getAllFoods() {
        return service.getAllFoods();
    }

    @PostMapping
    public Food addFood(@RequestBody Food food) {
        return service.saveFood(food);
    }
}
