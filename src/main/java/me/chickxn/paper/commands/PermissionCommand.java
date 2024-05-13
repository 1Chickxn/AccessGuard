package me.chickxn.paper.commands;

import me.chickxn.paper.PaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.awt.print.Paper;
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
                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Information about §9" + playerName);
                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Group §8- §9" + user.getGroupName());
                commandSender.sendMessage(user.getPermissions().toString());


            }
        }
        return false;
    }

    public void sendHelp(CommandSender commandSender) {
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "available commands");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/permission player §8(§9PLAYER§8) §8- §7information about the player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/permission player §8(§9PLAYER§8) §7set §8(§9GROUP§8) §8- §7set the group for the player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/permission player §8(§9PLAYER§8) §7add §8(§9PERMISSION§8) §8- §7add a custom permission to player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/permission player §8(§9PLAYER§8) §7remove §8(§9PERMISSION§8) §8- §7remove a custom permission to player§8!");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("group");
            completions.add("player");
        } else if (args.length == 2) {
            if ("group".equalsIgnoreCase(args[0])) {
                completions.add("admin");
                completions.add("default");
            } else if ("player".equalsIgnoreCase(args[0])) {
                for (Player player : PaperPlugin.getInstance().getServer().getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }
        } else if (args.length == 3) {
            if ("group".equalsIgnoreCase(args[0])) {
                completions.add("create");
                completions.add("delete");
                completions.add("remove");
                completions.add("add");
                completions.add("setprefix");
                completions.add("setid");
                completions.add("setnamecolor");
            } else if ("player".equalsIgnoreCase(args[0])) {
                completions.add("set");
                completions.add("add");
                completions.add("remove");
            }
        } else if (args.length == 4) {
            if ("set".equalsIgnoreCase(args[0])) {
                completions.add("admin");
                completions.add("default");
            }
        }
        String currentArg = args[args.length - 1].toLowerCase();
        List<String> filteredCompletions = new ArrayList<>();
        for (String completion : completions) {
            if (completion.toLowerCase().startsWith(currentArg)) {
                filteredCompletions.add(completion);
            }
        }
        return filteredCompletions;
    }
}
