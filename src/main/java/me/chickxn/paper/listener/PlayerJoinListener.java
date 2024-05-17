package me.chickxn.paper.listener;

import me.chickxn.paper.PaperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        PaperPlugin.getInstance().getUserHandler().createUserIfNotExists(player.getUniqueId(), PaperPlugin.getInstance().getPaperConfiguration().getDefaultGroup());
        PaperPlugin.getInstance().getPaperUserHandler().initPlayerPermission(player);
        PaperPlugin.getInstance().getPaperUserHandler().initGroupPermission(player);
    }
}
