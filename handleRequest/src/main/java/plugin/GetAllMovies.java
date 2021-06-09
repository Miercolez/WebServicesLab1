package plugin;

import com.google.gson.Gson;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HTTPType;
import utils.Request;
import utils.Response;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static utils.Utils.addTwoByteArrays;

@Url("/getallmovies")
public class GetAllMovies implements Spi {

    @Override
    public Response handleRequest(Request request) {
//        //Byte array for list of movies from DB.
//        List<entity.Movie> movies = Functions.getAllMovies();
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(movies);
//        byte[] data = jsonStr.getBytes(StandardCharsets.UTF_8);
//
//        //Byte Array for header
//        String header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length + "\r\n\r\n";
//        byte[] headerByte = header.getBytes(StandardCharsets.UTF_8);
//
//
//        byte[] headerAndData = addTwoByteArrays(headerByte, data);
//
//        if (request.type.equals(HTTPType.HEAD)) {
//            return headerByte;
//        } else if (request.type.equals(HTTPType.GET)) {
//            return headerAndData;
//        } else
//            return "HTTP/1.1 400 Bad Request\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
        return new Response();
    }


}
