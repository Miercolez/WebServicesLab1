package plugin;

import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.Request;
import utils.Response;

import java.nio.charset.StandardCharsets;

@Url("/load_db")
public class LoadDB implements Spi {
    @Override
    public Response handleRequest(Request request) {
//
//        Functions.addMoviePackToDatabase();
//        String header = "HTTP/1.1 200 OK\r\n\r\n";
//        byte[] headerByte = header.getBytes(StandardCharsets.UTF_8);
//        return headerByte;

        return new Response();
    }
}
