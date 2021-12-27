package org.spagetik.bankmod.gui.phone;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.phone.sendtoacount.PhoneSendToAccountGui;
import org.spagetik.bankmod.gui.phone.sendtocard.PhoneSendToCardGui;

import java.util.HashMap;

public class PhoneSendGui extends AbstractPhoneGui{

    public PhoneSendGui(String login, String password, String ISPB) {
        super(new PhoneActionGui(login, password, ISPB));
        setPhoneSize();
        setPhoneName();
        setBorders();
        setBackButton(2,27, 16, 1);
        WText text = new WText(new LiteralText("Выберете способ для перевода денег"));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(text, 1, 2, 18, 3);
        WButton toAccount = new WButton(new LiteralText("На счет"))
                .setOnClick(() -> {
                    HashMap<String, Object> data = BankApi.getBalanceByLoginAndPassword(login, password);
                    if (!(Boolean) data.get("success")) {
                        GuiScreen.openErrorAtmGui((String) data.get("error"));
                    }
                    else {
                        GuiScreen.setScreen(new PhoneSendToAccountGui(login, password, ISPB, String.valueOf(data.get("balance"))));
                    }
                });
        root.add(toAccount, 2, 6, 16, 1);
        WButton toCard = new WButton(new LiteralText("На карту"))
                .setOnClick(() -> {
                    HashMap<String, Object> data = BankApi.getBalanceByLoginAndPassword(login, password);
                    if (!(Boolean) data.get("success")) {
                        GuiScreen.openErrorAtmGui((String) data.get("error"));
                    }
                    else {
                        GuiScreen.setScreen(new PhoneSendToCardGui(login, password, ISPB, String.valueOf(data.get("balance"))));
                    }
                });
        root.add(toCard, 2, 10, 16, 1);
    }
}
