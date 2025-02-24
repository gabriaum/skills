package com.gabriaum.skills.user.assets.controller;

import com.gabriaum.skills.SkillsMain;
import com.gabriaum.skills.user.assets.Module;
import com.gabriaum.skills.user.assets.type.ModuleType;
import com.gabriaum.skills.util.loader.ClassLoader;

import java.util.HashMap;

public class ModuleController extends HashMap<ModuleType, Module> {

    public void load() {

        for (Class<?> clazz : ClassLoader.getClassesForPackage(SkillsMain.getInstance(), "com.gabriaum.skills.user.assets.list")) {

            try {

                if (Module.class.isAssignableFrom(clazz)) {

                    Module module = (Module) clazz.newInstance();

                    put(module.getType(), module);
                }

            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
    }
}
