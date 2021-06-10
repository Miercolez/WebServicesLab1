package plugin;

import spi.Spi;
import spi.Url;
import utils.*;

import java.io.IOException;


@Url(value = "/")
public class Default implements Spi {

    @Override
    public Response handleRequest(Request request) throws IOException {
        return new Index().handleRequest(request);
    }
}
