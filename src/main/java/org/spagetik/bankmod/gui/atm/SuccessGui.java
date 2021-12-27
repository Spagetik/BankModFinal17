package org.spagetik.bankmod.gui.atm;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;

public class SuccessGui extends AbstractAtmGui {

    private void runHomeButton(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin) {
        GuiScreen.playAtmClickSound();
        GuiScreen.setScreen(new ActionGui(cardNum, cardHolder, ISPB, cardHash, cardPin));
    }

    public SuccessGui(String cardNum, String cardHolder, String ISPB, String cardHash, String cardPin, String toISPB, String amount, String transUuid) {
        super(null);

        setBankName();

        setExitButton(9, 6, 6, 1);

        WLabel success00 = new WLabel("Статус перевода:", 0x000000);
        WLabel success01 = new WLabel("УСПЕШНО", 0x00FF00);
        success00.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        success01.setHorizontalAlignment(HorizontalAlignment.LEFT);
        root.add(success00, 0, 1, 7, 1);
        root.add(success01, 9, 1);

        WLabel success10 = new WLabel("Получатель:", 0x000000);
        WLabel success11 = new WLabel(toISPB, 0x428C9D);
        success10.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        success11.setHorizontalAlignment(HorizontalAlignment.LEFT);
        root.add(success10, 0, 2, 7,1);
        root.add(success11, 9, 2);

        WLabel success20 = new WLabel("Сумма:", 0x000000);
        WLabel success21 = new WLabel(amount + " " + Bankmod.CURRENCY, 0x428C9D);
        success20.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        success21.setHorizontalAlignment(HorizontalAlignment.LEFT);
        root.add(success20, 0, 3, 7,1);
        root.add(success21, 9, 3);

        WLabel success3 = new WLabel(new LiteralText(transUuid.replaceAll("-", ""))
                .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, transUuid.replaceAll("-", "")))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Нажмите чтобы скопировать индентификатор транзакции")))))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success3, 3, 4, 9,1);

        WLabel success4 = new WLabel(new LiteralText("ссылка на онлайн чек").
                styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://spagetik.me/checktrans?code="+transUuid.replaceAll("-", ""))).
                        withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Нажмите для перехода на страницу с онлайн чеком")))))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success4, 3, 5, 9, 1);

        WButton homeButton = new WButton()
                .setLabel(new LiteralText("НА ГЛАВНУЮ"))
                .setOnClick(() -> runHomeButton(cardNum, cardHolder, ISPB, cardHash, cardPin));
        root.add(homeButton, 0, 6, 6, 1);
    }
}