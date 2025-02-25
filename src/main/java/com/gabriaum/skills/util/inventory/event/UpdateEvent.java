package com.gabriaum.skills.util.inventory.event;

import com.gabriaum.skills.util.inventory.event.type.UpdateType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@RequiredArgsConstructor
public class UpdateEvent extends Event {

    private final UpdateType type;
    private final long ticks;

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public UpdateEvent(UpdateType type) {
        this(type, -1);
    }

    public boolean isType(UpdateType type) {
        return this.type.equals(type);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public void push() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
