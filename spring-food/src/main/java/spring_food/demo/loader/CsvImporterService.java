package spring_food.demo.loader;

import org.springframework.stereotype.Service;

import spring_food.demo.entities.Food;
import spring_food.demo.entities.Category;
import spring_food.demo.entities.Origin;
import spring_food.demo.entities.Diet;

import spring_food.demo.services.FoodService;
import spring_food.demo.services.CategoryService;
import spring_food.demo.services.OriginService;
import spring_food.demo.services.DietService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


@Service
public class CsvImporterService {

    private final FoodService foodService;
    private final CategoryService categoryService;
    private final OriginService originService;
    private final DietService dietService;

    private final Map<String, Category> dictCategory = new HashMap<>();
    private final Map<String, Origin> dictOrigin = new HashMap<>();
    private final Map<String, Diet> dictDiet = new HashMap<>();
    private final List<Food> foods = new ArrayList<>();

    public CsvImporterService(FoodService foodService, CategoryService categoryService,
                              OriginService originService, DietService dietService) {
        this.foodService = foodService;
        this.categoryService = categoryService;
        this.originService = originService;
        this.dietService = dietService;
    }

    public void importFromCsv(File file) throws IOException {
        Files.lines(Paths.get(file.toURI()))
                .skip(1) // header
                .forEach(this::procesarLinea);

        // Persisto en orden: primero maestras, luego foods
        categoryService.saveAll(dictCategory.values());
        originService.saveAll(dictOrigin.values());
        dietService.saveAll(dictDiet.values());
        foodService.saveAll(foods);
    }

    private void procesarLinea(String linea) {
        String[] tokens = linea.split(",");

        String name = tokens[0].replace("\"", "").trim();
        double proteins = Double.parseDouble(tokens[1].replace("\"", "").trim());
        double carbohydrates = Double.parseDouble(tokens[2].replace("\"", "").trim());
        double fats = Double.parseDouble(tokens[3].replace("\"", "").trim());
        double calories = Double.parseDouble(tokens[4].replace("\"", "").trim());

        String categoryStr = tokens[5].replace("\"", "").trim();
        String originStr = tokens[6].replace("\"", "").trim();
        String dietStr = tokens[7].replace("\"", "").trim();

        Category category;
        if (this.dictCategory.get(categoryStr) != null) {
            category = this.dictCategory.get(categoryStr);
        } else {
            category = new Category(categoryStr);
            this.dictCategory.put(categoryStr, category);
        }

        Origin origin;
        if (this.dictOrigin.get(originStr) != null) {
            origin = this.dictOrigin.get(originStr);
        } else {
            origin = new Origin(originStr);
            this.dictOrigin.put(originStr, origin);
        }

        Diet diet;
        if (this.dictDiet.get(dietStr) != null) {
            diet = this.dictDiet.get(dietStr);
        } else {
            diet = new Diet(dietStr);
            this.dictDiet.put(dietStr, diet);
        }

        

        Food food = new Food(null, name, proteins, carbohydrates, fats, calories, category, origin, diet);
        foods.add(food);
    }
}
/* ver despues estra forma
        Category category = dictCategory.computeIfAbsent(categoryStr, c -> new Category(null, c, new HashSet<>()));
        Origin origin = dictOrigin.computeIfAbsent(originStr, o -> new Origin(null, o, new HashSet<>()));
        Diet diet = dictDiet.computeIfAbsent(d -> new Diet(null, d, new HashSet<>()));
        */