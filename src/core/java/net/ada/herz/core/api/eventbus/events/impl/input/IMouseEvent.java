package net.ada.herz.core.api.eventbus.events.impl.input;


import net.ada.herz.core.api.eventbus.events.IEvent;

public interface IMouseEvent extends IEvent {
    int getKeyCode();

    KeyInteractLocation getKeyInteractLocation();

    int getDX();

    int getDY();

    int getDwheel();
}
