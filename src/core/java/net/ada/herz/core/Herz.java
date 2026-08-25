package net.ada.herz.core;

import net.ada.herz.core.api.data.packages.HerzPackage;
import net.ada.herz.core.api.eventbus.events.IEvent;
import net.ada.herz.core.api.eventbus.events.impl.core.ClientInitEvent;
import net.ada.herz.core.api.eventbus.events.impl.core.MinecraftInitEvent;
import net.ada.herz.core.api.eventbus.handler.EventBus;
import net.ada.herz.core.api.eventbus.handler.EventHandler;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Herz {
    List<HerzPackage> herzPackageList;
    EventBus eventBus;
    Logger logger;
    public static Herz INSTANCE;
    public Herz() {
        eventBus = new EventBus();
        herzPackageList = new ArrayList<>();
        logger = LogManager.getLogger("[Herz]");
        // register core event
        registerEvents();
        registerListeners();
        INSTANCE = this;
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
