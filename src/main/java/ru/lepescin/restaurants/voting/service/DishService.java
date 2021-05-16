package ru.lepescin.restaurants.voting.service;


import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.lepescin.restaurants.voting.model.Dish;
import ru.lepescin.restaurants.voting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.lepescin.restaurants.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    public List<Dish> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    public List<Dish> getMenu(LocalDate date, int restaurantId) {
        return repository.getMenu(date, restaurantId);
    }

    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }

    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }
}
