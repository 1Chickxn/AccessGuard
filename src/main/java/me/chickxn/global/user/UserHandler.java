package me.chickxn.global.user;

import dev.httpmarco.evelon.MariaDbLayer;
import dev.httpmarco.evelon.Repository;

import java.util.*;

public class UserHandler {

    private final Repository<User> repository;

    public UserHandler() {
        this.repository = Repository.build(User.class).withLayer(MariaDbLayer.class).withId("users").build();
    }

    public User getUser(UUID uuid) {
        return repository.query().match("uuid", uuid).findFirst();
    }

    public void createUserIfNotExists(UUID uuid, String groupName) {
        if(repository.query().match("uuid", uuid).exists()) return;
        repository.query().create(new User(uuid, groupName, List.of("module.test")));
    }

    public boolean exists(UUID uuid) {
        return repository.query().match("uuid", uuid).exists();
    }

    public void updateUser(User user) {
        repository.query().update(user);
    }
}