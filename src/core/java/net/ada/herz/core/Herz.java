package net.ada.herz.core;

import net.ada.herz.core.api.data.packages.HerzPackage;
import net.ada.herz.core.api.eventbus.events.IEvent;
import net.ada.herz.core.api.eventbus.events.impl.core.ClientInitEvent;
import net.ada.herz.core.api.eventbus.events.impl.core.MinecraftInitEvent;
import net.ada.herz.core.api.eventbus.handler.EventBus;
import net.ada.herz.core.api.eventbus.handler.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Herz {
    List<HerzPackage> herzPackageList;
    EventBus eventBus;
    Logger logger;
    public static Herz INSTANCE;
    public Herz() {
        INSTANCE = this;
        eventBus = new EventBus();
        herzPackageList = new ArrayList<>();
        logger = Logger.getLogger("[Herz Logger]");
        // register core event
        registerEvents();
        registerListeners();

    }
    public void registerEvents() {
        eventBus.addEvent(new EventHandler<>(ClientInitEvent.class));
        eventBus.addEvent(new EventHandler<>(MinecraftInitEvent.class));
    }
    public void registerListeners() {
        eventBus.registerListener(MinecraftInitEvent.class, event -> {
            logger.info("Minecraft has initialized!");
        });
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public List<HerzPackage> getPackageList() {
        return herzPackageList;
    }

}
