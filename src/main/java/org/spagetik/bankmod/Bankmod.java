package org.spagetik.bankmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spagetik.bankmod.commands.ModCommands;
import org.spagetik.bankmod.sounds.ModSounds;

import java.util.ArrayList;
import java.util.List;

public class Bankmod implements ModInitializer {

    public static final Item CARD_ITEM = Items.WRITTEN_BOOK;
    public static final Item PHONE_ITEM = Items.IRON_INGOT;
    public static final String PHONE_NAME = "sPhone";
    public static final String BANK_NAME = "SPragueBank Inc.";
    public static final String CURRENCY = "АР";
    public static final String API_URL = "https://spagetik.me/pluginapi";
    public static final int CARD_NUM_LEN = 16+3;
    public static final int PIN_MAX_LEN = 4;
    public static String CLIENT_SECRET_KEY = null;
    public static String CLIENT_SECRET_RESPONSE_KEY = null;
    public static final String MOD_ID = "bankmod";
    public static List<BlockPos> ATM_POSES = new ArrayList<>();
    public static World WORLD;
    public static PlayerEntity PLAYER;
    public static BlockPos CURRENT_ATM_POS;
    public static ModConfig CONFIG;
    public static String CLIENT_UUID;
    public static boolean ON_SPK;


    @Override
    public void onInitialize() {
        ModCommands.registerCommands();
        ModSounds.registerSounds();
        CONFIG = new ModConfig().registerConfigs();
        updateConfigs();
        ON_SPK = false;
    }

    public static void updateConfigs() {
        CLIENT_SECRET_KEY = CONFIG.clientKey;
        CLIENT_SECRET_RESPONSE_KEY = CONFIG.responseKey;
    }

    public static void reloadConfigsInFile() {
        Bankmod.CONFIG.writeConfigs();
        Bankmod.CONFIG = new ModConfig().registerConfigs();
        updateConfigs();
    }
}
