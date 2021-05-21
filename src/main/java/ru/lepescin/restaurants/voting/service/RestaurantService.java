package ru.lepescin.restaurants.voting.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.lepescin.restaurants.voting.model.Restaurant;
import ru.lepescin.restaurants.voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.lepescin.restaurants.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {
    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = {"restaurants", "allRestaurantsWithMenuOfDay"}, allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    @Cacheable("allRestaurantsWithMenuOfDay")
    public List<Restaurant> getAllWithMenuOfDay() {
        return repository.getAllWithMenuOfDay(LocalDate.now());
    }

    @CacheEvict(value = {"restaurants", "allRestaurantsWithMenuOfDay"}, allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    @CacheEvict(value = {"restaurants", "allRestaurantsWithMenuOfDay"}, allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }

    public List<Restaurant> getAllWithVotesOfDay() {
        return repository.getAllWithVotesOfDay(LocalDate.now());
    }
}
