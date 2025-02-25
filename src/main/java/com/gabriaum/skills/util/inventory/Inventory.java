package com.gabriaum.skills.util.inventory;

import com.gabriaum.skills.util.inventory.configuration.InventoryConfiguration;
import com.gabriaum.skills.util.inventory.configuration.layout.Layout;
import com.gabriaum.skills.util.inventory.configuration.layout.frame.Frame;
import com.gabriaum.skills.util.inventory.controller.list.LayoutController;
import com.gabriaum.skills.util.inventory.sound.SoundType;
import com.gabriaum.skills.util.inventory.stack.ItemHelper;
import com.google.common.annotations.Beta;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;

import java.util.*;

@Getter
public abstract class Inventory implements InventoryHolder {

    private final String title;
    private final int size;

    private org.bukkit.inventory.Inventory inventory;

    private final Map<Map<Integer, Integer>, ItemHelper> staticItems;
    private final List<ItemHelper> flexibleItems;
    private final Map<Integer, ItemHelper> items;

    private InventoryConfiguration configuration;
    private int currentPage;

    public Inventory(String title, int size) {
        this.title = title;
        this.size = 9 * size;

        staticItems = new HashMap<>();
        flexibleItems = new ArrayList<>();

        items = new HashMap<>();

        configuration = new InventoryConfiguration();
        currentPage = 1;
    }

    public abstract void handle(Player human);

    public void add(int row, int column, ItemHelper item) {
        Map<Integer, Integer> slot = new HashMap<>();

        slot.put(row, column);
        staticItems.put(slot, item);
    }

    @Beta
    public void modelAdd(ItemHelper item) {
    }

    public void remove(ItemHelper item) {
        staticItems.values().removeIf(itemHelper -> itemHelper.getType().equals(item.getType()));
        flexibleItems.removeIf(itemHelper -> itemHelper.getType().equals(item.getType()));

        items.values().removeIf(itemHelper -> itemHelper.getType().equals(item.getType()));
    }

    public void clear() {
        staticItems.clear();
        flexibleItems.clear();

        items.clear();
    }

    public void updateConfiguration(InventoryConfiguration configuration) {
        this.configuration = configuration;
    }

    public void moveItems(boolean val) {
        configuration.setMoveItems(val);

        updateConfiguration(configuration);
    }

    public void updateLayout(String... layout) {
        configuration.setUseLayout(true);
        configuration.setLayout(layout);

        updateConfiguration(configuration);
    }

    public void updateSource(List<ItemHelper> source) {
        configuration.setSource(source);

        updateConfiguration(configuration);
    }

    public void play(Player human, SoundType sound) {
        human.playSound(human.getLocation(), sound.getSound(), 1f, 1f);
    }

    public void open(Player human) {
        inventory = Bukkit.createInventory(this, size, title);

        if (configuration.isUseLayout()) {
            List<ItemHelper> source = configuration.getSource();

            LayoutController controller = new LayoutController(configuration.getLayout());
            Iterator<Layout> layoutIterator = controller.iterator();

            int
                    itemsPerPage = controller.getItemCount(),
                    pageStart = 0,
                    pageEnd = itemsPerPage;

            if (currentPage > 1) {
                pageStart = ((currentPage - 1) * itemsPerPage);
                pageEnd = (pageStart + itemsPerPage);
            }

            List<ItemHelper> nextLayouts = new ArrayList<>();

            for (int i = pageStart; i <= pageEnd; i++) {
               if (i >= source.size())
                   break;

                nextLayouts.add(source.get(i));
            }

            int sourceIndex = 0;

            while (layoutIterator.hasNext()) {
                Layout layout = layoutIterator.next();
                ItemHelper item;

                switch (layout.getFrame()) {
                    case ITEM: {
                        if (sourceIndex < nextLayouts.size()) {
                            item = nextLayouts.get(sourceIndex);

                            sourceIndex++;
                        } else
                            item = new ItemHelper(Material.AIR);

                        break;
                    }

                    case NEXT_PAGE: {
                        item = new ItemHelper(Material.AIR);

                        if (Math.ceil((double) source.size() / itemsPerPage) + 1 > currentPage) {
                            item = configuration.getNextItem()
                                    .click(event -> {
                                        currentPage++;

                                        inventory.clear();
                                        open((Player) event.getWhoClicked());
                                    });
                        }

                        break;
                    }


                    case PREVIOUS_PAGE: {
                        item = new ItemHelper(Material.AIR);

                        if (currentPage > 1)
                            item = getConfiguration().getPreviousItem()
                                    .click(event -> {
                                        currentPage--;

                                        inventory.clear();
                                        open((Player) event.getWhoClicked());
                                    });

                        break;
                    }

                    case SKIP: {
                        item = new ItemHelper(Material.AIR);
                        break;
                    }

                    default: {
                        item = null;
                        break;
                    }
                }

                if (item != null) {
                    int currentSlot = layout.getSlot();

                    inventory.setItem(currentSlot, item);
                    items.put(currentSlot, item);
                }
            }
        }

        if (!staticItems.isEmpty()) {
            for (Map.Entry<Map<Integer, Integer>, ItemHelper> entry : staticItems.entrySet()) {
                Map<Integer, Integer> slot = entry.getKey();
                ItemHelper item = entry.getValue();

                int
                        row = slot.keySet().iterator().next(),
                        column = slot.get(row),
                        allocatedSlot = (row - 1) * 9 + (column - 1);

                inventory.setItem(allocatedSlot, item);
                items.put(allocatedSlot, item);
            }
        }

        human.openInventory(inventory);
        InventoryProctor.getController().put(human, this);
    }
}