package ru.lepescin.restaurants.voting.web.controller.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lepescin.restaurants.voting.model.Dish;
import ru.lepescin.restaurants.voting.service.DishService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    protected Logger log = LoggerFactory.getLogger(DishRestController.class);
    static final String REST_URL = "/rest/profile/menu";

    @Autowired
    private DishService service;

    @GetMapping(value = "/{restaurantId}")
    public List<Dish> getMenu(@PathVariable int restaurantId) {
        log.info("get menu for date {} from restaurant {}", LocalDate.now(), restaurantId);
        return service.getMenu(LocalDate.now(), restaurantId);
    }
}
