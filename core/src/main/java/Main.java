
import com.google.gson.Gson;
import functions.Functions;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
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
            var outputToClient = client.getOutputStream();

            switch (request.type) {
                //case HEAD -> handelHEAD();
                case GET -> handleGET(request, outputToClient);
                //case POST -> handlePOST();
            }

            inputFromClient.close();
            outputToClient.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleGET(Request request, OutputStream outputToClient) throws IOException {
        byte[] data = new byte[0];
        String header;
        System.out.println(request.url);
        if (request.url.equals("/getallmovies")) {
            List<entity.Movie> movies = Functions.getAllMovies();
            Gson gson = new Gson();
            String jsonStr = gson.toJson(movies);
            System.out.println(jsonStr);

            data = jsonStr.getBytes(StandardCharsets.UTF_8);
            header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length + "\r\n\r\n";

        } else {
            header = "HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n";
        }

        outputToClient.write(header.getBytes());
        outputToClient.write(data);
        outputToClient.flush();


    }

}

