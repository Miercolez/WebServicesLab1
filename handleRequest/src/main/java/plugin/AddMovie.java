package plugin;

import com.google.gson.Gson;
import entity.Movie;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HttpStatus;
import utils.Request;
import utils.Response;

import java.util.List;

@Url("/addmovie")
public class AddMovie implements Spi {


    @Override
    public Response handleRequest(Request request) {
        Gson gson = new Gson();
        Movie movie = gson.fromJson(request.body, Movie.class);
        System.out.println("The movie: " + movie);
        Functions.addMovie(movie);

        Response response = new Response();
        response.body = request.body.getBytes();
        response.status = HttpStatus.status200();
        return response;
    }
}
