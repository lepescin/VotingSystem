package ru.lepescin.restaurants.voting.web.controller.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lepescin.restaurants.voting.model.Vote;
import ru.lepescin.restaurants.voting.service.VoteService;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.lepescin.restaurants.voting.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/profile/votes";
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    VoteService service;

    @PostMapping(value = "/{restId}")
    public ResponseEntity<Vote> createWithLocation(@PathVariable int restId) {
        log.info("create vote from user {} for restaurant {}", authUserId(), restId);
        Vote created = service.create(authUserId(), restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/history")
    public List<Vote> getAll() {
        log.info("history of votes for user {}", authUserId());
        return service.getAll(authUserId());
    }

    @GetMapping
    public Vote get() {
        LocalDate date = LocalDate.now();
        log.info("get today vote for user {}", authUserId());
        return service.get(date, authUserId());
    }

    @GetMapping("/{restId}")
    public List<Vote> get(@PathVariable int restId) {
        LocalDate date = LocalDate.now();
        log.info("get today votes for restaurant {}", restId);
        return service.getVotesForRestaurantOnDate(date, restId);
    }
}
