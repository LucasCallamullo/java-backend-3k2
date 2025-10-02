package spring_food.demo;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import spring_food.demo.loader.CsvImporterService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(CsvImporterService importer) {
		return args -> {
			URL folderPath = DemoApplication.class.getResource("/files/foods.csv");
			if (folderPath != null) {
				File file = Paths.get(folderPath.toURI()).toFile();
				importer.importFromCsv(file);   	// llamás al nuevo servicio
				System.out.println("✅ Foods loaded from CSV!");
			} else {
				throw new IllegalArgumentException("Archivo foods.csv inexistente");
			}
		};
	}

}
