package com.gabriaum.skills.util.inventory.stack;

import com.gabriaum.skills.util.inventory.stack.action.ItemClick;
import com.gabriaum.skills.util.inventory.stack.action.ItemInteract;
import com.gabriaum.skills.util.inventory.stack.action.ItemUpdater;
import com.gabriaum.skills.util.inventory.stack.util.SkullMaker;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class ItemHelper extends ItemStack {

    private static final List<ItemHelper> contents = new ArrayList<>();
    private ItemMeta meta = getItemMeta();

    private ItemUpdater updater;
    private ItemClick click;
    private ItemInteract interact;

    public ItemHelper(Material type, int amount, int durability) {
        super(type, amount, (short) durability);
    }

    public ItemHelper(Material type, int amount) {
        super(type, amount);
    }

    public ItemHelper(Material type) {
        super(type);
    }

    public ItemHelper(String skullUrl) {
        super(Material.SKULL_ITEM, 1, (short) 3);

        ItemStack skull = SkullMaker.collectByUrl(skullUrl);

        updateMeta(skull.getItemMeta());
    }

    public ItemHelper(Player skullByPlayer) {
        super(Material.SKULL_ITEM, 1, (short) 3);

        SkullMeta skullMeta = (SkullMeta) getItemMeta();

        skullMeta.setOwner(skullByPlayer.getName());
        updateMeta(skullMeta);
    }

    public static ItemHelper read(ItemStack item) {
        return contents.stream().filter(items -> items.isSimilar(item)).findFirst().orElse(null);
    }

    public ItemHelper updater(ItemUpdater updater) {
        this.updater = updater;
        contents.add(this);
        return this;
    }

    public ItemHelper click(ItemClick click) {
        this.click = click;
        contents.add(this);
        return this;
    }

    public ItemHelper interact(ItemInteract interact) {
        this.interact = interact;
        contents.add(this);
        return this;
    }

    public void updateMeta(ItemMeta meta) {
        this.meta = meta;
        super.setItemMeta(meta);
    }

    public ItemHelper name(String var) {
        meta.setDisplayName(var);
        updateMeta(meta);
        return this;
    }

    public ItemHelper lore(String var) {
        meta.setLore(Collections.singletonList(var));
        updateMeta(meta);
        return this;
    }

    public ItemHelper lore(String... var) {
        meta.setLore(Arrays.asList(var));
        updateMeta(meta);
        return this;
    }

    public ItemHelper lore(List<String> var) {
        meta.setLore(var);
        updateMeta(meta);
        return this;
    }

    public ItemHelper updateRGB(int r, int g, int b) {
        Color color = Color.fromBGR(r, g, b);
        LeatherArmorMeta armorMeta = (LeatherArmorMeta) meta;

        armorMeta.setColor(color);
        updateMeta(armorMeta);

        return this;
    }

    public ItemHelper updateNBT(String key, String value) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
        net.minecraft.server.v1_8_R3.NBTTagCompound compound = nmsItem.getTag();

        if (compound == null)
            compound = new net.minecraft.server.v1_8_R3.NBTTagCompound();

        compound.setString(key, value);
        nmsItem.setTag(compound);
        setItemMeta(CraftItemStack.asBukkitCopy(nmsItem).getItemMeta());

        return this;
    }

    public String getNBT(String key) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(this);
        net.minecraft.server.v1_8_R3.NBTTagCompound compound = nmsItem.getTag();

        if (compound != null && compound.hasKey(key))
            return compound.getString(key);

        return null;
    }
}
