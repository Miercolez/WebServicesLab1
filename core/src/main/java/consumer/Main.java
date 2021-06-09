package consumer;

import spi.Spi;
import spi.Url;
import utils.Request;
import utils.Response;
import utils.Utils;
import utils.UtilsResponse;

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
            System.out.println("hejsan4");
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

        Response response2 = new Response();
        response2.status = "404 Not found";

        for (Spi response : responses) {
            Url annotation = response.getClass().getAnnotation(Url.class);
            if (annotation != null && annotation.value().equals(request.url)) {
                response2 = response.handleRequest(request);
                System.out.println("Hejsan2");
            }
        }
        System.out.println("Hejsan3");
        return response2;
    }

    private static byte[] httpResponse404() {
        return "HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
    }

}

