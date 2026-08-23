package net.ada.mixins;

import net.ada.herz.core.Herz;
import net.ada.herz.core.api.eventbus.events.impl.core.MinecraftInitEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "<init>", at = @At("HEAD"))
    private void onInit(CallbackInfo ci) {

        Herz herz = new Herz();
        System.out.println("Initialized Herz");
    }
    @Inject(method = "run", at = @At("HEAD"))
    private void onInitialize(CallbackInfo ci) {
        Herz.INSTANCE.getEventBus().fireEvent(MinecraftInitEvent.class, new MinecraftInitEvent());
    }
}
