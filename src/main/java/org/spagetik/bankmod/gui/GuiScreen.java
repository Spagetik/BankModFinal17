package org.spagetik.bankmod.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.sounds.ModSounds;

public class GuiScreen extends CottonClientScreen {
    public static SoundEvent exitBttnSound;
    public static SoundEvent clickSound;

    public GuiScreen(GuiDescription description) {
        super(description);
    }

    public static void openErrorAtmGui(String errorText) {
        MinecraftClient.getInstance().setScreen(new GuiScreen(new ErrorGui(errorText)));
    }

    public static void closeAtmGui() {
        MinecraftClient.getInstance().setScreen(null);
        playExitAtm();
    }

    public static void setScreen(GuiDescription description) {
        MinecraftClient.getInstance().setScreen(new GuiScreen(description));
    }

    public static void playPinSound() {
        if (Bankmod.WORLD == null) return;
        Bankmod.WORLD.playSound(Bankmod.PLAYER, Bankmod.CURRENT_ATM_POS, ModSounds.ENTER_PIN, SoundCategory.BLOCKS, 1f, 1f );
    }

    public static void playAtmClickSound() {
        if (Bankmod.WORLD == null) return;
        if(clickSound == null) return;
        Bankmod.WORLD.playSound(Bankmod.PLAYER, Bankmod.CURRENT_ATM_POS, clickSound, SoundCategory.BLOCKS, 1f, 1f );
    }

    public static void playExitAtm() {
        if (Bankmod.WORLD == null) return;
        if(exitBttnSound == null) return;
        Bankmod.WORLD.playSound(Bankmod.PLAYER, Bankmod.CURRENT_ATM_POS, exitBttnSound, SoundCategory.BLOCKS, 3f, 3f );
    }
}
