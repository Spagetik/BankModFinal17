package org.spagetik.bankmod.gui.player;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.phone.AbstractPhoneGui;
import org.spagetik.bankmod.gui.phone.PhoneActionGui;

public class PlayerSuccesGui extends AbstractPhoneGui {

    public PlayerSuccesGui(PlayerEntity player, String amount, String transUuid) {
        super(null);
        root = new WGridPanel(9);
        setRootPanel(root);
        root.setSize(180, 200);
        root.setInsets(Insets.ROOT_PANEL);

        WText success0 = new WText(new LiteralText("Статус перевода:\n")
                .styled(style -> style.withColor(0x000000))
                .append(new LiteralText("УСПЕШНО")
                        .styled(style -> style.withColor(0x00FF00))));
        success0.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success0, 1, 2, 18, 2);

        WText success1 = new WText(new LiteralText("Получатель:\n")
                .styled(style -> style.withColor(0x000000))
                .append(new LiteralText(player.getName().asString())
                        .styled(style -> style.withColor(0x428C9D))));
        success1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success1, 1, 5, 18, 2);

        WText success2 = new WText(new LiteralText("Сумма:\n")
                .styled(style -> style.withColor(0x000000))
                .append(new LiteralText(amount + " " + Bankmod.CURRENCY)
                        .styled(style -> style.withColor(0x428C9D))));
        success2.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success2, 1, 8, 18, 2);

        WText success3 = new WText(new LiteralText(transUuid.replaceAll("-", ""))
                .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, transUuid.replaceAll("-", "")))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Нажмите чтобы скопировать индентификатор транзакции")))))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success3, 1, 11, 18, 2);

        WText success4 = new WText(new LiteralText("ссылка на онлайн чек").
                styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://spagetik.me/checktrans?code=" + transUuid.replaceAll("-", ""))).
                        withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Нажмите для перехода на страницу с онлайн чеком")))))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(success4, 1, 14, 18, 2);
    }
}
