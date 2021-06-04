package consumer;

import spi.Spi;
import spi.Url;
import utils.Request;
import utils.Utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(80)) {
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Connection from : " + client.getInetAddress());
                executorService.submit(() -> handleConnection(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleConnection(Socket client) {
        try {
            var inputFromClient = new BufferedReader(new InputStreamReader((client.getInputStream())));
            Request request = Utils.parseHttpRequest(inputFromClient.readLine());

            System.out.println("Request url: " + request.url); //TODO <------ Ta bort

            var outputToClient = client.getOutputStream();
            outputToClient.write(getResponsData(request));
            outputToClient.flush();

            inputFromClient.close();
            outputToClient.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getResponsData(Request request) {
        ServiceLoader<Spi> responses = ServiceLoader.load(Spi.class);

        byte[] responsData = httpResponse404();
        for (Spi respons : responses) {
            Url annotaion = respons.getClass().getAnnotation(Url.class);
            if (annotaion != null && annotaion.value().equals(request.url)) {
                responsData = respons.handleRequest(request);
            }
        }
        return responsData;
    }

    private static byte[] httpResponse404() {
        return "HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
    }

}

