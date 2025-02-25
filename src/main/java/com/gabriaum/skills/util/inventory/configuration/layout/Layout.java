package com.gabriaum.skills.util.inventory.configuration.layout;

import com.gabriaum.skills.util.inventory.configuration.layout.frame.Frame;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Layout {

    private final int slot;
    private final Frame frame;
}
