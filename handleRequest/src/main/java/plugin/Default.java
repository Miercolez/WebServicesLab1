package plugin;

import spi.Spi;
import spi.Url;
import utils.Request;
import utils.Response;

@Url("/")
public class Default implements Spi {

    @Override
    public Response handleRequest(Request request) {



        return null;
    }
}
