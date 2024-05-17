package me.chickxn.paper.handler.events;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerGroupUpdateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    private final String playerName;
    private final String groupName;
    private final String oldGroupName;

    public PlayerGroupUpdateEvent(String playerName, String groupName, String oldGroupName) {
        this.playerName = playerName;
        this.groupName = groupName;
        this.oldGroupName = oldGroupName;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}