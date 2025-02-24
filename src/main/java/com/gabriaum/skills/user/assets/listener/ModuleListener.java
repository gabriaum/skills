package com.gabriaum.skills.user.assets.listener;

import com.gabriaum.skills.user.User;
import com.gabriaum.skills.user.assets.Module;
import com.gabriaum.skills.user.assets.type.ModuleType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;

public class ModuleListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player player) {

            Entity entity = event.getEntity();

            User user = User.getUser(player.getUniqueId());

            if (user == null) {
                return;
            }

            if (user.getModuleSelected().equals(ModuleType.ASSASSIN) && player.getItemInHand().getType().name().contains("_SWORD")) {

                double damage = event.getDamage() + ((event.getDamage() / 100) * 30);

                event.setDamage(damage);
            }
        }

        if (event.getEntity() instanceof Player player) {

            User user = User.getUser(player.getUniqueId());

            if (user == null) {
                return;
            }

            if (user.getModuleSelected().equals(ModuleType.GUARDIAN)) {

                double damage = event.getDamage() - ((event.getDamage() / 100) * 30);

                event.setDamage(damage);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        User user = User.getUser(player.getUniqueId());

        if (user == null) {
            return;
        }

        if (user.getModuleSelected().equals(ModuleType.WIZARD) && player.getItemInHand().getType().name().contains("_SWORD") && Math.random() < 0.1) {

            Vector direction = player.getLocation().getDirection().normalize();

            Location spawnLocation = player.getEyeLocation().add(direction.multiply(1.5));

            Fireball fireball = player.getWorld().spawn(spawnLocation.add(direction.multiply(1.5)), Fireball.class);

            fireball.setDirection(direction);
        }
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        ItemStack item = event.getItem();

        if (item.getType().equals(Material.MILK_BUCKET)) {

            event.setCancelled(true);

            for (PotionEffect effect : player.getActivePotionEffects()) {

                player.addPotionEffect(effect);
            }
        }
    }
}
