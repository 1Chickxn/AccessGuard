package me.chickxn.global.group;

import dev.httpmarco.evelon.MariaDbLayer;
import dev.httpmarco.evelon.Repository;
import me.chickxn.global.user.User;

import java.util.List;
import java.util.UUID;

public class GroupHandler {

    private final Repository<Groups> repository;

    public GroupHandler() {
        this.repository = Repository.build(Groups.class).withLayer(MariaDbLayer.class).withId("groups").build();
    }

    public Groups getGroups(String groupName) {
        return repository.query().match("groupName", groupName).findFirst();
    }

    public List<Groups> getAllGroups() {
        return repository.query().find();
    }

    public void createGroupIfNotExists(String groupName) {
        if(repository.query().match("groupName", groupName).exists()) return;
        repository.query().create(new Groups(groupName, "§7", "", "§7", 1, List.of("")));
    }

    public boolean exists(String groupName) {
        return repository.query().match("groupName", groupName).exists();
    }

    public void updateUser(Groups groups) {
        repository.query().update(groups);
    }
}
