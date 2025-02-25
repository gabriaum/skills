package com.gabriaum.skills.util.inventory.listener;

import com.gabriaum.skills.util.inventory.stack.ItemHelper;
import com.gabriaum.skills.util.inventory.stack.action.ItemInteract;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void clickMaker(PlayerInteractEvent event) {
        if (event.getItem() != null && event.getAction().name().contains("RIGHT")) {
            ItemHelper item = ItemHelper.read(event.getItem());

            if (item != null && event.getAction().toString().contains("RIGHT")) {
                ItemInteract interact = item.getInteract();

                if (interact != null) {
                    event.setCancelled(true);

                    interact.run(event);
                }
            }
        }
    }
}
