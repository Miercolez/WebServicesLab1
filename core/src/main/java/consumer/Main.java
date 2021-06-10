package consumer;

import spi.Spi;
import spi.Url;
import utils.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(5051)) {
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

            var inputFromClient = client.getInputStream();

            Request request = Utils.parseHttpRequest(inputFromClient);

            System.out.println("Request url: " + request.url); //TODO <------ Ta bort

            var outputToClient = client.getOutputStream();
            outputToClient.write(UtilsResponse.parseHTTPResponse(getResponseData(request)));
            outputToClient.flush();

            inputFromClient.close();
            outputToClient.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response getResponseData(Request request) {
        ServiceLoader<Spi> responses = ServiceLoader.load(Spi.class);

        Response response = HttpStatus.status404();

        for (Spi resp : responses) {
            Url annotation = resp.getClass().getAnnotation(Url.class);
            if (annotation != null && annotation.value().equals(request.url)) {
                response = resp.handleRequest(request);
            }
        }
        return response;
    }


}

