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
import ru.lepescin.restaurants.voting.to.DishTo;

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

    @PostMapping(value = "/{restId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody DishTo dishTo, @PathVariable int restId) {
        log.info("create {} for restaurant with id={}", dishTo, restId);
        checkNew(dishTo);
        Dish created = dishService.create(dishTo, restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/" + restId + "/dishes" + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{restId}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restId, @PathVariable int dishId) {
        log.info("delete dish {} of restaurant with id={}", dishId, restId);
        dishService.delete(dishId, restId);
    }

    @PutMapping(value = "/{restId}/dishes/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int restId, @PathVariable int dishId) {
        assureIdConsistent(dishTo, dishId);
        log.info("update {} with id={} for restaurant with id={}", dishTo, dishId, restId);
        dishService.update(dishTo, restId);
    }
}
