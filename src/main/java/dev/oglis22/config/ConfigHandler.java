package dev.oglis22.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.oglis22.logs.LogType;
import dev.oglis22.logs.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ConfigHandler {

    private String filepath = "config.json";
    private File file = new File(filepath);

    private JsonObject jsonObject;
    private JsonObject serversObject;

    public ConfigHandler() throws IOException {

        if (!file.exists()) {
            Logger.log("Can not find config file", "SYSTEM", LogType.WARNING);
            Logger.log("Succesfully created a new Config", "SYSTEM", LogType.SUCCES);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(filepath);
            fileWriter.write("{\n" +
                    "  \"servers\": {\n" +
                    "    \n" +
                    "  }\n" +
                    "}");
            fileWriter.close();
        }

    }

    public void init() throws IOException {

        Logger.log("Reading config file", "SYSTEM", LogType.INFORMATION);

        Gson gson = new Gson();

        try (Reader reader = new FileReader(filepath)) {
            // JSON-Datei deserialisieren
            jsonObject = gson.fromJson(reader, JsonObject.class);
            serversObject = jsonObject.getAsJsonObject("servers");

        } catch (Exception e) {
            Logger.log("Can not read config file", "SYSTEM", LogType.ERROR);
            e.printStackTrace();
        }
    }



    public void getAllServers(Consumer<JsonObject> serverConsumer) {

        for (String serverName : serversObject.keySet()) {
            JsonObject serverObject = serversObject.getAsJsonObject(serverName);

            serverConsumer.accept(serverObject);
        }
    }



}
