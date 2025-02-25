package com.gabriaum.skills.util.inventory.event.scheduler;

import com.gabriaum.skills.util.inventory.event.UpdateEvent;
import com.gabriaum.skills.util.inventory.event.type.UpdateType;

public class UpdateScheduler implements Runnable {

    private long totalTicks;

    @Override
    public void run() {
        totalTicks++;

        if (totalTicks % 20 == 0)
            new UpdateEvent(UpdateType.SECOND, totalTicks).push();
    }
}
