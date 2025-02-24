package com.gabriaum.skills.user.listener;

import com.gabriaum.skills.SkillsMain;
import com.gabriaum.skills.user.User;
import com.gabriaum.skills.user.assets.Module;
import com.gabriaum.skills.user.assets.type.ModuleType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

import java.util.UUID;

public class UserListener implements Listener {

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

        UUID uniqueId = event.getUniqueId();

        String name = event.getName();

        try {

            User user = new User(uniqueId, name);

            if (user != null) {

                SkillsMain.getInstance().getUserController().put(uniqueId, user);
            }
        } catch (Exception ex) {

            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("§cOcorreu um erro ao tentar logar, tente novamente mais tarde.");

            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        event.setJoinMessage(null);

        Player player = event.getPlayer();

        User user = SkillsMain.getInstance().getUserController().get(player.getUniqueId());

        for (PotionEffect effect : player.getActivePotionEffects()) {

            player.removePotionEffect(effect.getType());
        }

        if (user != null) {

            if (user.hasModule()) {

                ModuleType moduleType = user.getModuleSelected();

                Module module = SkillsMain.getInstance().getModuleController().get(moduleType);

                if (module != null) {

                    module.execute(user);
                }
            }

            user.setPlayer(player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        event.setQuitMessage(null);

        Player player = event.getPlayer();

        User user = SkillsMain.getInstance().getUserController().get(player.getUniqueId());

        if (user != null) {

            SkillsMain.getInstance().getUserController().remove(player.getUniqueId());
        }
    }
}
