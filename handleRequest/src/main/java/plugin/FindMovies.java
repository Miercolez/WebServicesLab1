package plugin;

import com.google.gson.Gson;
import entity.Movie;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HTTPType;
import utils.Request;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.addTwoByteArrays;

@Url("/findmovie")
public class FindMovies implements Spi {

    @Override
    public byte[] handleRequest(Request request) {

        String key = getKey(request);
        List<Movie> movies = new ArrayList<>();

        switch (key) {
            case "id":
                movies = (List<Movie>) Functions.findMovieById(Long.valueOf(request.urlParams.get("id")));
                break;
            case "director":
                movies = Functions.findMoviesByDirector(request.urlParams.get("director"));
                break;
        }


        Gson gson = new Gson();
        String jsonStr = "";

        if (!movies.isEmpty()) {
            jsonStr = gson.toJson(movies);
        } else {
            jsonStr = "Could not find movie\r\n";
        }

        byte[] data = jsonStr.getBytes(StandardCharsets.UTF_8);

        //Byte Array for header
        String header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length + "\r\n\r\n";
        byte[] headerByte = header.getBytes(StandardCharsets.UTF_8);


        byte[] headerAndData = addTwoByteArrays(headerByte, data);
        System.out.println(header);

        if (request.type.equals(HTTPType.HEAD)) {
            return headerByte;
        } else if (request.type.equals(HTTPType.GET)) {
            System.out.println(headerAndData);
            return headerAndData;
        } else
            return "HTTP/1.1 400 Bad Request\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
    }

    private String getKey(Request request) {
        if (request.urlParams.containsKey("id")) return "id";
        else if (request.urlParams.containsKey("director")) return "director";
        else if (request.urlParams.containsKey("length")) return "length";
        else if (request.urlParams.containsKey("releaseYear")) return "releaseYear";
        else return "title";
    }
}
