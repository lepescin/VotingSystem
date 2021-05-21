package ru.lepescin.restaurants.voting.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.lepescin.restaurants.voting.AuthorizedUser;
import ru.lepescin.restaurants.voting.model.User;
import ru.lepescin.restaurants.voting.repository.UserRepository;
import ru.lepescin.restaurants.voting.to.UserTo;
import ru.lepescin.restaurants.voting.util.UserUtil;

import java.util.List;

import static ru.lepescin.restaurants.voting.util.UserUtil.prepareToSave;
import static ru.lepescin.restaurants.voting.util.ValidationUtil.checkNotFound;
import static ru.lepescin.restaurants.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User with " + email + " not found");
        }
        return new AuthorizedUser(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        return prepareAndSave(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email.toLowerCase()), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        prepareAndSave(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(UserTo userTo) {
        User user = get(userTo.id());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    private User prepareAndSave(User user) {
        return repository.save(prepareToSave(user, passwordEncoder));
    }
}
