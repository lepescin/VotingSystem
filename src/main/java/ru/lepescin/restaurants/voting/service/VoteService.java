package ru.lepescin.restaurants.voting.service;

import org.springframework.stereotype.Service;
import ru.lepescin.restaurants.voting.model.Vote;
import ru.lepescin.restaurants.voting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.lepescin.restaurants.voting.util.ValidationUtil.checkNotFoundWithId;
import static ru.lepescin.restaurants.voting.util.ValidationUtil.checkTime;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void delete(LocalDate date, int userId) {
        voteRepository.delete(date, userId);
    }

    public Vote get(LocalDate date, int userId) {
        return voteRepository.getByDate(date, userId);
    }

    public List<Vote> getAll(int userId) {
        return voteRepository.getAll(userId);
    }

    public Vote create(int userId, int restaurantId) {
        Vote vote = voteRepository.getByDate(LocalDate.now(), userId);
        if (vote == null) {
            return voteRepository.save(new Vote(), userId, restaurantId);
        } else {
            checkTime(LocalDateTime.now().toLocalTime());
            return checkNotFoundWithId(voteRepository.save(vote, userId, restaurantId), vote.getId());
        }
    }

    public List<Vote> getVotesForRestaurantOnDate(LocalDate date, int restId) {
        return voteRepository.getVotesForRestaurantOnDate(date, restId);
    }
}
