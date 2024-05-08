package me.chickxn.global.group;

import dev.httpmarco.evelon.MariaDbLayer;
import dev.httpmarco.evelon.Repository;

import java.util.List;

public class GroupHandler {

    private final Repository<Groups> repository;

    public GroupHandler() {
        this.repository = Repository.build(Groups.class).withLayer(MariaDbLayer.class).build();
    }

    public Groups getGroup(String groupName) {
        return repository.query(MariaDbLayer.class).find().stream().filter(it -> it.getGroupName().equals(groupName)).findFirst().orElse(null);
    }

    public void createGroupIfNotExists(String groupName) {
        if (repository.query(MariaDbLayer.class).find().stream().anyMatch(it -> it.getGroupName().equals(groupName)))
            return;
        repository.query().create(new Groups(groupName, 0, "§a" + groupName, "", "§a", List.of()));
    }

    public void updateGroup(Groups group) {
        repository.query().update(group);
    }
}
