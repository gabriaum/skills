package com.gabriaum.skills.user.assets.list;

import com.gabriaum.skills.user.User;
import com.gabriaum.skills.user.assets.Module;
import com.gabriaum.skills.user.assets.type.ModuleType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Regenerator extends Module {

    public Regenerator() {
        super(ModuleType.REGENERATOR);
    }

    @Override
    public void execute(User user) {

        Player player = user.getPlayer();

        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
    }
}
