package ru.lepescin.restaurants.voting.to;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class DishTo extends BaseTo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDate date;

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Range(min = 1)
    @NotNull
    private Integer price; // in cents

    public DishTo() {
    }

    public DishTo(Integer id, LocalDate date, String name, Integer price) {
        super(id);
        this.date = date;
        this.name = name;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", date=" + date +
                ", name=" + name +
                ", price=" + price +
                '}';
    }
}
