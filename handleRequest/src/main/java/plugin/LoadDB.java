package plugin;

import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HttpStatus;
import utils.Request;
import utils.Response;

@Url("/load_db")
public class LoadDB implements Spi {
    @Override
    public Response handleRequest(Request request) {
        Response response = new Response();
        Functions.addMoviePackToDatabase();
        response.status = HttpStatus.status200();
        return response;
    }
}
