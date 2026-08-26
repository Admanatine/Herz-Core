package net.ada.herz.core;

import net.ada.herz.core.api.data.packages.HerzPackage;
import net.ada.herz.core.api.eventbus.events.impl.core.ClientInitEvent;
import net.ada.herz.core.api.eventbus.events.impl.core.MinecraftInitEvent;
import net.ada.herz.core.api.eventbus.events.impl.core.TickEvent;
import net.ada.herz.core.api.eventbus.events.impl.input.*;
import net.ada.herz.core.api.eventbus.handler.EventBus;
import net.ada.herz.core.api.eventbus.handler.EventHandler;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Herz {
    List<HerzPackage> herzPackageList;
    EventBus eventBus;
    static Logger logger;
    public static Herz INSTANCE;
    public Herz() {
        eventBus = new EventBus();
        herzPackageList = new ArrayList<>();
        logger = LogManager.getLogger("[Herz]");
        // register core event
        registerEvents();
        registerListeners();
        INSTANCE = this;
        eventBus.fireEvent(ClientInitEvent.class, new ClientInitEvent());
    }
    public void registerEvents() {
        eventBus.addEvent(new EventHandler<>(ClientInitEvent.class));
        eventBus.addEvent(new EventHandler<>(MinecraftInitEvent.class));
        eventBus.addEvent(new EventHandler<>(KeyDownEvent.class));
        eventBus.addEvent(new EventHandler<>(KeyReleasedEvent.class));
        eventBus.addEvent(new EventHandler<>(TickEvent.class));
        eventBus.addEvent(new EventHandler<>(MouseDownEvent.class));
        eventBus.addEvent(new EventHandler<>(MouseReleasedEvent.class));
        eventBus.addEvent(new EventHandler<>(MouseScrollEvent.class));
    }
    public void registerListeners() {

    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public List<HerzPackage> getPackageList() {
        return herzPackageList;
    }

    public Logger getLogger() {
        return logger;
    }
}
