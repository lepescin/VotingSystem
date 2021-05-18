package ru.lepescin.restaurants.voting.web.controller.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lepescin.restaurants.voting.model.Dish;
import ru.lepescin.restaurants.voting.model.Restaurant;
import ru.lepescin.restaurants.voting.service.DishService;
import ru.lepescin.restaurants.voting.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest/restaurants";

    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private DishService dishService;

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping(value = "/{restId}")
    public List<Dish> getMenu(@PathVariable int restId) {
        log.info("get menu for date {} from restaurant {}", LocalDate.now(), restId);
        return dishService.getMenu(LocalDate.now(), restId);
    }

    @GetMapping("/{restId}/dishes")
    public List<Dish> getAllDishes(@PathVariable int restId) {
        log.info("get all dishes of restaurant {}", restId);
        return dishService.getAll(restId);
    }

    @GetMapping("/menus")
    public List<Restaurant> getAllTodayMenus() {
        log.info("get all today menus");
        return restaurantService.getAllWithMenuOfDay();
    }

    @GetMapping("/{restId}/dishes/{dishId}")
    public Dish getDish(@PathVariable int restId, @PathVariable int dishId) {
        log.info("get dish {} of restaurant {}", dishId, restId);
        return dishService.get(dishId, restId);
    }

    @GetMapping("/votes")
    public List<Restaurant> getAllWithVotesOfDay() {
        log.info("get all restaurants with today votes");
        return restaurantService.getAllWithVotesOfDay();
    }
}
