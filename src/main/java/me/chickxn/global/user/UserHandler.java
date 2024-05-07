package me.chickxn.global.user;

import dev.httpmarco.evelon.MariaDbLayer;
import dev.httpmarco.evelon.Repository;
import me.chickxn.paper.PaperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserHandler {

    private final Repository<User> repository;
    private final Map<UUID, PermissionAttachment> playerPermissions = new HashMap<>();

    public UserHandler() {
        this.repository = Repository.build(User.class).withLayer(MariaDbLayer.class).withId("users").build();
    }

    public User getUser(UUID uuid) {
        return repository.query(MariaDbLayer.class).find().stream().filter(it -> it.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public void createUserIfNotExists(UUID uuid) {
        if (repository.query(MariaDbLayer.class).find().stream().anyMatch(it -> it.getUuid().equals(uuid))) return;
        repository.query().create(new User(uuid, "default", List.of("test.permission")));
    }

    public void updateUser(User user) {
        repository.query().update(user);
    }

    private PermissionAttachment initPlayerAttachment(Player player) {
        PermissionAttachment permissionAttachment = player.addAttachment(PaperPlugin.getInstance());
        playerPermissions.put(player.getUniqueId(), permissionAttachment);
        return permissionAttachment;
    }

    public void initPlayerPermission(Player player) {
        this.createUserIfNotExists(player.getUniqueId());
        PermissionAttachment permissionAttachment = this.initPlayerAttachment(player);
        this.getUser(player.getUniqueId()).getPermissions().forEach(permission -> {
            if (!permission.equals("*")) {
                permissionAttachment.setPermission(permission, true);
            } else {
                player.setOp(true);
            }
        });
    }
}