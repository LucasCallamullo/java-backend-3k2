package spring_food.demo.services;

import org.springframework.stereotype.Service;

import spring_food.demo.entities.Diet;
import spring_food.demo.repositories.DietRepository;
import spring_food.demo.services.interfaces.BaseService;


@Service
public class DietService extends BaseService<Diet, Long> {
    public DietService(DietRepository repository) {
        super(repository);
    }
}


/* 
@Service
public class DietService {
    private final DietRepository repository;

    public DietService(DietRepository repository) {
        this.repository = repository;
    }

    public List<Diet> getAllList() {
        return repository.findAll();
    }

    public Diet save(Diet food) {
        return this.repository.save(food);
    }

    public List<Diet> saveAll(Iterable<Diet> foods) {
        // método ya viene de JpaRepository
        return repository.saveAll(foods);  
    }
}
*/