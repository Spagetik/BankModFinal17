package org.spagetik.bankmod.mixins;

import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.atm.WelcomeGui;
import org.spagetik.bankmod.sounds.ModSounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

@Mixin(WrittenBookItem.class)
public abstract class WrittenBookUseOnBlock {

    @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
    private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (context.getStack().getItem().equals(Bankmod.CARD_ITEM)) {
            if (Bankmod.ATM_POSES.contains(context.getBlockPos())) {
                if (context.getWorld().isClient) {
                    Bankmod.WORLD = context.getWorld();
                    Bankmod.PLAYER = context.getPlayer();
                    Bankmod.CURRENT_ATM_POS = context.getBlockPos();
                    ItemStack item = context.getStack();
                    NbtCompound nbt = item.getNbt();
                    assert nbt != null;
                    byte[] nbtBytes = SerializationUtils.serialize(nbt.toString());
                    String cardHash = DigestUtils.sha256Hex(nbtBytes);
                    HashMap<String, Object> data = BankApi.getCardNumByHash(cardHash);
                    if ((Boolean) data.get("success")) {
                        String cardNum = (String) data.get("cardNum");
                        cir.setReturnValue(ActionResult.success(true));
                        if (cardNum != null) {
                            if (cardNum.length() == Bankmod.CARD_NUM_LEN) {
                                GuiScreen.playPinSound();
                                GuiScreen.clickSound = ModSounds.ATM_CLICK;
                                GuiScreen.exitBttnSound = ModSounds.EXIT_ATM;
                                MinecraftClient.getInstance().setScreen(new GuiScreen(new WelcomeGui(cardNum, cardHash)));
                            }
                        }
                        else {
                            GuiScreen.openErrorAtmGui((String) data.get("error"));
                        }
                    }
                    else {
                        cir.setReturnValue(ActionResult.success(true));
                        GuiScreen.openErrorAtmGui((String) data.get("error"));
                    }
                }
            }
        }
    }
}
