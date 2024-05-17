package me.chickxn.paper.handler.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerPermissionUpdateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final String playerName;
    private final String permission;
    private final boolean removed;

    public PlayerPermissionUpdateEvent(String playerName, String permission, boolean removed) {
        this.playerName = playerName;
        this.permission = permission;
        this.removed = removed;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}