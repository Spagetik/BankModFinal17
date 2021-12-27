package org.spagetik.bankmod.gui.phone;

import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;

public class PhoneBalanceGui extends AbstractPhoneGui {

    public PhoneBalanceGui(String login, String password, String ISPB, String balance) {
        super(new PhoneActionGui(login, password, ISPB));

        setBorders();
        setPhoneSize();
        setPhoneName();
        setBackButton(2, 27, 16, 1);

        WText text = new WText(new LiteralText("Текущий баланс счета составляет:\n").append(new LiteralText(balance+" "+ Bankmod.CURRENCY)
                .styled(style -> style.withBold(true))));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);
        WText text1 = new WText(new LiteralText("Для пополнения счета или снятия средств явитесь в ближайший банк для совершения операции"));
        text1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(text, 1, 8, 18, 3);
        root.add(text1, 1, 12, 18, 4);
    }
}
