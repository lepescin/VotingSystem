package ru.lepescin.restaurants.voting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.lepescin.restaurants.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DishRepository {
    private final CrudDishRepository dishRepository;
    private final CrudRestaurantRepository restaurantRepository;

    @Autowired
    public DishRepository(CrudDishRepository dishRepository, CrudRestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setDate(LocalDate.now());
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    public boolean delete(int id, int restaurantId) {
        return dishRepository.delete(id, restaurantId) != 0;
    }

    public Dish get(int id, int restaurantId) {
        return dishRepository
                .findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    public List<Dish> getMenu(LocalDate date, int restaurantId) {
        return dishRepository.getMenu(date, restaurantId);
    }
}
