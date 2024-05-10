package me.chickxn.global.user;

import dev.httpmarco.evelon.MariaDbLayer;
import dev.httpmarco.evelon.Repository;

import java.util.List;
import java.util.UUID;

public class UserHandler {

    private final Repository<User> repository;

    public UserHandler() {
        this.repository = Repository.build(User.class).withLayer(MariaDbLayer.class).withId("users").build();
    }

    public User getUser(UUID uuid) {
        return repository.query().match("uuid", uuid).findFirst();
    }

    public void createUserIfNotExists(UUID uuid) {
        if (repository.query().match("uuid", uuid).exists()) return;
        repository.query().create(new User(uuid, "default", List.of("test.permission")));
    }

    public void updateUser(User user) {
        repository.query().update(user);
    }
}