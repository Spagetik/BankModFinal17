package org.spagetik.bankmod.gui.atm;

import io.github.cottonmc.cotton.gui.widget.WLabel;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;

public class CardInfoGui extends AbstractAtmGui {

    public CardInfoGui(String cardNum, String cardHolder, String ISPB, String cardHash, String balance, String cardPin) {
        super(new ActionGui(cardNum, cardHolder, ISPB, cardHash, cardPin));

        setBankName();

        setExitButton(9, 7, 6, 1);

        setBackButton(0, 7, 6, 1);

        WLabel cardNumberLabel = new WLabel(new LiteralText("Номер карты:"));
        root.add(cardNumberLabel, 1, 2);

        WLabel cardNumberField = new WLabel(new LiteralText(cardNum));
        root.add(cardNumberField, 8, 2, 5, 1);

        WLabel cardHolderLabel = new WLabel(new LiteralText("Владелец карты:"));
        root.add(cardHolderLabel, 1, 3);

//        String uuid = cardHolder.substring(0, 8)+"-"
//                +cardHolder.substring(8, 12)+"-"
//                +cardHolder.substring(12, 16)+"-"
//                +cardHolder.substring(16, 20)+"-"
//                +cardHolder.substring(20);


        WLabel cardHolderField = new WLabel(new LiteralText(BankApi.getPlayerNameByUuid(cardHolder)));
        root.add(cardHolderField, 8, 3, 5, 1);

        WLabel cardAccountLabel = new WLabel(new LiteralText("Счет карты:"));
        root.add(cardAccountLabel, 1, 4);

        WLabel cardAccountField = new WLabel(new LiteralText(ISPB));
        root.add(cardAccountField, 8, 4, 5, 1);

        WLabel cardBalanceLabel = new WLabel(new LiteralText("Баланс:"));
        root.add(cardBalanceLabel, 1, 5);

        WLabel cardBalanceField = new WLabel(new LiteralText(balance + " " + Bankmod.CURRENCY));
        root.add(cardBalanceField, 8, 5, 5, 1);
    }
}
