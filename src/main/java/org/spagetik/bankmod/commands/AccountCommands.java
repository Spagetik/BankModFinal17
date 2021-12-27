package org.spagetik.bankmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;

import java.util.HashMap;
import java.util.Objects;

public class AccountCommands {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("account")
                .then(ClientCommandManager.literal("create")
                        .then(ClientCommandManager.argument("login", StringArgumentType.string())
                                .then(ClientCommandManager.argument("password", StringArgumentType.string())
                                        .then(ClientCommandManager.argument("secret", StringArgumentType.string())
                                                .executes(AccountCommands::create)))))
                .then(ClientCommandManager.literal("connect")
                        .then(ClientCommandManager.literal("card")
                                .then(ClientCommandManager.argument("cardPin", StringArgumentType.string())
                                        .executes(AccountCommands::connectCard))))
                .then(ClientCommandManager.literal("info")
                        .executes(AccountCommands::info)));
    }

    private static int create(CommandContext<FabricClientCommandSource> context) {
        if (Bankmod.ATM_POSES.size() == 0) {
            context.getSource().sendFeedback(new LiteralText("Вы сейчас находитесь не на СПк"));
            return 0;
        }
        new Thread(() -> {
            String login = StringArgumentType.getString(context, "login");
            String password = StringArgumentType.getString(context, "password");
            String secret = StringArgumentType.getString(context, "secret");
            HashMap<String, Object> data = BankApi.createAccount(login, password, secret);
            if ((Boolean) data.get("success")) {
                context.getSource().sendFeedback(new LiteralText("Счет успешно создан, номер счета - " + data.get("ISPB")));
            }
            else {
                context.getSource().sendFeedback(new LiteralText((String) data.get("error")));
            }
        }).start();
        return 1;
    }

    private static int connectCard(CommandContext<FabricClientCommandSource> context) {
        if (Bankmod.ATM_POSES.size() == 0) {
            context.getSource().sendFeedback(new LiteralText("Вы сейчас находитесь не на СПк"));
            return 0;
        }
        new Thread(() -> {
            ItemStack inHandItem = context.getSource().getPlayer().getStackInHand(Hand.MAIN_HAND);
            NbtCompound nbt = inHandItem.getNbt();
            if (nbt == null || inHandItem.getItem() != Bankmod.CARD_ITEM) {
                context.getSource().sendFeedback(new LiteralText("Возьмите ПОДПИСАНУЮ КНИГУ в руки"));
            }
            else {
                byte[] nbtBytes = SerializationUtils.serialize(nbt.toString());
                String cardHash = DigestUtils.sha256Hex(nbtBytes);
                String cardPin = StringArgumentType.getString(context, "cardPin");
                HashMap<String, Object> data = BankApi.connectCardToAccount(cardPin, cardHash);
                if ((Boolean) data.get("success")) {
                    context.getSource().sendFeedback(new LiteralText("Нажмите чтобы скопировать данные своей карты")
                            .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "Номер карты: "+data.get("cardNum")+
                                    "\nPin: "+data.get("cardPin")+
                                    "\nCvv: "+data.get("cardCvv")))));
                }
                else {
                    context.getSource().sendFeedback(new LiteralText((String) data.get("error")));
                }
            }
        }).start();
        return 1;
    }

    private static int info(CommandContext<FabricClientCommandSource> context) {
        if (Bankmod.ATM_POSES.size() == 0) {
            context.getSource().sendFeedback(new LiteralText("Вы сейчас находитесь не на СПк"));
            return 0;
        }
        new Thread(() -> {
            String ISPB = BankApi.getAccountISPB();
            if (!Objects.equals(ISPB, "Not found"))
            context.getSource().sendFeedback(new LiteralText("Номер вашего счета - "+BankApi.getAccountISPB())
                    .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, ISPB)))
                    .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Нажмите чтобы скопировать номер счета")))));
            else context.getSource().sendFeedback(new LiteralText("У вас нет активного счета"));
        }).start();
        return 1;
    }
}
