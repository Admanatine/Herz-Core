package net.ada.mixins;

import net.ada.herz.core.Herz;
import net.ada.herz.core.api.eventbus.events.impl.core.ClientInitEvent;
import net.ada.herz.core.api.eventbus.events.impl.core.MinecraftInitEvent;
import net.ada.herz.core.api.eventbus.events.impl.core.TickEvent;
import net.ada.herz.core.api.eventbus.events.impl.input.*;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "run", at = @At("TAIL"))
    private void onInitialize(CallbackInfo ci) {
        Herz herz = new Herz();
    }
    @Inject(method = "startGame", at = @At("TAIL"))
    private void onStartGame(CallbackInfo ci) {
        Herz.INSTANCE.getEventBus().fireEvent(MinecraftInitEvent.class, new MinecraftInitEvent());
        Herz.INSTANCE.getEventBus().fireEvent(ClientInitEvent.class, new ClientInitEvent());
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    private void onBeginTick(CallbackInfo ci) {
        Herz.INSTANCE.getEventBus().fireEvent(TickEvent.class, new TickEvent());
    }

    @Redirect(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/lax1dude/eaglercraft/v1_8/Keyboard;next()Z"
            )
    )
    private boolean herz$keyboardNext() {
        boolean next = Keyboard.next();

        if (next) {
            if ((Keyboard.getEventKeyState())) {
                Herz.INSTANCE.getEventBus().fireEvent(KeyDownEvent.class, new KeyDownEvent(Keyboard.getEventCharacter(), Keyboard.getEventKey(), KeyInteractLocation.INGAME));
            }
            else {
                Herz.INSTANCE.getEventBus().fireEvent(KeyReleasedEvent.class, new KeyReleasedEvent(Keyboard.getEventCharacter(), Keyboard.getEventKey(), KeyInteractLocation.INGAME));
            }
        }
        return next;
    }
    @Redirect(
            method = "runTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/lax1dude/eaglercraft/v1_8/Mouse;next()Z"
            )
    )
    private boolean herz$mouseNext() {
        boolean next = Mouse.next();
        if (next) {
            int button = Mouse.getEventButton();
            int dx = Mouse.getDX();
            int dy = Mouse.getDY();
            int dwheel = Mouse.getDWheel();

            if (button >= 0 && Mouse.getEventButtonState()) {
                Herz.INSTANCE.getEventBus().fireEvent(MouseDownEvent.class,
                        new MouseDownEvent(button,
                                KeyInteractLocation.INGAME,
                                dx,
                                dy,
                                dwheel));
            }
            else if (button >= 0) {
                Herz.INSTANCE.getEventBus().fireEvent(MouseReleasedEvent.class,
                        new MouseReleasedEvent(button,
                                KeyInteractLocation.INGAME,
                                dx,
                                dy,
                                dwheel));
            }

            if (dwheel != 0) {
                Herz.INSTANCE.getEventBus().fireEvent(MouseScrollEvent.class,
                        new MouseScrollEvent(KeyInteractLocation.INGAME, dx, dy, dwheel));
            }
        }
        return next;
    }
}
