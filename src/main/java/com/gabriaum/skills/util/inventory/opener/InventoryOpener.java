package com.gabriaum.skills.util.inventory.opener;

import com.gabriaum.skills.util.inventory.ClickableItem;
import com.gabriaum.skills.util.inventory.SmartInventory;
import com.gabriaum.skills.util.inventory.content.InventoryContents;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public interface InventoryOpener {

    Inventory open(SmartInventory inv, Player player);

    Inventory open(SmartInventory inv, Player player, String title);

    boolean supports(InventoryType type);

    default void fill(Inventory handle, InventoryContents contents) {
        ClickableItem[][] items = contents.all();

        for (int row = 0; row < items.length; row++) {
            for (int column = 0; column < items[row].length; column++) {
                if (items[row][column] != null)
                    handle.setItem(9 * row + column, items[row][column].getItem());
            }
        }
    }

}
