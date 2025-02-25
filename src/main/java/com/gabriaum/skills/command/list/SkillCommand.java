package com.gabriaum.skills.command.list;

import com.gabriaum.skills.SkillsMain;
import com.gabriaum.skills.command.framework.Command;
import com.gabriaum.skills.command.framework.CommandArgs;
import com.gabriaum.skills.command.framework.Completer;
import com.gabriaum.skills.inventory.SkillInventory;
import com.gabriaum.skills.user.User;
import com.gabriaum.skills.user.assets.Module;
import com.gabriaum.skills.user.assets.type.ModuleType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class SkillCommand {

    @Command(name = "skill", aliases = {"skills", "habilidades", "habilidade", "hab"}, inGameOnly = true)
    public void skillCommand(CommandArgs assistance) {

        String[] args = assistance.getArgs();

        Player player = assistance.getPlayer();

        User user = User.getUser(player.getUniqueId());

        if (args.length < 1) {
            new SkillInventory(player);
            return;
        }

        ModuleType moduleType = ModuleType.getByName(args[0]);

        if (moduleType == null) {
            player.sendMessage("§cHabilidade não encontrada.");
            return;
        }

        for (PotionEffect effect : player.getActivePotionEffects()) {

            player.removePotionEffect(effect.getType());
        }

        Module module = SkillsMain.getInstance().getModuleController().get(moduleType);

        if (module != null) {

            module.execute(user);
        }

        user.setModuleSelected(moduleType);

        player.sendMessage("§aVocê selecionou a habilidade " + moduleType.getName() + ".");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
    }

    @Completer(name = "skill")
    public List<String> skillCompleter(CommandArgs assistance) {

        String[] args = assistance.getArgs();

        if (args.length == 1) {

            List<String> completions = new ArrayList<>();

            for (ModuleType moduleType : ModuleType.values()) {

                completions.add(moduleType.getName());
            }

            return completions;
        }

        return new ArrayList<>();
    }
}
