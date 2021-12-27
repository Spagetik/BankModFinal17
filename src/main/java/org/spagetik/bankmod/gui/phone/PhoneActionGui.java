package org.spagetik.bankmod.gui.phone;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;

import java.util.HashMap;

public class PhoneActionGui extends AbstractPhoneGui{

    public PhoneActionGui(String login, String password, String ISPB) {
        super(null);

        setPhoneName();
        setBorders();
        setPhoneSize();

        WText text = new WText(new LiteralText("Добро пожаловать в интернент банк, текущий аккаунт:\n").append(new LiteralText(ISPB)
                .styled(style -> style.withBold(true).withUnderline(true))));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(text, 1, 2, 18, 3);

        WButton balanceButton = new WButton(new LiteralText("Баланс"))
                .setOnClick(() -> {
                    HashMap<String, Object> data = BankApi.getBalanceByLoginAndPassword(login, password);
                    if (!(Boolean) data.get("success")) {
                        GuiScreen.openErrorAtmGui((String) data.get("error"));
                    }
                    else {
                        int balance = (Integer) data.get("balance");
                        GuiScreen.setScreen(new PhoneBalanceGui(login, password, ISPB, String.valueOf(balance)));
                    }
                });
        root.add(balanceButton, 2, 7, 16, 1);
        WButton sendMoneyButton = new WButton(new LiteralText("Отправить"))
                .setOnClick(() -> GuiScreen.setScreen(new PhoneSendGui(login, password, ISPB)));
        root.add(sendMoneyButton, 2, 11, 16, 1);
        WButton payBillButton = new WButton(new LiteralText("Оплатить"))
                .setOnClick(() -> {
                    assert true;
                });
        payBillButton.setEnabled(false);
        root.add(payBillButton, 2, 15, 16, 1);
        WButton createBillButton = new WButton(new LiteralText("Создать чек на оплату"))
                .setOnClick(() -> {
                    assert true;
                });
        createBillButton.setEnabled(false);
        root.add(createBillButton, 2, 19, 16, 1);

    }
}
