package com.gabriaum.skills.util.inventory.configuration;

import com.gabriaum.skills.util.inventory.stack.ItemHelper;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class InventoryConfiguration {

    private boolean
            moveItems = true,
            useLayout = false;

    private ItemHelper previousItem = new ItemHelper(Material.ARROW)
            .name("§aPrevious page");

    private ItemHelper nextItem = new ItemHelper(Material.ARROW)
            .name("§aNext page");

    private String[] layout = {};
    private List<ItemHelper> source = new ArrayList<>();
}
