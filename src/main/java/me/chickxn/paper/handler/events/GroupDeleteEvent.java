package me.chickxn.paper.handler.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class GroupDeleteEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final String groupName;

    public GroupDeleteEvent(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}