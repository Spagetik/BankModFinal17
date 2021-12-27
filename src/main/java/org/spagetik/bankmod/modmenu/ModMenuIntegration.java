package org.spagetik.bankmod.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.ModConfig;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(new LiteralText("BankMod"));
            ConfigCategory general = builder.getOrCreateCategory(new LiteralText("BankMod"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();
            general.addEntry(entryBuilder.startStrField(new LiteralText("Client secret key"), Bankmod.CONFIG.clientKey)
                    .setDefaultValue("null") // Recommended: Used when user click "Reset"
                    .setSaveConsumer(newKey -> Bankmod.CONFIG.clientKey = newKey) // Recommended: Called when user save the config
                    .build()); // Builds the option entry for cloth config
            general.addEntry(entryBuilder.startStrField(new LiteralText("Response secret key"), Bankmod.CONFIG.responseKey)
                    .setDefaultValue("null") // Recommended: Used when user click "Reset"
                    .setSaveConsumer(newKey -> Bankmod.CONFIG.responseKey = newKey) // Recommended: Called when user save the config
                    .build()); // Builds the option entry for cloth config
            builder.setSavingRunnable(Bankmod::reloadConfigsInFile);
            return builder.build();
        };
    }
}
