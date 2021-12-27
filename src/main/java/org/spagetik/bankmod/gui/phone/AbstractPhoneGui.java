package org.spagetik.bankmod.gui.phone;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spagetik.bankmod.Bankmod;
import org.spagetik.bankmod.gui.GuiScreen;

public abstract class AbstractPhoneGui extends LightweightGuiDescription {
    public WGridPanel root = new WGridPanel(9)
            .setInsets(Insets.ROOT_PANEL);
    public GuiDescription parent;
    public WButton backButton = new WButton(new LiteralText("НАЗАД"))
            .setOnClick(() -> GuiScreen.setScreen(parent));

    public AbstractPhoneGui(GuiDescription parentDesc) {
        parent = parentDesc;
        setRootPanel(root);
        root.validate(this);
    }

    public void setSize(int x, int y) {
        root.setSize(x, y);
    }

    public void setBackButton(int x, int y, int width, int height) {
        root.add(backButton, x, y, width, height);
    }

    public void setName(String name) {
        WLabel nameLabel = new WLabel(name);
        nameLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(nameLabel, 0, 0, 20, 1);
    }

    public void setName(Text text) {
        WLabel nameLabel = new WLabel(text);
        nameLabel.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(nameLabel, 0, 0, 20, 1);
    }

    public void setBorders() {
        WText text = new WText(new LiteralText("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(text, 0, 0);

        WText text1 = new WText(new LiteralText("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||"));
        text.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(text1, 19, 0);
    }

    public void setPhoneName() {
        setName(new LiteralText(Bankmod.PHONE_NAME)
                .styled(style -> style.withBold(true)));
    }

    public void setPhoneSize() {
        setSize(192, 280);
    }

    public void setParent(GuiDescription parent) {
        this.parent = parent;
    }
}
