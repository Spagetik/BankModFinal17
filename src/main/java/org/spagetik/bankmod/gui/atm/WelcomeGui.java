package org.spagetik.bankmod.gui.atm;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;
import org.jetbrains.annotations.NotNull;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;

import java.util.HashMap;

public class WelcomeGui extends AbstractAtmGui {

    private void runLoginButton(String cardPin, String cardHash) {
        GuiScreen.playAtmClickSound();
        HashMap<String, Object> data = BankApi.getCardAndAccountDataByHashAndPin(cardHash, cardPin);
        Boolean success = (Boolean) data.get("success");
        if (success) {
            openActionScreen(data, cardHash, cardPin);
        }
        else {
            GuiScreen.openErrorAtmGui((String) data.get("error"));
        }
    }

    private void openActionScreen(@NotNull HashMap<String, Object> data, String cardHash, String cardPin) {
        String cardISPB = (String) data.get("cardISPB");
        String cardHolder = (String) data.get("cardHolder");
        String cardNum = (String) data.get("cardNum");
        // System.out.println("----" + data);
        GuiScreen.setScreen(new ActionGui(cardNum, cardHolder, cardISPB, cardHash, cardPin));
    }

    public WelcomeGui(String cardNumber, String cardHash) {
        super(null);

        setBankName();

        setExitButton(5, 8, 5, 1);
        
        WLabel cardNumberLabel = new WLabel(new LiteralText("Номер карты:"));
        root.add(cardNumberLabel, 1, 2);
        
        WLabel cardNumberField = new WLabel(new LiteralText(cardNumber));
        root.add(cardNumberField, 8, 2, 5, 1);
        
        WTextField pinField = new WTextField()
                .setMaxLength(Bankmod.PIN_MAX_LEN)
                .setSuggestion("PIN");
        root.add(pinField, 5, 4, 5, 1);
        
        WButton loginButton = new WButton()
                .setLabel(new LiteralText("АВТОРИЗИРОВАТЬСЯ"))
                .setOnClick(() -> runLoginButton(pinField.getText(), cardHash));
        root.add(loginButton, 4, 6, 7, 1);
    }
}
