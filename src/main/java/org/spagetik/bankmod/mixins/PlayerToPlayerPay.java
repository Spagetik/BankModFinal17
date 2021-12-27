package org.spagetik.bankmod.mixins;

import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.player.PlayerSendMoneyGui;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerToPlayerPay {

    @Shadow public abstract ItemStack eatFood(World world, ItemStack stack);

    @Shadow @Final private PlayerInventory inventory;

    @Inject(method = "interact", at = @At("HEAD"))
    private void useOnEntity(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (entity.world.isClient()) {
            if (entity.world.isClient()) {
                if (entity instanceof PlayerEntity) {
                    if(MinecraftClient.getInstance().options.keySneak.isPressed()) {
                        if (Bankmod.ON_SPK) {
                            PlayerEntity player = (PlayerEntity) entity;
                            UUID playerUuid = player.getUuid();
                            Bankmod.WORLD = entity.world;
                            Bankmod.PLAYER = this.inventory.player;
                            System.out.println(playerUuid);
                            GuiScreen.setScreen(new PlayerSendMoneyGui(player));
                        }
                    }
                }
            }
        }
    }
}
