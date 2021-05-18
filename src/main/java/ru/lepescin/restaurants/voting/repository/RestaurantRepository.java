package ru.lepescin.restaurants.voting.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.lepescin.restaurants.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantRepository {
    private static final Sort SORT_BY_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudRestaurantRepository crudRepository;

    public RestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew() && get(restaurant.getId()) == null) {
            return null;
        }
        return crudRepository.save(restaurant);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public Restaurant get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return crudRepository.findAll(SORT_BY_NAME);
    }

    public List<Restaurant> getAllWithMenuOfDay(LocalDate date) {
        return crudRepository.getAllWithMenuOfDay(date);
    }

    public List<Restaurant> getAllWithVotesOfDay(LocalDate date) {
        return crudRepository.getAllWithVotesOfDay(date);
    }
}
