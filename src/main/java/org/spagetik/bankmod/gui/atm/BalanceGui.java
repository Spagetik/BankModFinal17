package org.spagetik.bankmod.gui.atm;

import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;

public class BalanceGui extends AbstractAtmGui {

    public BalanceGui(String cardNum, String cardHolder, String ISPB, String cardHash, String balance, String cardPin) {
        super(new ActionGui(cardNum, cardHolder, ISPB, cardHash, cardPin));

        setSize(280, 60);

        setBankName();

        setExitButton(9, 2,6, 1);

        setBackButton(0,2, 6, 1);

        WLabel cardBalanceLabel = new WLabel(new LiteralText("Баланс:"));
        root.add(cardBalanceLabel, 5, 1);

        WLabel cardBalanceField = new WLabel(new LiteralText(balance + " " + Bankmod.CURRENCY));
        root.add(cardBalanceField, 8, 1, 5, 1);
    }
}
