package org.spagetik.bankmod.commands;

import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;

public class ModCommands {
    public static void registerCommands() {
//        GetNbtHash.register(ClientCommandManager.DISPATCHER);
        AccountCommands.register(ClientCommandManager.DISPATCHER);
        StaffCommands.register(ClientCommandManager.DISPATCHER);
    }

    public static void unregisterCommands() {

    }
}
