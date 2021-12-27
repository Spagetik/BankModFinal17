package org.spagetik.bankmod;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ModConfig {
    private final static String CONFIG_FILE_NAME = Bankmod.MOD_ID + ".yaml";
    private static File file;
    @JsonProperty
    public String clientKey = "none";
    @JsonProperty
    public String responseKey = "none";

    public ModConfig() {
    }

    public ModConfig registerConfigs() {
        setup();
        return readConfig(0);
    }

    public void writeConfigs() {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            mapper.writeValue(file, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ModConfig readConfig(int i) {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        if (i > 5) {
            return null;
        }
        try {
            return mapper.readValue(file, ModConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            writeConfigs();
            return readConfig(i+1);
        }
    }

    private static void setup() {
        Path path = FabricLoader.getInstance().getConfigDir();
        File fileDir = new File(String.valueOf(path.resolve("bankmod")));
        if (fileDir.exists()) {
            System.out.println("Config dir exists");
        }
        else {
            if (fileDir.mkdir()) {
                System.out.println("Config dir was created");
            }
            else {
                System.out.println("Config dir error");
            }
        }
        file = new File(String.valueOf((path.resolve("bankmod").resolve(CONFIG_FILE_NAME))));
        if (file.exists()) {
            System.out.println("Config file exists");
        }
        else {
            try {
                if (file.createNewFile()) {
                    System.out.println("Config file created");
                }
                else {
                    System.out.println("Config file error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
