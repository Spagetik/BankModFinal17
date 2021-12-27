package org.spagetik.bankmod.gui.atm;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;

public abstract class AbstractAtmGui extends LightweightGuiDescription {
    public WGridPanel root = new WGridPanel()
            .setInsets(Insets.ROOT_PANEL);
    private final WLabel bankName = new WLabel(new LiteralText("                              "+Bankmod.BANK_NAME+"                              ")
            .styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/kmGBjgz3Gq")))
            .styled(style -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Ссылка на дискорд")))))
            .setHorizontalAlignment(HorizontalAlignment.CENTER);
    private final WButton exitButton = new WButton()
            .setLabel(new LiteralText("ВЫЙТИ"))
            .setOnClick(GuiScreen::closeAtmGui);
    private final WButton backButton = new WButton()
            .setLabel(new LiteralText("НАЗАД"));
    public GuiDescription parent;

    public AbstractAtmGui(GuiDescription parentDesc) {
        parent = parentDesc;
        setRootPanel(root);
        root.validate(this);
    }

    public void setBankName() {
        root.add(bankName, 0, 0, 15, 1);
    }

    public void setSize(int x, int y) {
        root.setSize(x, y);
    }

    public void setExitButton(int x, int y, int wight, int height) {
        root.add(exitButton, x, y, wight, height);
    }

    public void setBackButton(int x, int y, int wight, int height) {
        backButton.setOnClick(() -> {
            GuiScreen.playAtmClickSound();
            GuiScreen.setScreen(parent);
        });
        root.add(backButton, x, y, wight, height);
    }
}
