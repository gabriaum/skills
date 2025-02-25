package com.gabriaum.skills;

import com.gabriaum.skills.command.framework.CommandFramework;
import com.gabriaum.skills.command.list.SkillCommand;
import com.gabriaum.skills.user.assets.controller.ModuleController;
import com.gabriaum.skills.user.assets.listener.ModuleListener;
import com.gabriaum.skills.user.controller.UserController;
import com.gabriaum.skills.user.listener.UserListener;
import com.gabriaum.skills.util.inventory.InventoryProctor;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SkillsMain extends JavaPlugin {

    @Getter
    private static SkillsMain instance;

    protected UserController userController;
    protected ModuleController moduleController;

    protected InventoryProctor inventoryProctor;

    protected CommandFramework commandFramework;

    @Override
    public void onLoad() {

        instance = this;
    }

    @Override
    public void onEnable() {

        userController = new UserController();

        moduleController = new ModuleController();
        moduleController.load();

        inventoryProctor = new InventoryProctor(this);

        commandFramework = new CommandFramework(this);

        commandFramework.registerCommands(
                new SkillCommand()
        );

        handleListeners(
                new UserListener(), new ModuleListener()
        );
    }

    protected void handleListeners(Object... listeners) {

        for (Object listener : listeners) {

            getServer().getPluginManager().registerEvents((Listener) listener, this);
        }
    }
}
