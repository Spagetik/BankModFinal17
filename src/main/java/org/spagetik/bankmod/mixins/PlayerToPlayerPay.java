package org.spagetik.bankmod.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spagetik.bankmod.Bankmod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class PlayerToPlayerPay {

    @Inject(method = "useOnEntity", at = @At("HEAD"))
    private void useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (MinecraftClient.getInstance().world != null)
            if (MinecraftClient.getInstance().world.isClient)
                if (stack.getItem() == Bankmod.PAYPLAYER_ITEM)
                    if (entity instanceof PlayerEntity) {
                        PlayerEntity toPlayer = (PlayerEntity) entity;
                    }
    }
}
