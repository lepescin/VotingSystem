package ru.lepescin.restaurants.voting.web.controller.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lepescin.restaurants.voting.model.Dish;
import ru.lepescin.restaurants.voting.service.DishService;

import javax.validation.Valid;
import java.net.URI;

import static ru.lepescin.restaurants.voting.util.ValidationUtil.assureIdConsistent;
import static ru.lepescin.restaurants.voting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishRestController {
    static final String REST_URL = "/rest/admin/restaurants";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService dishService;

    @PostMapping(value = "/{restaurantId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create {} for restaurant with id={}", dish, restaurantId);
        checkNew(dish);
        Dish created = dishService.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + restaurantId + "/dishes" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restaurantId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int dishId) {
        log.info("delete dish {} of restaurant with id={}", dishId, restaurantId);
        dishService.delete(dishId, restaurantId);
    }

    @PutMapping(value = "/{restaurantId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int restaurantId, @PathVariable int dishId) {
        assureIdConsistent(dish, dishId);
        log.info("update {} with id={} for restaurant with id={}", dish, dishId, restaurantId);
        dishService.update(dish, restaurantId);
    }
}
