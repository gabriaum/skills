package com.gabriaum.skills.util.inventory.sound;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Sound;

@Getter
@AllArgsConstructor
public enum SoundType {

    CORRECT(Sound.SUCCESSFUL_HIT),
    ERROR(Sound.ITEM_BREAK),
    CHANGE_PAGE(Sound.ITEM_PICKUP),
    CLICK(Sound.WOOD_CLICK);

    private final Sound sound;
}
