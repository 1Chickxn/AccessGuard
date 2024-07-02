package me.chickxn.paper.commands;

import me.chickxn.global.group.Groups;
import me.chickxn.paper.PaperPlugin;
import me.chickxn.paper.handler.events.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PermissionCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender.hasPermission("accessguard.use")) {
            if (args.length == 0) {
                sendHelp(commandSender);
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("group")) {
                    List<Groups> getAllGroups = PaperPlugin.getInstance().getGroupHandler().getAllGroups();
                    String groups = String.join("§8, §9", getAllGroups.stream().map(Groups::getGroupName).collect(Collectors.toList()));
                    commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "all available groups");
                    commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "§9" + groups);
                }
            } else if (args.length == 2) {
                String groupName = args[1];
                String playerName = args[1];
                UUID uuid = UUID.fromString(PaperPlugin.getInstance().getUuidFetcher().getUUID(playerName));
                var user = PaperPlugin.getInstance().getUserHandler().getUser(uuid);
                if (args[0].equalsIgnoreCase("player")) {
                    if (PaperPlugin.getInstance().getUserHandler().exists(uuid)) {
                        String permission = String.join("§8, §9", user.getPermissions());
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Information about §9" + playerName);
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Group§8: §9" + user.getGroupName());
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Permissions§8: §9" + permission);
                    } else {
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The player §9" + playerName + " §7was not found in the §9database§8!");
                    }
                } else if (args[0].equalsIgnoreCase("group")) {
                    if (PaperPlugin.getInstance().getGroupHandler().exists(groupName)) {
                        var group = PaperPlugin.getInstance().getGroupHandler().getGroups(groupName);
                        String groupPermissions = String.join("§8, §9", group.getPermissions());
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Information about the group §9" + groupName);
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Group ID§8: §9" + group.getGroupID());
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Prefix§8: §9" + group.getGroupPrefix().replace("&", "§") + commandSender.getName());
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Suffix§8: §9" + group.getGroupSuffix().replace("&", "§"));
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "NameColour§8: §9" + group.getGroupNameColour().toString() + commandSender.getName());
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "Permissions§8: §9" + groupPermissions);
                    } else {
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupName + "§7 doesn't exists§8!");
                    }
                } else {
                    sendHelp(commandSender);
                }
            } else if (args.length == 3) {
                String groupName = args[1];
                if (args[0].equalsIgnoreCase("group")) {
                    if (args[2].equalsIgnoreCase("create")) {
                        if (!PaperPlugin.getInstance().getGroupHandler().exists(groupName)) {
                            PaperPlugin.getInstance().getGroupHandler().createGroupIfNotExists(groupName);
                            PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new GroupCreateEvent(groupName));
                            commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupName + "§7 is now created§8!");
                        } else {
                            commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupName + "§7 already exists§8!");
                        }
                    } else if (args[2].equalsIgnoreCase("delete")) {
                        if (PaperPlugin.getInstance().getGroupHandler().exists(groupName)) {
                            if (!PaperPlugin.getInstance().getGroupHandler().getGroups(groupName).getGroupName().equals(PaperPlugin.getInstance().getPaperConfiguration().getDefaultGroup())) {
                                PaperPlugin.getInstance().getGroupHandler().deleteGroup(groupName);
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupName + "§7 is now deleted§8!");
                                PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new GroupDeleteEvent(groupName));
                            } else {
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupName + "§7 cannot be deleted because it is a default group§8!");
                            }
                        } else {
                            commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupName + "§7 deosn't exists§8!");
                        }
                    } else {
                        sendHelp(commandSender);
                    }
                } else {
                    sendHelp(commandSender);
                }
            } else {
                String groupName = args[1];
                String playerName = args[1];
                String permissions = args[3];
                String groupNameNew = args[3];
                String message = "";
                for (int i = 3; i <= args.length - 1; i++) {
                    message = message + args[i] + " ";
                }
                if (args[0].equalsIgnoreCase("group")) {
                    var group = PaperPlugin.getInstance().getGroupHandler().getGroups(groupName);
                    if (PaperPlugin.getInstance().getGroupHandler().exists(groupName)) {
                        if (args[2].equalsIgnoreCase("add")) {
                            if (!group.getPermissions().contains(permissions)) {
                                group.getPermissions().add(permissions);
                                PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new GroupPermissionUpdateEvent(groupName, permissions, false));
                                PaperPlugin.getInstance().getGroupHandler().updateGroup(group);
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The Group §9" + groupName + "§7 has now the permission §9" + permissions + "§8!");
                            } else {
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The Group §9" + groupName + "§7 already has the permission §9" + permissions + "§8!");
                            }
                        } else if (args[2].equalsIgnoreCase("remove")) {
                            if (group.getPermissions().contains(permissions)) {
                                group.getPermissions().remove(permissions);
                                PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new GroupPermissionUpdateEvent(groupName, permissions, true));
                                PaperPlugin.getInstance().getGroupHandler().updateGroup(group);
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The Group §9" + groupName + "§7 has no longer the permission §9" + permissions + "§8!");
                            } else {
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The Group §9" + groupName + "§7 deosn't have the permission §9" + permissions + "§8!");
                            }
                        } else if (args[2].equalsIgnoreCase("setid")) {
                            PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new GroupUpdateEvent(groupName));
                            group.setGroupID(Integer.parseInt(args[3]));
                            PaperPlugin.getInstance().getGroupHandler().updateGroup(group);
                            commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupName + "§7 has now the id §9" + Integer.parseInt(args[3]));
                        } else if (args[2].equalsIgnoreCase("setnamecolor")) {
                            PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new GroupUpdateEvent(groupName));
                            group.setGroupNameColour(args[3].replace("&", "§"));
                            PaperPlugin.getInstance().getGroupHandler().updateGroup(group);
                            commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupName + "§7 has now the group name colour §9" + args[3]);

                        } else if (args[2].equalsIgnoreCase("setprefix")) {
                            commandSender.sendMessage(message);

                            var groupUpdate = PaperPlugin.getInstance().getGroupHandler().getGroups(groupName);
                            groupUpdate.setGroupPrefix(message.replace("&", "§"));
                            PaperPlugin.getInstance().getGroupHandler().updateGroup(groupUpdate);
                            Bukkit.getPluginManager().callEvent(new GroupUpdateEvent(groupName));

                            Bukkit.getOnlinePlayers().forEach(onlinePlayers -> {
                                PaperPlugin.getInstance().getPaperUserHandler().updatePermissions(onlinePlayers);
                            });
                        } else if (args[2].equalsIgnoreCase("setsuffix")) {
                            var groupUpdate = PaperPlugin.getInstance().getGroupHandler().getGroups(groupName);
                            groupUpdate.setGroupSuffix(message.replace("&", "§"));
                            PaperPlugin.getInstance().getGroupHandler().updateGroup(groupUpdate);
                            Bukkit.getPluginManager().callEvent(new GroupUpdateEvent(groupName));

                            Bukkit.getOnlinePlayers().forEach(onlinePlayers -> {
                                PaperPlugin.getInstance().getPaperUserHandler().updatePermissions(onlinePlayers);
                            });
                        }
                    }
                } else if (args[0].equalsIgnoreCase("player")) {
                    UUID uuid = UUID.fromString(PaperPlugin.getInstance().getUuidFetcher().getUUID(playerName));
                    var user = PaperPlugin.getInstance().getUserHandler().getUser(uuid);
                    if (PaperPlugin.getInstance().getUserHandler().exists(uuid)) {
                        if (args[2].equalsIgnoreCase("add")) {
                            if (!user.getPermissions().contains(permissions)) {
                                user.getPermissions().add(permissions);
                                PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new PlayerPermissionUpdateEvent(playerName, permissions, false));
                                PaperPlugin.getInstance().getUserHandler().updateUser(user);
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The player §9" + playerName + " §7has now the permission §9" + permissions + "§8!");
                            } else {
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The player §9" + playerName + "§7 already has the permission §9" + permissions + "§8!");
                            }
                        } else if (args[2].equalsIgnoreCase("remove")) {
                            if (user.getPermissions().contains(permissions)) {
                                user.getPermissions().remove(permissions);
                                PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new PlayerPermissionUpdateEvent(playerName, permissions, true));
                                PaperPlugin.getInstance().getUserHandler().updateUser(user);
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The player §9" + playerName + " §7has no longer the permission §9" + permissions + "§8!");
                            } else {
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The player §9" + playerName + "§7 doesn't have the permission §9" + permissions + "§8!");
                            }
                        } else if (args[2].equalsIgnoreCase("set")) {
                            if (PaperPlugin.getInstance().getGroupHandler().exists(groupNameNew)) {
                                PaperPlugin.getInstance().getServer().getPluginManager().callEvent(new PlayerGroupUpdateEvent(playerName, groupNameNew, user.getGroupName()));
                                user.setGroupName(groupNameNew);
                                PaperPlugin.getInstance().getUserHandler().updateUser(user);
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The player §9" + playerName + "§7 is now in the group §9" + groupNameNew + "§8!");
                            } else {
                                commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The group §9" + groupNameNew + " §7deosn't exists§8!");
                            }
                        }
                    } else {
                        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "The player §9" + playerName + " §7was not found in the §9database§8!");
                    }
                } else {
                    sendHelp(commandSender);
                }
            }
            Bukkit.getOnlinePlayers().forEach(onlinePlayers -> {
                PaperPlugin.getInstance().getPaperUserHandler().updatePermissions(onlinePlayers);
            });
        } else {
            commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "You have no permission to use that §9Command§8!");
        }
        return false;
    }

    public void sendHelp(CommandSender commandSender) {
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "available commands");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard player §8(§9PLAYER§8) §8- §7information about the player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard player §8(§9PLAYER§8) §7set §8(§9GROUP§8) §8- §7set the group for the player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard player §8(§9PLAYER§8) §7add §8(§9PERMISSION§8) §8- §7add a custom permission to player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard player §8(§9PLAYER§8) §7remove §8(§9PERMISSION§8) §8- §7remove a custom permission to player§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard group §8(§9PLAYER§8) §7create §8- §7create a custom group§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard group §8(§9PLAYER§8) §7delete  §8- §7removed a custom group§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard group §8(§9PLAYER§8) §7add §8(§9PERMISSION§8) §8- §7add a custom permission to the group§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard group §8(§9PLAYER§8) §7remove §8(§9PERMISSION§8) §8- §7removed a custom permission to the group§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard group §8(§9PLAYER§8) §7setid §8(§9ID§8) §8- §7sets the id for the group§8!");
        commandSender.sendMessage(PaperPlugin.getInstance().getPrefix() + "/accessguard group §8(§9PLAYER§8) §7setnamecolour §8(§9PERMISSION§8) §8- §7removed a custom permission to the group§8!");
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("group");
            completions.add("player");
        } else if (args.length == 2) {
            if ("group".equalsIgnoreCase(args[0])) {
                List<Groups> getAllGroups = PaperPlugin.getInstance().getGroupHandler().getAllGroups();
                List<String> groupNames = getAllGroups.stream().map(Groups::getGroupName).collect(Collectors.toList());
                completions.addAll(groupNames);
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
                completions.add("setsuffix");
                completions.add("setid");
                completions.add("setnamecolor");
            } else if ("player".equalsIgnoreCase(args[0])) {
                completions.add("set");
                completions.add("add");
                completions.add("remove");
            }
        } else if (args.length == 4) {
            if ("player".equalsIgnoreCase(args[0])) {
                if ("set".equalsIgnoreCase(args[2])) {
                    List<Groups> getAllGroups = PaperPlugin.getInstance().getGroupHandler().getAllGroups();
                    List<String> groupNames = getAllGroups.stream().map(Groups::getGroupName).collect(Collectors.toList());
                    completions.addAll(groupNames);
                }
            } else if ("group".equalsIgnoreCase(args[0])) {
                if ("setnamecolor".equalsIgnoreCase(args[2])) {
                    completions.add(Arrays.toString(ChatColor.values()));
                }
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
