package ru.lepescin.restaurants.voting.repository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.lepescin.restaurants.voting.model.User;

import java.util.List;

@Repository
public class UserRepository {
    private static final Sort SORT_BY_EMAIL = Sort.by(Sort.Direction.ASC, "email");

    private final CrudUserRepository crudRepository;

    public UserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public User save(User user) {
        if (!user.isNew() && get(user.getId()) == null) {
            return null;
        }
        return crudRepository.save(user);
    }

    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return crudRepository.findAll(SORT_BY_EMAIL);
    }
}
