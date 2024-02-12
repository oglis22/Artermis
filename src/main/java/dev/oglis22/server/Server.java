package dev.oglis22.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

import dev.oglis22.logs.LogType;
import dev.oglis22.logs.Logger;

public class Server implements Runnable {

    private String name;
    private int port;
    private String viewdir;
    private String viewfile;

    public Server(String name, int port, String viewdir, String viewfile) {
        this.name = name;
        this.port = port;
        this.viewdir = viewdir;
        this.viewfile = viewfile;
    }

    @Override
    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            Logger.log("Server succesfully created", this.name, LogType.SUCCES);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            try {
                Logger.log("Server cannot be created", this.name, LogType.ERROR);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }


    }

    private void handleClient(Socket clientSocket) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String request = reader.readLine();
            if (request.startsWith("GET")) {
                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream);

                // Serve a simple HTML page
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/html");
                writer.println();
                // writer.println("<html><body><h1>Hello, World!</h1></body></html>");
                getFileValue(writer::println);
                writer.flush();

                writer.close();
                outputStream.close();
            }
            clientSocket.close();
        } catch (IOException e) {
            Logger.log("Buffer error", this.name, LogType.ERROR);
            e.printStackTrace();
        }
    }

    public void getFileValue(Consumer<String> consumer) throws IOException {

        File file = new File(viewdir + "/" + viewfile);
        BufferedReader br = new BufferedReader(new FileReader(viewdir + "/" + viewfile));
        String line;
        while ((line = br.readLine()) != null) {
            consumer.accept(line);
        }


    }


}
