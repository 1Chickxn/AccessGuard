package me.chickxn.paper;

import lombok.Getter;
import me.chickxn.global.group.GroupHandler;
import me.chickxn.global.user.UserHandler;
import me.chickxn.paper.handler.PaperUserHandler;
import me.chickxn.paper.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PaperPlugin extends JavaPlugin {

    @Getter
    private static PaperPlugin instance;

    private UserHandler userHandler;
    private GroupHandler groupHandler;
    private PaperUserHandler paperUserHandler;

    private final String prefix = "§8▶▷ §bVynl §8✒ §7";

    @Override
    public void onEnable() {
        instance = this;

        this.paperUserHandler = new PaperUserHandler();
        this.userHandler = new UserHandler();
        this.groupHandler = new GroupHandler();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
