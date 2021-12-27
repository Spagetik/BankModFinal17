package org.spagetik.bankmod.gui.phone;

import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.text.LiteralText;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.gui.GuiScreen;

import java.util.HashMap;


public class PhoneWelcomeGui extends AbstractPhoneGui {

    public PhoneWelcomeGui() {
        super(null);

        setPhoneName();
        setBorders();
        setPhoneSize();

        WText text2 = new WText(new LiteralText("Добро пожаловать в интернет банк, для авторизации заполните поля логина и пароля ниже"));
        text2.setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(text2, 1, 4, 18, 4);

        WLabel loginLabel = new WLabel(new LiteralText("Логин")
                .styled(style -> style.withBold(true)))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(loginLabel, 1, 9, 18, 1);

        WTextField loginField = new WTextField(new LiteralText("login"));
        root.add(loginField, 2, 10, 16, 1);

        WLabel passLabel = new WLabel(new LiteralText("Пароль")
                .styled(style -> style.withBold(true)))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        root.add(passLabel, 1, 13, 18, 1);

        WTextField passField = new WTextField(new LiteralText("password"));
        root.add(passField, 2, 14, 16, 1);

        WButton logInButton = new WButton()
                .setLabel(new LiteralText("ВХОД"))
                .setOnClick(() -> {
                    String password = passField.getText();
                    String login = loginField.getText();
                    HashMap<String, Object> data = BankApi.getAccountDataByLoginAndPassword(login, password);
                    if (!(Boolean) data.get("success")) {
                        GuiScreen.openErrorAtmGui((String) data.get("error"));
                    }
                    else {
                        String ISPB = (String) data.get("ISPB");
                        GuiScreen.setScreen(new PhoneActionGui(login, password, ISPB));
                    }
        });
        root.add(logInButton, 2, 18, 16, 1);

    }
}
