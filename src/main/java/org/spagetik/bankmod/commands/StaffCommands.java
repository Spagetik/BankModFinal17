package org.spagetik.bankmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;

import java.util.HashMap;


public class StaffCommands {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("staff")
                .then(ClientCommandManager.literal("money")
                        .then(ClientCommandManager.literal("put")
                                .then(ClientCommandManager.argument("nickname", StringArgumentType.string())
                                        .suggests((context, builder) -> CommandSource.suggestMatching(context.getSource().getPlayerNames(), builder))
                                        .then(ClientCommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(StaffCommands::addMoneys))))
                        .then(ClientCommandManager.literal("withdraw")
                                .then(ClientCommandManager.argument("nickname", StringArgumentType.string())
                                        .suggests((context, builder) -> CommandSource.suggestMatching(context.getSource().getPlayerNames(), builder))
                                        .then(ClientCommandManager.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(StaffCommands::removeMoneys))))));
    }

    private static int addMoneys(CommandContext<FabricClientCommandSource> context) {
        String nickname = StringArgumentType.getString(context, "nickname");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        new Thread(() -> {
            HashMap<String, Object> data = BankApi.addMoneys(nickname, amount);
            if ((Boolean) data.get("success")) {
                context.getSource().sendFeedback(new LiteralText("Счет игрока "+nickname+" успешно пополнен на "+amount+" "+ Bankmod.CURRENCY)
                        .styled(style -> style.withColor(0x00FF00)));
            }
            else {
                context.getSource().sendFeedback(new LiteralText((String) data.get("error"))
                        .styled(style -> style.withColor(0xFF0000)));
            }
        }).start();
        return 1;
    }

    private static int removeMoneys(CommandContext<FabricClientCommandSource> context) {
        String nickname = StringArgumentType.getString(context, "nickname");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        new Thread(() -> {
            HashMap<String, Object> data = BankApi.removeMoneys(nickname, amount);
            if ((Boolean) data.get("success")) {
                context.getSource().sendFeedback(new LiteralText("Со счета игрока "+nickname+" успешно снято "+amount+" "+ Bankmod.CURRENCY)
                        .styled(style -> style.withColor(0x00FF00)));
            }
            else {
                context.getSource().sendFeedback(new LiteralText((String) data.get("error"))
                        .styled(style -> style.withColor(0xFF0000)));
            }
        }).start();
        return 1;
    }
}
