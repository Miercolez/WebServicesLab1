package plugin;

import com.google.gson.Gson;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HTTPType;
import utils.HttpStatus;
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
        Response response = new Response();

        if(request.type == HTTPType.POST){
            return  HttpStatus.status400();
        }

        response.type = request.type;

        //Byte array for list of movies from DB.
        List<entity.Movie> movies = Functions.getAllMovies();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(movies);

        response.status = HttpStatus.status200();
        response.body = jsonStr.getBytes(StandardCharsets.UTF_8);

        return response;
    }


}
