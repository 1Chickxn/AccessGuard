package me.chickxn.paper.handler;

import lombok.Getter;
import me.chickxn.paper.PaperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PaperUserHandler {

    private final Map<UUID, PermissionAttachment> permissionAttachmentMap;

    public PaperUserHandler() {
        this.permissionAttachmentMap = new HashMap<>();
    }

    private PermissionAttachment initPlayerAttachment(Player player) {
        PermissionAttachment permissionAttachment = player.addAttachment(PaperPlugin.getInstance());
        permissionAttachmentMap.put(player.getUniqueId(), permissionAttachment);
        return permissionAttachment;
    }

    public void initPlayerPermission(Player player) {
        PaperPlugin.getInstance().getUserHandler().createUserIfNotExists(player.getUniqueId(), PaperPlugin.getInstance().getPaperConfiguration().getDefaultGroup());
        PermissionAttachment permissionAttachment = this.initPlayerAttachment(player);
        PaperPlugin.getInstance().getUserHandler().getUser(player.getUniqueId()).getPermissions().forEach(permission -> {
            if (!permission.contains("*")) {
                permissionAttachment.setPermission(permission, true);
            } else {
                player.setOp(true);
            }
        });
    }

    public void initGroupPermission(Player player) {
        PaperPlugin.getInstance().getUserHandler().createUserIfNotExists(player.getUniqueId(), PaperPlugin.getInstance().getPaperConfiguration().getDefaultGroup());
        PermissionAttachment permissionAttachment = this.initPlayerAttachment(player);
        var user = PaperPlugin.getInstance().getUserHandler().getUser(player.getUniqueId());
        if (PaperPlugin.getInstance().getGroupHandler().exists(user.getGroupName())) {
            var group = PaperPlugin.getInstance().getGroupHandler().getGroups(user.getGroupName());
            group.getPermissions().forEach(permission -> {
                if (!permission.contains("*")) {
                    permissionAttachment.setPermission(permission, true);
                } else {
                    player.setOp(true);
                }
            });
        } else {
            user.setGroupName(PaperPlugin.getInstance().getPaperConfiguration().getDefaultGroup());
        }
    }
}
