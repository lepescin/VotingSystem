package ru.lepescin.restaurants.voting.repository;

import org.springframework.stereotype.Repository;
import ru.lepescin.restaurants.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VoteRepository {

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public VoteRepository(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository,
                          CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Vote save(Vote vote, int userId, int restaurantId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setVoteDate(LocalDate.now());
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.findById(restaurantId).orElse(null));
        return crudVoteRepository.save(vote);
    }

    public boolean delete(LocalDate date, int userId) {
        return crudVoteRepository.delete(date, userId) != 0;
    }

    public Vote get(int id, int userId) {
        return crudVoteRepository
                .findById(id)
                .filter(vote -> vote.getUser().getId() == userId)
                .orElse(null);
    }

    public List<Vote> getAll(int userId) {
        return crudVoteRepository.getAll(userId);
    }

    public Vote getByDate(LocalDate date, int userId) {
        return crudVoteRepository.getByDate(date, userId);
    }

    public List<Vote> getVotesForRestaurantOnDate(LocalDate date, int restId) {
        return crudVoteRepository.getVotesForRestaurantOnDate(date, restId);
    }
}
