package com.gabriaum.skills.user.assets;

import com.gabriaum.skills.user.User;
import com.gabriaum.skills.user.assets.type.ModuleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Module {

    protected final ModuleType type;

    public abstract void execute(User user);
}
