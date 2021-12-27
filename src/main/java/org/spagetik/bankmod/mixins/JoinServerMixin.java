package org.spagetik.bankmod.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.login.LoginDisconnectS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginSuccessS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spagetik.bankmod.BankApi;
import org.spagetik.bankmod.Bankmod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLoginNetworkHandler.class)
public abstract class JoinServerMixin {

    @Shadow @Final private ClientConnection connection;

    @Inject(method = "onLoginSuccess", at=@At("HEAD"))
    private void onConnect(LoginSuccessS2CPacket packet, CallbackInfo ci) {
        Bankmod.CLIENT_UUID = packet.getProfile().getId().toString();
        Bankmod.ATM_POSES = null;
        Bankmod.ON_SPK = false;
        if (connection.getAddress().toString().contains("spk.spworlds.ru")) {
            Bankmod.ATM_POSES = BankApi.getAtmPoses();
            if (Bankmod.ATM_POSES.size() > 0) {
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText("Синхронизация с " +
                        "банковской системой прошла успешно"));
                Bankmod.ON_SPK = true;
            }
            else {
                MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(new LiteralText("Ошибка " +
                        "синхронизации с банковской системой, вы не зарегестрированы или используете неправильные ключи"));
            }
        }
    }
}


