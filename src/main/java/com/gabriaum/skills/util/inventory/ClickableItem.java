package com.gabriaum.skills.util.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem implements Cloneable {

    private ItemStack item;
    private final Consumer<InventoryClickEvent> consumer;

    private ClickableItem(ItemStack item, Consumer<InventoryClickEvent> consumer) {
        this.item = item;
        this.consumer = consumer;
    }

    public static ClickableItem empty(ItemStack item) {
        return of(item, e -> e.setCancelled(false));
    }

    public static ClickableItem cancellable(ItemStack item) {
        return of(item, e -> e.setCancelled(true));
    }

    public static ClickableItem of(ItemStack item, Consumer<InventoryClickEvent> consumer) {
        return new ClickableItem(item, consumer);
    }

    public void run(InventoryClickEvent e) {
        consumer.accept(e);
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public ClickableItem clone() {
        try {
            return (ClickableItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }
}
