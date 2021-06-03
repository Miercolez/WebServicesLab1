package consumer;

import spi.Spi;
import utils.Request;

import java.io.OutputStream;

public class HandleRequest implements Spi {
    @Override
    public String handleRequest(Request request) {
        if(request.url.equals("/getallmovies")){

        }

        return null;
    }
}
