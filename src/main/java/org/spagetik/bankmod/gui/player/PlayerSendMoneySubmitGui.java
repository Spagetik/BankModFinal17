package org.spagetik.bankmod.gui.player;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.gui.GuiScreen;
import org.spagetik.bankmod.gui.phone.PhoneSuccessGui;

import javax.swing.text.SimpleAttributeSet;
import java.util.HashMap;

public class PlayerSendMoneySubmitGui extends LightweightGuiDescription {

    public PlayerSendMoneySubmitGui(PlayerEntity player, String amount) {
        WGridPanel root = new WGridPanel(18);
        setRootPanel(root);
        root.setSize(180, 200);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel label = new WLabel(new LiteralText("Перевод игроку")
                .styled(style -> style.withBold(true)));
        label.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(label, 0, 0, 10, 1);

        WLabel nickLabel = new WLabel(new LiteralText("Ник получателя"));
        nickLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        nickLabel.setVerticalAlignment(VerticalAlignment.BOTTOM);
        root.add(nickLabel, 0, 1, 10, 1);

        WTextField toPlayerNick = new WTextField();
        toPlayerNick.setMaxLength(16);
        toPlayerNick.setText(player.getName().asString());
        toPlayerNick.setEditable(false);
        root.add(toPlayerNick, 1, 2, 8, 1);

        WLabel sumLabel = new WLabel(new LiteralText("Сумма"));
        sumLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        sumLabel.setVerticalAlignment(VerticalAlignment.BOTTOM);
        root.add(sumLabel, 0, 3, 10, 1);

        WTextField toSum = new WTextField();
        toSum.setText(amount);
        toSum.setEditable(false);
        root.add(toSum, 1, 4, 8, 1);

        WButton continueButton = new WButton(new LiteralText("Подтвердить"));
        continueButton.setOnClick(() -> {
            HashMap<String, Object> data = BankApi.sendFromPlayerToPlayer(player, amount);
            if (!(Boolean) data.get("success")) {
                GuiScreen.openErrorAtmGui((String) data.get("error"));
            }
            else {
                GuiScreen.setScreen(new PlayerSuccesGui(player, amount, String.valueOf(data.get("uuid"))));
            }
        });
        root.add(continueButton, 1, 6, 8, 1);

        WButton backButton = new WButton(new LiteralText("НАЗАД"));
        backButton.setOnClick(() -> {
            GuiScreen.setScreen(new PlayerSendMoneyGui(player));
        });
        root.add(continueButton, 1, 6, 8, 1);

        root.validate(this);
    }
}
