package org.spagetik.bankmod.gui.atm.sendtocard;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.atm.AbstractAtmGui;
import org.spagetik.bankmod.gui.atm.SendGui;

import java.util.HashMap;

public class SendToCardGui extends AbstractAtmGui {

    private void runSubmitButton(String cardNum, String cardHolder, String ISPB, String cardHash, String toCardNum, String amount, String cardPin) {
        GuiScreen.playAtmClickSound();
        GuiScreen.setScreen(new SendToCardGuiSubmit(cardNum, cardHolder, ISPB, cardHash, toCardNum, amount, cardPin));
    }

    public SendToCardGui(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        super(new SendGui(cardNum, cardHolder, ISPB, cardHash, cardPin));
        HashMap<String, Object> data = BankApi.getBalanceByHash(cardHash);
        String balance = String.valueOf(data.get("balance"));
        if (balance.equals("null")) {
            GuiScreen.openErrorAtmGui(String.valueOf(data.get("error")));
            return;
        }

        setBankName();

        setExitButton(9, 8, 6, 1);

        setBackButton(0, 8, 6, 1);

        WLabel balanceLabel = new WLabel(new LiteralText("Доступно для перевода: " + balance + " " + Bankmod.CURRENCY));
        balanceLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        balanceLabel.setColor(0x4CC62A);
        root.add(balanceLabel, 0, 1, 15, 1);

        WTextField toAccountField = new WTextField();
        toAccountField.setMaxLength(16);
        toAccountField.setSuggestion("0000000000000000");
        root.add(toAccountField, 8, 2, 6, 1);

        WLabel SugAccountLabel = new WLabel(new LiteralText("Номер карты получателя"));
        SugAccountLabel.setVerticalAlignment(VerticalAlignment.CENTER);
        SugAccountLabel.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        root.add(SugAccountLabel, 6, 2);

        WLabel amountLabel = new WLabel(new LiteralText("Введите сумму"));
        amountLabel.setVerticalAlignment(VerticalAlignment.CENTER);
        amountLabel.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        root.add(amountLabel, 6, 4);

        WTextField toAmountField = new WTextField(new LiteralText("0"))
                .setMaxLength(7);
        root.add(toAmountField, 8, 4, 6, 1);

        WButton submitButton = new WButton()
                .setLabel(new LiteralText("Продолжить"))
                .setOnClick(() -> runSubmitButton(cardNum, cardHolder, ISPB, cardHash, toAccountField.getText(), toAmountField.getText(), cardPin));
        root.add(submitButton, 5, 6, 5, 1);
    }
}
