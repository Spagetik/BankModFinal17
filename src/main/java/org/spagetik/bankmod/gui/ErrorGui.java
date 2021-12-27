package org.spagetik.bankmod.gui;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.atm.AbstractAtmGui;

public class ErrorGui extends AbstractAtmGui {

    public ErrorGui(String errorText) {
        super(null);

        setSize(280, 30);

        setBankName();

        WButton errorButton = new WButton()
                .setLabel(new LiteralText(errorText))
                .setOnClick(GuiScreen::closeAtmGui);
        root.add(errorButton, 0, 1, 15, 1);
    }
}
