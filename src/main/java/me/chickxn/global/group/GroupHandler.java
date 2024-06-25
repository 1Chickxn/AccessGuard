package me.chickxn.global.group;

import dev.httpmarco.evelon.MariaDbLayer;
import dev.httpmarco.evelon.Repository;
import dev.httpmarco.evelon.sql.h2.H2Layer;

import java.util.List;

public class GroupHandler {

    private final Repository<Groups> repository;

    public GroupHandler() {
        this.repository = Repository.build(Groups.class).withLayer(H2Layer.class).withLayer(MariaDbLayer.class).withId("groups").build();
    }

    public Groups getGroups(String groupName) {
        return repository.query().match("groupName", groupName).findFirst();
    }

    public List<Groups> getAllGroups() {
        return repository.query().find();
    }

    public void createGroupIfNotExists(String groupName) {
        if (repository.query().match("groupName", groupName).exists()) return;
        repository.query().create(new Groups(groupName, "§7", "§7", "§7", 1, List.of("test.module")));
    }

    public void deleteGroup(String groupName) {
        repository.query().match("groupName", groupName).delete();
    }

    public boolean exists(String groupName) {
        return repository.query().match("groupName", groupName).exists();
    }

    public void updateGroup(Groups groups) {
        repository.query().update(groups);
    }
}
