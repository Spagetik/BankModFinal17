package org.spagetik.bankmod.mixins;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WButton.class)
public abstract class WButtonClickMixin {
    
    @Redirect(method = "onClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;play(Lnet/minecraft/client/sound/SoundInstance;)V"))
    private void play(SoundManager instance, SoundInstance sound) {
    }
}
