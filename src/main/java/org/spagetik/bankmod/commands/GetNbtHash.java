package org.spagetik.bankmod.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.*;
import net.minecraft.util.Hand;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationUtils;

public class GetNbtHash {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("getNbtHash")
                .executes(GetNbtHash::execute));
    }

    private static int execute(CommandContext<FabricClientCommandSource> context) {
        new Thread(() -> {
            ItemStack inHandItem = context.getSource().getPlayer().getStackInHand(Hand.MAIN_HAND);
            NbtCompound nbt = inHandItem.getNbt();
            if (nbt == null) {
                return;
            }
            byte[] nbtBytes = SerializationUtils.serialize(nbt.toString());
            String cardHash = DigestUtils.sha256Hex(nbtBytes);
            MutableText text = new LiteralText("Нажми чтобы скопировать NbtHash").styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, cardHash)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.copy.click"))));
            context.getSource().sendFeedback(text);
        });
        return 1;
    }
}
