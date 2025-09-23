package spring_food.demo.services;

import org.springframework.stereotype.Service;
import spring_food.demo.entities.Food;
import spring_food.demo.repositories.FoodRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository repository;

    public FoodService(FoodRepository repository) {
        this.repository = repository;
    }

    public List<Food> getAllFoods() {
        return repository.findAll();
    }

    public Food saveFood(Food food) {
        return repository.save(food);
    }

    public void bulkInsert(File fileToImport) throws IOException {
        Files.lines(Paths.get(fileToImport.toURI()))
                .skip(1) // skip header
                .forEach(line -> {
                    Food food = procesarLinea(line);
                    this.repository.save(food);
                });
    }

    private Food procesarLinea(String linea) {
        String[] tokens = linea.split(",");
        Food food = new Food();

        // Trim elimina espacios, replaceAll quita comillas dobles si existen
        String name = tokens[0].trim().replaceAll("\"", "");
        String caloriesStr = tokens[1].trim().replaceAll("\"", "");

        food.setName(name);
        food.setCalories(Double.parseDouble(caloriesStr));

        return food;
    }

}

