package org.spagetik.bankmod.gui.phone.sendtocard;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.phone.AbstractPhoneGui;
import org.spagetik.bankmod.gui.phone.PhoneSuccessGui;
import org.spagetik.bankmod.gui.phone.sendtoacount.PhoneSendToAccountSubmitGui;

import java.util.HashMap;

public class PhoneSendToCardSubmitGui extends AbstractPhoneGui {
    public PhoneSendToCardSubmitGui(String login, String password, String ISPB, String balance, String toCard, String amount) {
        super(new PhoneSendToCardGui(login, password, ISPB, balance));

        setBorders();
        setBorders();
        setPhoneName();
        setPhoneSize();
        setBackButton(2, 27, 16, 1);

        WText text = new WText(new LiteralText("Доступно для переводов:\n")
                .append(new LiteralText(balance+" "+ Bankmod.CURRENCY)
                        .styled(style -> style.withBold(true))));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(text, 1, 2, 18, 3);

        WLabel toISPBlabel = new WLabel(new LiteralText("Номер счета").styled(style -> style.withBold(true)));
        toISPBlabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(toISPBlabel, 1, 6, 18, 1);

        WTextField toCardField = new WTextField();
        toCardField.setMaxLength(16);
        toCardField.setText(toCard);
        toCardField.setEditable(false);
        root.add(toCardField, 4, 7, 12, 1);

        WTextField amountField = new WTextField();
        amountField.setText(amount);
        amountField.setEditable(false);
        root.add(amountField, 5, 11, 10, 1);

        WLabel amountLabel = new WLabel(new LiteralText("Сумма").styled(style -> style.withBold(true)));
        amountLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(amountLabel, 1, 10, 18, 1);

        WButton continueButton = new WButton(new LiteralText("ПОДТВЕРДИТЬ"));
        continueButton.setOnClick(() -> {
            HashMap<String, Object> data = BankApi.sendMoneyFromAccountToCard(login, password, toCard, amount);
            if (!(Boolean) data.get("success")) {
                GuiScreen.openErrorAtmGui((String) data.get("error"));
            }
            else {
                GuiScreen.setScreen(new PhoneSuccessGui(login, password, ISPB, toCard, amount, String.valueOf(data.get("uuid"))));
            }
        });
        root.add(continueButton, 2, 16, 16, 1);
    }
}
