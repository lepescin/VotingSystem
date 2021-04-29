package ru.votingsystem.model;

import java.time.LocalDateTime;

public class Vote {

    private LocalDateTime voteDateTime;

    private User user;

    private Restaurant restaurant;

    public LocalDateTime getVoteDateTime() {
        return voteDateTime;
    }

    public void setVoteDateTime(LocalDateTime voteDateTime) {
        this.voteDateTime = voteDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteDateTime=" + voteDateTime +
                ", user=" + user +
                ", restaurant=" + restaurant +
                '}';
    }
}
