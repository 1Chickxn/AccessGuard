package me.chickxn.paper.handler.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GroupUpdateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final String groupName;

    public GroupUpdateEvent(String groupName) {
        this.groupName = groupName;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}