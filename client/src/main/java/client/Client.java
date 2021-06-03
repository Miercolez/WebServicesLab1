package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {




    public static void getAllMovies(){
        try {
            Socket socket = new Socket("localhost", 80);

            var output = new PrintWriter(socket.getOutputStream());
            output.print("GET /getallmovies HTTP/1.1\r\n");
            output.print("Host: localhost\r\n");
            output.print("\r\n");
            output.flush();

            var inputFromServer = new BufferedReader(new InputStreamReader((socket.getInputStream())));

            boolean header = true;
            while (true) {
                var line = inputFromServer.readLine();

                if (line == null || line.isEmpty()) {
                    if (header) {
                        header = false;
                    } else {
                        break;
                    }
                }

                System.out.println(line);
            }

            inputFromServer.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

