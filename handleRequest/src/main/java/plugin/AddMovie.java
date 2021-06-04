package plugin;

import com.google.gson.Gson;
import entity.Movie;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.Request;

@Url("/addmovie")
public class AddMovie implements Spi {


    @Override
    public byte[] handleRequest(Request request) {
        Gson gson = new Gson();
        Movie movie = gson.fromJson(request.body, Movie.class);
        System.out.println("testar" + movie);

//        String[] movieInfo = (request.body).split("\\+");
//        String movieTitle = movieInfo[0];
//        Functions.addMovie(movieTitle, );

        return new byte[0];
    }
}
