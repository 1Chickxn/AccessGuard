package me.chickxn.paper;

import lombok.Getter;
import me.chickxn.global.user.UserHandler;
import me.chickxn.paper.listener.PlayerJoinListener;
import net.http.aeon.Aeon;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class PaperPlugin extends JavaPlugin {

    @Getter
    private static PaperPlugin instance;

    private UserHandler userHandler;
    //private PaperConfiguration paperConfiguration;

    @Override
    public void onEnable() {
        instance = this;
        this.userHandler = new UserHandler();
        //this.paperConfiguration = Aeon.insert(new PaperConfiguration());

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
