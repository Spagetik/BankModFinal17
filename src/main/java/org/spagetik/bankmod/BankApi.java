package org.spagetik.bankmod;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.spagetik.bankmod.exeptions.WrongResponseKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BankApi {

    private static final String strUrl = Bankmod.API_URL;

    private static HashMap<String, Object> sendPostRequest(@NotNull HashMap<String, String> data, String method) {
        data.put("clientUuid", Bankmod.CLIENT_UUID.replace("-", ""));
        data.put("clientKey", Bankmod.CLIENT_SECRET_KEY);
        data.put("method", method);
        HashMap<String, Object> returnData = new HashMap<>();
        HashMap<String, Object> out = new HashMap<>();
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String json = new JSONObject(data).toString();
            // System.out.println(json);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                String outJson = response.toString();
                out = new ObjectMapper().readValue(outJson, HashMap.class);
                returnData = checkResponseKey(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
            returnData.put("success", false);
            returnData.put("error", "Internal server error, sql, bankapi");
        } catch (WrongResponseKey e) {
//            e.printStackTrace();
            returnData.put("success", false);
            returnData.put("error", out.get("error"));
        }
        return returnData;
    }

    private static @NotNull HashMap<String, Object> checkResponseKey(@NotNull HashMap<String, Object> responseData) throws WrongResponseKey {
        String clientResponseKey = (String) responseData.get("clientResponseKey");
        if (!Bankmod.CLIENT_SECRET_RESPONSE_KEY.equals(clientResponseKey)) {
            throw new WrongResponseKey("Wrong response key");
        }
        responseData.remove("clientResponseKey");
        return responseData;
    }

    public static HashMap<String, Object> getCardAndAccountDataByHashAndPin(String cardHash, String cardPin) {
        HashMap<String, String> data = new HashMap<>();
        data.put("cardHash", cardHash);
        data.put("cardPin", cardPin);
        return sendPostRequest(data, "getCardAndAccountDataByHashAndPin");
    }

    public static HashMap<String, Object> getCardNumByHash(String cardHash) {
        HashMap<String, String> data = new HashMap<>();
        data.put("cardHash", cardHash);
        return sendPostRequest(data, "getCardNumByHash");
    }

    public static HashMap<String, Object> getBalanceByHash(String cardHash) {
        HashMap<String, String> data = new HashMap<>();
        data.put("cardHash", cardHash);
        return sendPostRequest(data, "getBalanceByHash");
    }

    public static HashMap<String, Object> sendMoneyFromCardToISPB(String cardHash, String cardPin, String toISPB, String amount) {
        HashMap<String, String> data = new HashMap<>();
        data.put("cardHash", cardHash);
        data.put("cardPin", cardPin);
        data.put("toISPB", toISPB);
        data.put("amount", amount);
        return sendPostRequest(data,  "sendMoneyFromCardToISPB");
    }

    public static HashMap<String, Object> sendMoneyFromCardToCard(String cardHash, String cardPin, String toCardNum, String amount) {
        HashMap<String, String> data = new HashMap<>();
        data.put("cardHash", cardHash);
        data.put("cardPin", cardPin);
        data.put("toCardNum", toCardNum);
        data.put("amount", amount);
        return sendPostRequest(data, "sendMoneyFromCardToCard");
    }

    public static HashMap<String, Object> createAccount(String login, String password, String secret) {
        HashMap<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        data.put("secret", secret);
        return sendPostRequest(data, "createAccount");
    }

    public static HashMap<String, Object> connectCardToAccount(String cardPin, String cardHash) {
        HashMap<String, String> data = new HashMap<>();
        data.put("cardPin", cardPin);
        data.put("cardHash", cardHash);
        return sendPostRequest(data, "connectCardToAccount");
    }

    public static List<BlockPos> getAtmPoses() {
        HashMap<String, Object> data;
        List<BlockPos> returnData = new ArrayList<>();
        data = sendPostRequest(new HashMap<>(), "GetAtmPoses");
        if ((Boolean) data.get("success")) {
            List<HashMap<String, Object>> dataList = (List<HashMap<String, Object>>) data.get("data");
            for (HashMap<String, Object> posData : dataList) {
                returnData.add(new BlockPos((Integer) posData.get("x"), (Integer) posData.get("y"), (Integer) posData.get("z")));
            }
        }
        return returnData;
    }

    public static String getPlayerNameByUuid(String uuid) {
        HashMap<String, String> data = new HashMap<>();
        data.put("uuid", uuid);
        HashMap<String, Object> data_1 = sendPostRequest(data, "getPlayerNameByUuid");
        if ((Boolean) data_1.get("success")) {
            return (String) data_1.get("name");
        }
        else {
            return "Not found";
        }
    }

    public static String getAccountISPB() {
        HashMap<String, Object> data_1 = sendPostRequest(new HashMap<>(), "getAccountISPB");
        if ((Boolean) data_1.get("success")) {
            return (String) data_1.get("ISPB");
        }
        else {
            return "Not found";
        }
    }

    public static HashMap<String, Object> addMoneys(String nickname, int amount) {
        HashMap<String, String> data = new HashMap<>();
        data.put("nickname", nickname);
        data.put("amount", String.valueOf(amount));
        return sendPostRequest(data, "addMoneys");
    }

    public static HashMap<String, Object> removeMoneys(String nickname, int amount) {
        HashMap<String, String> data = new HashMap<>();
        data.put("nickname", nickname);
        data.put("amount", String.valueOf(amount));
        return sendPostRequest(data, "removeMoneys");
    }

    public static HashMap<String, Object> getAccountDataByLoginAndPassword(String login, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        return sendPostRequest(data, "getAccountDataByLoginAndPassword");
    }

    public static HashMap<String, Object> getBalanceByLoginAndPassword(String login, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        return sendPostRequest(data, "getBalanceByLoginAndPassword");
    }

    public static HashMap<String, Object> sendMoneyFromAccountToISPB(String login, String password, String toISPB, String amount) {
        HashMap<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        data.put("toISPB", toISPB);
        data.put("amount", amount);
        return sendPostRequest(data, "sendMoneyFromAccountToISPB");
    }

    public static HashMap<String, Object> sendMoneyFromAccountToCard(String login, String password, String toCard, String amount) {
        HashMap<String, String> data = new HashMap<>();
        data.put("login", login);
        data.put("password", password);
        data.put("toISPB", toCard);
        data.put("amount", amount);
        return sendPostRequest(data, "sendMoneyFromAccountToCard");
    }
}
