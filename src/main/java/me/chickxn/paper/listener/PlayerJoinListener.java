package me.chickxn.paper.listener;

import me.chickxn.paper.PaperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        PaperPlugin.getInstance().getUserHandler().createUserIfNotExists(player.getUniqueId());
        var user = PaperPlugin.getInstance().getUserHandler().getUser(player.getUniqueId());
        for (String playerPermissions : user.getPermissions()) {
            player.addAttachment(PaperPlugin.getInstance(), playerPermissions, true);
        }
    }
}
