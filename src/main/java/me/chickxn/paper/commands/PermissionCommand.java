package me.chickxn.paper.commands;

import me.chickxn.paper.PaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PermissionCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(commandSender);
        } else if (args.length == 2) {
            String groupName = args[1];
            String playerName = args[1];
            if (args[0].equalsIgnoreCase("player")) {
                UUID uuid = UUID.fromString(PaperPlugin.getInstance().getUuidFetcher().getUUID(playerName));
                var user = PaperPlugin.getInstance().getUserHandler().getUser(uuid);
                if (user != null) {
                    String permissionString = String.join("§7, §b", user.getPermissions());
                    commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Information about §b" + playerName);
                    commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Group §8- §b" + user.getGroupName());
                    commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Permissions §8- §8(§b" + permissionString + "§8)");
                } else {
                    commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "the player ist not registerd");
                }
            }
        }
        return false;
    }

    public void sendHelp(CommandSender commandSender) {
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "available commands");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/permission player §8(§bPLAYER§8) §8- §7information about the player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/permission player §8(§bPLAYER§8) §7set §8(§bGROUP§8) §8- §7set the group for the player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/permission player §8(§bPLAYER§8) §7add §8(§bPERMISSION§8) §8- §7add a custom permission to player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/permission player §8(§bPLAYER§8) §7remove §8(§bPERMISSION§8) §8- §7remove a custom permission to player§8!");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        List<String> sugesstions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("permission"))  {
            if (args.length == 1) {
                sugesstions.add("player");
                sugesstions.add("group");
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("player")) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        sugesstions.add(player.getName());
                    });
                }
            }
        }
        return sugesstions;
    }
}
