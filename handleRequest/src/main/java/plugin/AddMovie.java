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

@Url("/movie")
public class AddMovie implements Spi {

    @Override
    public Response handleRequest(Request request) {

        Response response = new Response();

        if(request.type != HTTPType.POST){
            return  HttpStatus.status400();
        }

        response.type = request.type;

        Gson gson = new Gson();
        Movie movie = gson.fromJson(request.body, Movie.class);
        System.out.println("The movie: " + movie);
        Functions.addMovie(movie);

        response.status = HttpStatus.status200();
        response.body = request.body.getBytes(StandardCharsets.UTF_8);

        return response;
    }
}
