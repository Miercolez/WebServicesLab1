package plugin;

import com.google.gson.Gson;
import entity.Movie;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HTTPType;
import utils.HttpStatus;
import utils.Request;
import utils.Response;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.addTwoByteArrays;

@Url("/movies")
public class FindMovies implements Spi {

    @Override
    public Response handleRequest(Request request) {

        String key = getKey(request);
        System.out.println("hejsan1");
        List<Movie> movies = switch (key) {
            case "id" -> new ArrayList<>(List.of(Functions.findMovieById(Long.valueOf(request.urlParams.get("id")))));
            case "director" -> Functions.findMoviesByDirector(request.urlParams.get("director"));
            case "title" -> Functions.findMoviesByTitle(request.urlParams.get("title"));
            case "length" -> Functions.findMoviesByLength(Integer.parseInt(request.urlParams.get("length")));
            case "releaseYear" -> Functions.findMoviesByYear(Integer.parseInt(request.urlParams.get("releaseYear")));
            case "" -> Functions.getAllMovies();
            default -> new ArrayList<>();
        };

        System.out.println("hejsan2");
        Gson gson = new Gson();
        String jsonStr = "";

        if (!movies.isEmpty()) {
            jsonStr = gson.toJson(movies);
        } else {
            jsonStr = "Could not find movie\r\n";
        }

        //Byte Array for header
        Response response = new Response();
        response.status = HttpStatus.status200();
        response.body = jsonStr.getBytes(StandardCharsets.UTF_8);
        response.contentType = "application/json";
        response.contentLength();


//        if (request.type.equals(HTTPType.HEAD)) {
////            return headerByte;
//        } else if (request.type.equals(HTTPType.GET)) {
//            System.out.println(headerAndData);
////            return headerAndData;
//        } else{
//
//        }
//            return "HTTP/1.1 400 Bad Request\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);return new Response();
        return response;
    }

    private String getKey(Request request) {
        if (request.urlParams.containsKey("id")) return "id";
        else if (request.urlParams.containsKey("director")) return "director";
        else if (request.urlParams.containsKey("length")) return "length";
        else if (request.urlParams.containsKey("releaseYear")) return "releaseYear";
        else if (request.urlParams.containsKey("title")) return "title";
        else return "";
    }
}
