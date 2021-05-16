package ru.lepescin.restaurants.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.lepescin.restaurants.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.voteDate=:date AND v.user.id=:userId")
    int delete(@Param("date") LocalDate date, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.voteDate DESC")
    List<Vote> getAll(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.voteDate=:date")
    Vote getByDate(@Param("date") LocalDate date, @Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restId AND v.voteDate=:date")
    List<Vote> getVotesForRestaurantOnDate(@Param("date") LocalDate date, @Param("restId") int restId);
}
