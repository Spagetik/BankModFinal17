package org.spagetik.bankmod.gui.phone.sendtocard;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.phone.AbstractPhoneGui;
import org.spagetik.bankmod.gui.phone.PhoneSendGui;

public class PhoneSendToCardGui extends AbstractPhoneGui {
    public PhoneSendToCardGui(String login, String password, String ISPB, String balance) {
        super(new PhoneSendGui(login, password, ISPB));

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

        WLabel toCardlabel = new WLabel(new LiteralText("Номер карты").styled(style -> style.withBold(true)));
        toCardlabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(toCardlabel, 1, 6, 18, 1);

        WTextField toCard = new WTextField();
        toCard.setMaxLength(16);
        toCard.setSuggestion("0000000000000000");
        root.add(toCard, 4, 7, 12, 1);

        WTextField amount = new WTextField();
        amount.setText("0");
        root.add(amount, 5, 11, 10, 1);

        WLabel amountLabel = new WLabel(new LiteralText("Сумма").styled(style -> style.withBold(true)));
        amountLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(amountLabel, 1, 10, 18, 1);

        WButton continueButton = new WButton(new LiteralText("ПРОДОЛЖИТЬ"));
        continueButton.setOnClick(() -> GuiScreen.setScreen(new PhoneSendToCardSubmitGui(login, password, ISPB, balance, toCard.getText(), amount.getText())));
        root.add(continueButton, 2, 16, 16, 1);
    }
}
