package spring_food.demo.controllers;

import org.springframework.web.bind.annotation.*;
import spring_food.demo.entities.Food;
import spring_food.demo.services.FoodService;

import java.util.List;

// http://localhost:8080/foods
@RestController
@RequestMapping("/foods")
public class FoodController {

    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @GetMapping
    public List<Food> getAllFoods() {
        return service.getAllList();
    }

    // http://localhost:8080/foods/search?name=
    @GetMapping("/search")
    public List<Food> searchFoods(@RequestParam(required = false) String name) {
        if (name == null || name.isEmpty()) {
            return service.getAllList();
        }
        return service.findByName(name);
    }

    // http://localhost:8080/foods/search?category=
    @GetMapping("/searchByCategory")
    public List<Food> searchByCategory(@RequestParam String category) {
        return service.findByCategory(category);
    }


    @PostMapping
    public Food addFood(@RequestBody Food food) {
        return service.save(food);
    }
}
