package org.spagetik.bankmod.mixins;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.phone.PhoneWelcomeGui;
import org.spagetik.bankmod.sounds.ModSounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class NetheriteIngotMixin {

    @Inject(method = "use", at=@At("HEAD"))
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStackInHand = user.getStackInHand(hand);
        if (itemStackInHand.getItem().equals(Bankmod.PHONE_ITEM)) {
            if (itemStackInHand.getName().asString().contains(Bankmod.PHONE_NAME)) {
                System.out.println(Bankmod.ON_SPK);
                if (Bankmod.ON_SPK) {
                    if (world.isClient) {
                        Bankmod.WORLD = world;
                        Bankmod.PLAYER = user;
                        GuiScreen.clickSound = null;
                        GuiScreen.exitBttnSound = null;
                        GuiScreen.setScreen(new PhoneWelcomeGui());
                    }
                }
            }
        }
    }

}
