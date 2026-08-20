package net.ada.herz.core.api.eventbus.events;

@FunctionalInterface
public interface IEventListener<E extends IEvent> {
    void execute(E event);
}