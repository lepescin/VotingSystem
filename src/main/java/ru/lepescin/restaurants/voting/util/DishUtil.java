package ru.lepescin.restaurants.voting.util;

import ru.lepescin.restaurants.voting.model.Dish;
import ru.lepescin.restaurants.voting.to.DishTo;

public class DishUtil {
    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getDate(), dishTo.getName(), dishTo.getPrice());
    }
}
