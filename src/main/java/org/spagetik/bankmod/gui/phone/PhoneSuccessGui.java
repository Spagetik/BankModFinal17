package org.spagetik.bankmod.gui.phone;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;

public class PhoneSuccessGui extends AbstractPhoneGui {

    public PhoneSuccessGui(String login, String password, String ISPB, String toISPB, String amount, String transUuid) {
        super(null);

        setPhoneName();
        setBorders();
        setPhoneSize();

        WText success0 = new WText(new LiteralText("Статус перевода:\n")
                .styled(style -> style.withColor(0x000000))
                .append(new LiteralText("УСПЕШНО")
                        .styled(style -> style.withColor(0x00FF00))));
        success0.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success0, 1, 2, 18, 2);

        WText success1 = new WText(new LiteralText("Получатель:\n")
                .styled(style -> style.withColor(0x000000))
                .append(new LiteralText(toISPB)
                        .styled(style -> style.withColor(0x428C9D))));
        success1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success1, 1, 5, 18, 2);

        WText success2 = new WText(new LiteralText("Сумма:\n")
                .styled(style -> style.withColor(0x000000))
                .append(new LiteralText(amount+" "+ Bankmod.CURRENCY)
                        .styled(style -> style.withColor(0x428C9D))));
        success2.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success2, 1, 8, 18, 2);

        WText success3 = new WText(new LiteralText(transUuid.replaceAll("-", ""))
                .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, transUuid.replaceAll("-", "")))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Нажмите чтобы скопировать индентификатор транзакции")))))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success3, 1, 11, 18, 2);

        WText success4 = new WText(new LiteralText("ссылка на онлайн чек").
                styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://spagetik.me/checktrans?code="+transUuid.replaceAll("-", ""))).
                        withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Нажмите для перехода на страницу с онлайн чеком")))))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success4, 1, 14, 18, 2);

        WButton homeButton = new WButton()
                .setLabel(new LiteralText("НА ГЛАВНУЮ"))
                .setOnClick(() -> GuiScreen.setScreen(new PhoneActionGui(login, password, ISPB)));
        root.add(homeButton, 2, 27, 16, 1);

    }
}
