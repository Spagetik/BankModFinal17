package org.spagetik.bankmod.gui.phone.sendtoacount;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.phone.AbstractPhoneGui;
import org.spagetik.bankmod.gui.phone.PhoneSendGui;

import javax.imageio.spi.IIOServiceProvider;

public class PhoneSendToAccountGui extends AbstractPhoneGui {
    public PhoneSendToAccountGui(String login, String password, String ISPB, String balance) {
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

        WLabel toISPBlabel = new WLabel(new LiteralText("Номер счета").styled(style -> style.withBold(true)));
        toISPBlabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(toISPBlabel, 1, 6, 18, 1);

        WTextField toISPB = new WTextField();
        toISPB.setMaxLength(6);
        toISPB.setText("PA****");
        root.add(toISPB, 5, 7, 10, 1);

        WTextField amount = new WTextField();
        amount.setText("0");
        root.add(amount, 5, 11, 10, 1);

        WLabel amountLabel = new WLabel(new LiteralText("Сумма").styled(style -> style.withBold(true)));
        amountLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(amountLabel, 1, 10, 18, 1);

        WButton continueButton = new WButton(new LiteralText("ПРОДОЛЖИТЬ"));
        continueButton.setOnClick(() -> GuiScreen.setScreen(new PhoneSendToAccountSubmitGui(login, password, ISPB, balance, toISPB.getText(), amount.getText())));
        root.add(continueButton, 2, 16, 16, 1);
    }
}
