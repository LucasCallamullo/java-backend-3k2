package spring_food.demo;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import spring_food.demo.services.FoodService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(FoodService foodService) {
		return args -> {
			URL folderPath = DemoApplication.class.getResource("/files/foods.csv");
			if (folderPath != null) {
				File file = Paths.get(folderPath.toURI()).toFile();
				foodService.bulkInsert(file);
				System.out.println("✅ Foods loaded from CSV!");
			} else {
				throw new IllegalArgumentException("Archivo foods.csv inexistente");
			}
		};
	}
}
