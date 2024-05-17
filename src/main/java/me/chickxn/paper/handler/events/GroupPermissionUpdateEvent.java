package me.chickxn.paper.handler.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class GroupPermissionUpdateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final String groupName;
    private final String permission;
    private final boolean removed;

    public GroupPermissionUpdateEvent(String groupName, String permission, boolean removed) {
        this.groupName = groupName;
        this.permission = permission;
        this.removed = removed;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}