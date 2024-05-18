package me.chickxn.paper;

import dev.httpmarco.evelon.layer.connection.ConnectionAuthenticationPath;
import lombok.Getter;
import me.chickxn.global.fetcher.UUIDFetcher;
import me.chickxn.global.group.GroupHandler;
import me.chickxn.global.user.UserHandler;
import me.chickxn.paper.commands.PermissionCommand;
import me.chickxn.paper.handler.PaperUserHandler;
import me.chickxn.paper.listener.PlayerJoinListener;
import me.chickxn.paper.listener.PlayerQuitListener;
import me.chickxn.paper.loader.ModuleLoader;
import net.http.aeon.Aeon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


@Getter
public class PaperPlugin extends JavaPlugin {

    @Getter
    private static PaperPlugin instance;
    private final String prefix = "§8» §9AccessGuard §8| §7";
    private UserHandler userHandler;
    private GroupHandler groupHandler;
    private PaperUserHandler paperUserHandler;
    private UUIDFetcher uuidFetcher;
    private PaperConfiguration paperConfiguration;
    private ModuleLoader moduleLoader;

    @Override
    public void onEnable() {
        instance = this;

        ConnectionAuthenticationPath.set("plugins/AccessGuard/connection.json");

        this.moduleLoader = new ModuleLoader(new File("plugins/AccessGuard/modules"));

        this.paperConfiguration = Aeon.insert(new PaperConfiguration());

        this.userHandler = new UserHandler();
        this.paperUserHandler = new PaperUserHandler();
        this.groupHandler = new GroupHandler();
        this.uuidFetcher = new UUIDFetcher();

        this.groupHandler.createGroupIfNotExists(paperConfiguration.getDefaultGroup());

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);

        getCommand("accessguard").setExecutor(new PermissionCommand());
        getCommand("accessguard").setTabCompleter(new PermissionCommand());


        Bukkit.getConsoleSender().sendMessage(getPrefix() + "AccessGuard §9successfully §7loaded§8!");
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Author: §91Chickxn §8| §7Version: §9" + this.getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        instance = null;
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "AccessGuard §9successfully §7disabled§8!");
        Bukkit.getConsoleSender().sendMessage(getPrefix() + "Author: §91Chickxn §8| §7Version: §9" + this.getDescription().getVersion());
    }
}
