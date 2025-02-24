package com.gabriaum.skills.inventory;

import com.gabriaum.skills.SkillsMain;
import com.gabriaum.skills.user.User;
import com.gabriaum.skills.user.assets.Module;
import com.gabriaum.skills.user.assets.type.ModuleType;
import com.gabriaum.skills.util.inventory.ClickableItem;
import com.gabriaum.skills.util.inventory.ItemFactory;
import com.gabriaum.skills.util.inventory.SmartInventory;
import com.gabriaum.skills.util.inventory.content.InventoryContents;
import com.gabriaum.skills.util.inventory.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class ModuleInventory implements InventoryProvider {

    protected SmartInventory inventory = SmartInventory.builder()
            .id("module")
            .provider(this)
            .size(4, 9)
            .title("Selecionar habilidade")
            .closeable(true)
            .build();

    public ModuleInventory(Player player) {

        inventory.open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {

        User user = User.getUser(player.getUniqueId());

        ModuleType selected = user.getModuleSelected();

        int slot = 10;

        for (ModuleType type : ModuleType.values()) {

            if (type.equals(ModuleType.NONE)) {
                continue;
            }

            ItemFactory item = new ItemFactory(type.getIcon())
                    .name("§a" + type.getName())
                    .description(
                            "§7" + type.getDescription(),
                            "",
                            selected.equals(type) ? "§cSelecionado!" : "§eClique para selecionar."
                    );

            if (selected.equals(type)) {

                item.glow();
            }

            contents.set(slot++, ClickableItem.of(item.getItemStack(), event -> {

                for (PotionEffect effect : player.getActivePotionEffects()) {

                    player.removePotionEffect(effect.getType());
                }

                Module module = SkillsMain.getInstance().getModuleController().get(type);

                if (module != null) {

                    module.execute(user);
                }

                user.setModuleSelected(type);

                player.sendMessage("§aVocê selecionou a habilidade " + type.getName() + ".");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);

                inventory.open(player);
            }));
        }

        contents.set(3, 0, ClickableItem.of(new ItemFactory(Material.ARROW)
                .name("§aFechar")
                .getItemStack(), event -> player.closeInventory()));

        contents.set(3, 4, ClickableItem.cancellable(new ItemFactory(user.hasModule() ? selected.getIcon() : Material.BARRIER)
                .name(( user.hasModule() ? "§a" + selected.getName() : "§cNenhum"))
                .description("§7" + selected.getDescription())
                .getItemStack()));
    }
}
