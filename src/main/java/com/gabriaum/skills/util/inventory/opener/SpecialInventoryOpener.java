package com.gabriaum.skills.util.inventory.opener;

import com.google.common.collect.ImmutableList;
import com.gabriaum.skills.util.inventory.controller.InventoryController;
import com.gabriaum.skills.util.inventory.SmartInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class SpecialInventoryOpener implements InventoryOpener {

    private static final List<InventoryType> SUPPORTED = ImmutableList.of(
            InventoryType.FURNACE,
            InventoryType.WORKBENCH,
            InventoryType.DISPENSER,
            InventoryType.DROPPER,
            InventoryType.ENCHANTING,
            InventoryType.BREWING,
            InventoryType.ANVIL,
            InventoryType.BEACON,
            InventoryType.HOPPER
    );

    @Override
    public Inventory open(SmartInventory inv, Player player) {
        return open(inv, player, inv.getTitle());
    }

    @Override
    public Inventory open(SmartInventory inv, Player player, String title) {

        if (title == null) {
            return open(inv, player);
        }

        InventoryController manager = inv.getManager();
        Inventory handle = Bukkit.createInventory(player, inv.getType(), title);

        fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return SUPPORTED.contains(type);
    }

}
