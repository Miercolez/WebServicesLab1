package plugin;

import spi.Spi;
import spi.Url;
import utils.*;


@Url(value ="/")
public class Default implements Spi {

    @Override
    public Response handleRequest(Request request) {
        return new Index().handleRequest(request);
    }
}
