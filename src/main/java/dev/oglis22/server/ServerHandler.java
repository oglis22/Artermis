package dev.oglis22.server;

import dev.oglis22.config.ConfigHandler;
import dev.oglis22.logs.LogType;
import dev.oglis22.logs.Logger;
import dev.oglis22.server.Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ServerHandler {

    public void init() throws IOException {

        Logger.log("Artermis started", "SYSTEM", LogType.SUCCES);

        ConfigHandler configHandler = new ConfigHandler();
        configHandler.init();
        configHandler.getAllServers(serverObject -> {

            String name = serverObject.get("name").getAsString();
            int port = serverObject.get("port").getAsInt();
            String viewDir = serverObject.get("viewdir").getAsString();
            String viewfile = serverObject.get("viewfile").getAsString();


            try {
                Logger.log("Server starting", name, LogType.INFORMATION);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Server server = new Server(name, port, viewDir, viewfile);
            Thread serverThread = new Thread(server);
            serverThread.start();

        });



    }

}
