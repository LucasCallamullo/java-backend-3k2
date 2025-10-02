package spring_food.demo.services;

import org.springframework.stereotype.Service;

import spring_food.demo.entities.Origin;
import spring_food.demo.repositories.OriginRepository;
import spring_food.demo.services.interfaces.BaseService;

@Service
public class OriginService extends BaseService<Origin, Long> {
    public OriginService(OriginRepository repository) {
        super(repository);
    }
}
