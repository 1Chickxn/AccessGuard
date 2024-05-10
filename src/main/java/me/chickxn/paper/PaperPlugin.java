package me.chickxn.paper;

import lombok.Getter;
import me.chickxn.global.fetcher.UUIDFetcher;
import me.chickxn.global.group.GroupHandler;
import me.chickxn.global.user.UserHandler;
import me.chickxn.paper.commands.PermissionCommand;
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
    private UUIDFetcher uuidFetcher;

    private final String prefix = "§8▶▷ §bVynl §8✒ §7";

    @Override
    public void onEnable() {
        instance = this;

        this.paperUserHandler = new PaperUserHandler();
        this.userHandler = new UserHandler();
        this.groupHandler = new GroupHandler();
        this.uuidFetcher = new UUIDFetcher();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);

        getCommand("permission").setExecutor(new PermissionCommand());
        getCommand("permission").setTabCompleter(new PermissionCommand());

    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
