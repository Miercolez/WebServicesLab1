package plugin;

import spi.Spi;
import spi.Url;
import utils.Request;
import utils.Response;

@Url("/")
public class Default implements Spi {

    @Override
    public Response handleRequest(Request request) {

        System.out.println("Hejsan");
        Response response = new Response();
        response.status = "200 OK";

        return response;
    }
}
