package com.gabriaum.skills.user;

import com.gabriaum.skills.SkillsMain;
import com.gabriaum.skills.user.assets.type.ModuleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class User {

    protected final UUID uniqueId;

    protected final String name;

    private Player player;

    private ModuleType moduleSelected = ModuleType.NONE;

    public boolean hasModule() {
        return !moduleSelected.equals(ModuleType.NONE);
    }

    public static User getUser(UUID uniqueId) {
        return SkillsMain.getInstance().getUserController().get(uniqueId);
    }
}
