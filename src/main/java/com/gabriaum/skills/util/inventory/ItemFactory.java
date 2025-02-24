package com.gabriaum.skills.util.inventory;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemFactory implements Cloneable {

    private ItemStack itemStack;

    public ItemFactory(Material material) {
        itemStack = new ItemStack(material);
    }

    public ItemFactory(Material material, int amount, short data) {
        itemStack = new ItemStack(material, amount, data);
    }

    public ItemFactory(ItemStack stack) {
        itemStack = stack;
    }

    public ItemFactory() {

    }

    public ItemFactory item(ItemStack itemStack1) {
        itemStack = itemStack1;
        return this;
    }

    public ItemFactory material(Material type) {
        if (itemStack == null)
            itemStack = new ItemStack(type);
        else
            itemStack.setType(type);
        return this;
    }

    public ItemFactory setFast(Material type, String name, int data) {
        material(type);
        name(name);
        durability(data);
        return this;
    }

    public ItemFactory setFast(Material type, String name) {
        material(type);
        name(name);
        return this;
    }

    public ItemFactory type(Material type) {
        material(type);
        return this;
    }

    public ItemFactory amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemFactory durability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemFactory durability(org.bukkit.ChatColor color) {

        short data = switch (color) {
            case RED -> 1;
            case DARK_GREEN -> 2;
            case BLUE, DARK_BLUE -> 4;
            case DARK_PURPLE -> 5;
            case DARK_AQUA -> 6;
            case WHITE -> 7;
            case GRAY, DARK_GRAY -> 8;
            case GREEN -> 10;
            case YELLOW -> 11;
            case AQUA -> 12;
            case LIGHT_PURPLE -> 13;
            case GOLD -> 14;
            default -> 0;
        };

        itemStack.setDurability(data);
        return this;
    }

    public ItemFactory flag(ItemFlag... flag) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(flag);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemFactory name(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemFactory description(List<String> desc) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(desc);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemFactory description(String... desc) {
        description(Arrays.asList(desc));
        return this;
    }

    public ItemFactory description(String text) {

        if (text == null) return this;

        List<String> lore = formatLore(25, text);
        description(lore.toArray(new String[]{}));
        return this;
    }

    public ItemFactory description(int limit, String text) {
        List<String> lore = formatLore(limit, text);
        description(lore.toArray(new String[]{}));
        return this;
    }

    public ItemFactory enchantment(Enchantment[] enchant, int[] level) {
        for (int i = 0; i < enchant.length; ++i) {
            itemStack.addUnsafeEnchantment(enchant[i], level[i]);
        }
        return this;
    }

    public ItemFactory enchantment(Enchantment enchant, int level) {
        itemStack.addUnsafeEnchantment(enchant, level);
        return this;
    }

    public ItemFactory doneEnchantment(Enchantment enchant, int level) {
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemStack.getItemMeta();
        meta.addStoredEnchant(enchant, level, true);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemFactory removePotionEffects() {
        PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
        for (PotionEffect potionEffect : potionMeta.getCustomEffects())
            potionMeta.removeCustomEffect(potionEffect.getType());
        return this;
    }

    public ItemFactory unbreakable() {
        ItemMeta meta = itemStack.getItemMeta();
        meta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemFactory unbreakable(boolean unbreakable) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemFactory breakable() {
        ItemMeta meta = itemStack.getItemMeta();
        meta.spigot().setUnbreakable(false);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemFactory breakable(boolean breakable) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.spigot().setUnbreakable(!breakable);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemFactory build(Player player, int... slot) {
        build(player.getInventory(), slot);
        player.updateInventory();
        return this;
    }

    public ItemFactory noAttributes() {
        return flag(ItemFlag.values());
    }

    public ItemFactory build(Player player) {
        player.getInventory().addItem(itemStack);
        player.updateInventory();
        return this;
    }

    public ItemFactory build(Inventory inventory, int... slot) {
        for (int slots : slot) {
            inventory.setItem(slots, itemStack);
        }

        return this;
    }

    public ItemFactory build(Inventory inventory) {
        inventory.addItem(itemStack);
        return this;
    }

    public ItemFactory glow() {
        return tag("ench");
    }

    public ItemStack get() {
        return itemStack;
    }

    public ItemMeta name(ItemStack stack, String name) {
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        return meta;
    }

    public ItemFactory skull(String owner) {
        itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(owner);
        itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStack color(Material material, Color color) {
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) stack.getItemMeta();
        armorMeta.setColor(color);
        stack.setItemMeta(armorMeta);
        return stack;
    }

    public ItemFactory color(Color color) {
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) get().getItemMeta();
        armorMeta.setColor(color);
        get().setItemMeta(armorMeta);
        return this;
    }

    public ItemStack color(Material material, Color color, String name) {
        ItemStack stack = new ItemStack(material);
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) stack.getItemMeta();
        armorMeta.setColor(color);
        armorMeta.setDisplayName(name);
        stack.setItemMeta(armorMeta);
        return stack;
    }

    public ItemFactory color(Color color, String name) {
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        armorMeta.setColor(color);
        armorMeta.setDisplayName(name);
        itemStack.setItemMeta(armorMeta);
        return this;
    }

    public ItemFactory chanceItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public boolean checkItem(ItemStack item, String display) {
        return (item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(display));
    }

    public boolean checkContains(ItemStack item, String display) {
        return (item != null && item.getType() != Material.AIR && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().contains(display));
    }

    public List<String> formatLore(int limit, String text) {

        List<String> lore = new ArrayList<>();
        String[] split = text.split(" ");
        text = "";

        for (int i = 0; i < split.length; ++i) {
            if (ChatColor.stripColor(text).length() > limit || ChatColor.stripColor(text).endsWith(".") || ChatColor.stripColor(text).endsWith("!")) {
                lore.add("§7" + text);
                if (text.endsWith(".") || text.endsWith("!")) {
                    lore.add("");
                }
                text = "";
            }
            String toAdd = split[i];
            if (toAdd.contains("\n")) {
                toAdd = toAdd.substring(0, toAdd.indexOf("\n"));
                split[i] = split[i].substring(toAdd.length() + 1);
                lore.add("§7" + text + ((text.length() == 0) ? "" : " ") + toAdd);
                text = "";
                --i;
            } else {
                text += ((text.length() == 0) ? "" : " ") + toAdd;
            }
        }
        lore.add("§7" + text);

        return lore;
    }

    public ItemFactory tag(String... tag) {
        net.minecraft.server.v1_8_R3.ItemStack nmsCopy = CraftItemStack.asNMSCopy(this.itemStack);
        NBTTagCompound nbtTagCompound = (nmsCopy.hasTag()) ? nmsCopy.getTag() : new NBTTagCompound();
        for (String str : tag)
            nbtTagCompound.set(str, new NBTTagByte((byte) 0));
        nmsCopy.setTag(nbtTagCompound);
        this.itemStack = CraftItemStack.asBukkitCopy(nmsCopy);
        return this;
    }

    @Override
    public ItemFactory clone() {
        try {
            return (ItemFactory) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
