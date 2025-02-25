package com.gabriaum.skills.util.inventory.stack.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.UUID;

@SuppressWarnings("all")
public class SkullMaker {

    public static ItemStack collectByUrl(String skullUrl) {
        ItemStack stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta meta = stack.getItemMeta();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", skullUrl).getBytes());

        gameProfile.getProperties().put("textures", new Property("textures", new String(encodedData)));

        Field profile;

        try {
            profile = meta.getClass().getDeclaredField("profile");

            profile.setAccessible(true);
            profile.set(meta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        stack.setItemMeta(meta);

        return stack;
    }
}
