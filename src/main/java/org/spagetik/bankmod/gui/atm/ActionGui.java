package org.spagetik.bankmod.gui.atm;

import io.github.cottonmc.cotton.gui.widget.WButton;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.gui.GuiScreen;

import java.util.HashMap;

public class ActionGui extends AbstractAtmGui {

    private void runBalanceButton(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        GuiScreen.playAtmClickSound();
        HashMap<String, Object> data = BankApi.getBalanceByHash(cardHash);
        String balance = String.valueOf(data.get("balance"));
        if (balance.equals("null")) {
            GuiScreen.openErrorAtmGui(String.valueOf(data.get("error")));
        }
        else {
            GuiScreen.setScreen(new BalanceGui(cardNum, cardHolder, ISPB, cardHash, balance, cardPin));
        }
    }

    private void runInfoButton(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        GuiScreen.playAtmClickSound();
        HashMap<String, Object> data = BankApi.getBalanceByHash(cardHash);
        String balance = String.valueOf(data.get("balance"));
        if (balance.equals("null")) {
            GuiScreen.openErrorAtmGui(String.valueOf(data.get("error")));
        }
        else {
            GuiScreen.setScreen(new CardInfoGui(cardNum, cardHolder, ISPB, cardHash, balance, cardPin));
        }
    }

    private void runSendButton(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        GuiScreen.playAtmClickSound();
        GuiScreen.setScreen(new SendGui(cardNum, cardHolder, ISPB, cardHash, cardPin));
    }

    public ActionGui(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        super(null);

        setBankName();

        setExitButton(5, 8, 5, 1);

        WButton payButton = new WButton()
                .setLabel(new LiteralText("Оплатить"))
                .setEnabled(false)
                .setOnClick(() -> {
            assert true;
        });
        root.add(payButton, 0, 1, 5, 1);

        WButton sendButton = new WButton()
                .setLabel(new LiteralText("Перевести"))
                .setOnClick(() -> runSendButton(cardNum, cardHolder, ISPB, cardHash, cardPin));
        root.add(sendButton, 0, 3, 5, 1);

        WButton balanceButton = new WButton()
                .setLabel(new LiteralText("Баланс"))
                .setOnClick(() -> runBalanceButton(cardNum, cardHolder, ISPB, cardHash, cardPin));
        root.add(balanceButton, 10, 1, 5, 1);

        WButton infoButton = new WButton()
                .setLabel(new LiteralText("О карте"))
                .setOnClick(() -> runInfoButton(cardNum, cardHolder, ISPB, cardHash, cardPin));
        root.add(infoButton, 10, 3, 5, 1);
    }
}
