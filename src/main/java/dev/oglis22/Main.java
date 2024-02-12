package dev.oglis22;

import dev.oglis22.logs.LogType;
import dev.oglis22.logs.Logger;
import dev.oglis22.server.ServerHandler;
import org.eclipse.jetty.util.log.Log;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Logger.log("Artermis is starting", "SYSTEM", LogType.INFORMATION);
        new ServerHandler().init();

    }
}