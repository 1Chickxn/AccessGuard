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
        if (!PaperPlugin.getInstance().getGroupHandler().exists(PaperPlugin.getInstance().getUserHandler().getUser(player.getUniqueId()).getGroupName())) {
            PaperPlugin.getInstance().getUserHandler().getUser(player.getUniqueId()).setGroupName(PaperPlugin.getInstance().getPaperConfiguration().getDefaultGroup());
        }
        PaperPlugin.getInstance().getPaperUserHandler().initGroupPermission(player);
        PaperPlugin.getInstance().getPaperUserHandler().initPlayerPermission(player);
    }
}
