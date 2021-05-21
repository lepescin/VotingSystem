package ru.lepescin.restaurants.voting.service;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.lepescin.restaurants.voting.model.Dish;
import ru.lepescin.restaurants.voting.repository.DishRepository;
import ru.lepescin.restaurants.voting.to.DishTo;

import java.time.LocalDate;
import java.util.List;

import static ru.lepescin.restaurants.voting.util.DishUtil.createNewFromTo;
import static ru.lepescin.restaurants.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {
    private final DishRepository repository;

    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "allRestaurantsWithMenuOfDay", allEntries = true)
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

    private Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }

    @CacheEvict(value = "allRestaurantsWithMenuOfDay", allEntries = true)
    public Dish create(DishTo dishTo, int restaurantId) {
        return create(createNewFromTo(dishTo), restaurantId);
    }

    @CacheEvict(value = "allRestaurantsWithMenuOfDay", allEntries = true)
    public Dish create(Dish dish, int restaurantId) {
        return save(dish, restaurantId);
    }

    @CacheEvict(value = "allRestaurantsWithMenuOfDay", allEntries = true)
    public void update(DishTo dishTo, int restaurantId) {
        update(createNewFromTo(dishTo), restaurantId);
    }

    @CacheEvict(value = "allRestaurantsWithMenuOfDay", allEntries = true)
    public void update(Dish dish, int restaurantId) {
        save(dish, restaurantId);
    }
}
