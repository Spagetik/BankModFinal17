package org.spagetik.bankmod.gui.atm;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.atm.sendtoaccount.SendToAccountGui;
import org.spagetik.bankmod.gui.atm.sendtocard.SendToCardGui;

public class SendGui extends AbstractAtmGui {

    private void runToCarsButton(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        GuiScreen.playAtmClickSound();
        GuiScreen.setScreen(new SendToCardGui(cardNum, cardHolder, ISPB, cardHash, cardPin));
    }

    private void runToAccountButton(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        GuiScreen.playAtmClickSound();
        GuiScreen.setScreen(new SendToAccountGui(cardNum, cardHolder, ISPB, cardHash, cardPin));
    }

    public SendGui(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        super(new ActionGui(cardNum, cardHolder, ISPB, cardHash, cardPin));

        setBankName();

        setExitButton(9, 4, 6, 1);

        setBackButton(0, 4, 6, 1);

        WButton toCardButton = new WButton()
                .setLabel(new LiteralText("На карту"))
                .setOnClick(() -> runToCarsButton(cardNum, cardHolder, ISPB, cardHash, cardPin));
        root.add(toCardButton, 0, 1, 6, 1);

        WButton toISPBButton = new WButton()
                .setLabel(new LiteralText("На счет"))
                .setOnClick(() -> runToAccountButton(cardNum, cardHolder, ISPB, cardHash, cardPin));
        root.add(toISPBButton, 9, 1, 6, 1);
    }
}
