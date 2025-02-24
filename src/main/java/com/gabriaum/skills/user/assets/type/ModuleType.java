package com.gabriaum.skills.user.assets.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter
@RequiredArgsConstructor
public enum ModuleType {

    NONE(null, "", ""),
    ASSASSIN(Material.REDSTONE, "Assassino", "Aumenta o dano de espadas em +30% enquanto ativa."),
    GUARDIAN(Material.DIAMOND_SWORD, "Guardião", "Reduz o dano recebido em 20% e concede resistência temporária."),
    SPRINTER(Material.LEATHER_BOOTS, "Velocista", "Receba Speed II e Jump Boost I enquanto ativa."),
    WIZARD(Material.BLAZE_POWDER, "Mago", "Tenha a chance de 10% de lançar uma bola de fogo ao atacar com uma espada."),
    REGENERATOR(Material.GOLDEN_APPLE, "Regenerador", "Receba Regeneração I enquanto ativa."),
    ;

    private final Material icon;

    private final String name, description;

    public static ModuleType getByName(String name) {

        for (ModuleType moduleType : values()) {

            if (moduleType.getName().equalsIgnoreCase(name)) {
                return moduleType;
            }
        }

        return null;
    }
}