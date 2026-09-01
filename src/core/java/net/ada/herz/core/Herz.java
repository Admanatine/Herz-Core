package net.ada.herz.core;

import net.ada.herz.core.api.config.HerzConfigNamespace;
import net.ada.herz.core.api.config.IConfigPersistence;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Herz {
    List<HerzPackage> herzPackageList;
    EventBus eventBus;
    static Logger logger;
    public static Herz INSTANCE;

    private IConfigPersistence configPersistence = new IConfigPersistence() {
        // No-op persistence used until a platform registers a real one.
        private final Map<String, Map<String, String>> store = new HashMap<>();
        public Map<String, String> load(String ns) { return store.getOrDefault(ns, new HashMap<>()); }
        public void save(String ns, Map<String, String> vals) { store.put(ns, new HashMap<>(vals)); }
    };
    private final Map<String, HerzConfigNamespace> configNamespaces = new HashMap<>();

    public Herz() {
        eventBus = new EventBus();
        herzPackageList = new ArrayList<>();
        logger = LogManager.getLogger("Herz");
        // register core event
        registerEvents();
        registerListeners();
        INSTANCE = this;
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
        eventBus.registerListener(MinecraftInitEvent.class, event -> {
            logger.info("Booting Minecraft");
        });
        eventBus.registerListener(ClientInitEvent.class, event -> {
            logger.info("Initialized Herz Client Events");
        });
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

    /**
     * Register a platform-specific persistence backend for the Config API.
     * Call this before any package loads its config (e.g. at platform init).
     */
    public void setConfigPersistence(IConfigPersistence persistence) {
        this.configPersistence = persistence;
    }

    /**
     * Get or create a config namespace for the given identifier.
     * Use your package's reverse-domain name, e.g. "com.example.mypkg".
     */
    public HerzConfigNamespace getConfig(String namespace) {
        return configNamespaces.computeIfAbsent(namespace,
                ns -> new HerzConfigNamespace(ns, configPersistence));
    }
}
