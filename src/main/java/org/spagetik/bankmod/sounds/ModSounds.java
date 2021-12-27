package org.spagetik.bankmod.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spagetik.bankmod.Bankmod;

public class ModSounds {

    public static SoundEvent ENTER_PIN = registerSoundEvent("enter_pin");
    public static SoundEvent ATM_CLICK = registerSoundEvent("atm_click");
    public static SoundEvent EXIT_ATM = registerSoundEvent("exit_atm");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(Bankmod.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void registerSounds() {
        System.out.println("Sounds registered for " + Bankmod.MOD_ID);
    }
}
