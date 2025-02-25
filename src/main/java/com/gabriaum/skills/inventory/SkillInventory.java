package com.gabriaum.skills.inventory;

import com.gabriaum.skills.SkillsMain;
import com.gabriaum.skills.user.User;
import com.gabriaum.skills.user.assets.Module;
import com.gabriaum.skills.user.assets.type.ModuleType;
import com.gabriaum.skills.util.inventory.Inventory;
import com.gabriaum.skills.util.inventory.stack.ItemHelper;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class SkillInventory extends Inventory {

    public SkillInventory(Player player) {
        super("Skill Selector", 4);

        moveItems(false);

        updateLayout(
                "XXXXXXXXX",
                "XOOOOOOOX",
                "XXXXXXXXX",
                "<XXXXXXX>"
        );

        handle(player);
    }

    @Override
    public void handle(Player player) {

        clear();

        User user = User.getUser(player.getUniqueId());

        List<ItemHelper> items = new ArrayList<>();

        for (ModuleType type : ModuleType.values()) {

            if (type.equals(ModuleType.NONE)) {
                continue;
            }

            ItemHelper item = new ItemHelper(type.getIcon())
                    .name("§a" + type.getName())
                    .lore(
                            "§7" + type.getDescription(),
                            "",
                            "§eClique para selecionar."
                    ).click(event -> {

                        for (PotionEffect effect : player.getActivePotionEffects()) {

                            player.removePotionEffect(effect.getType());
                        }

                        if (user.getModuleSelected().equals(type)) {

                            player.sendMessage("§aVocê removeu a habilidade " + type.getName() + ".");
                            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

                            user.setModuleSelected(ModuleType.NONE);

                            handle(player);
                            return;
                        }

                        Module module = SkillsMain.getInstance().getModuleController().get(type);

                        if (module != null) {

                            module.execute(user);
                        }

                        user.setModuleSelected(type);

                        player.sendMessage("§aVocê selecionou a habilidade " + type.getName() + ".");
                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

                        handle(player);
                    });

            items.add(item);
        }

        updateSource(items);

        open(player);
    }
}
