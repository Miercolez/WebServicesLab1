package plugin;

import spi.Spi;
import spi.Url;
import utils.Request;

@Url("/")
public class Default implements Spi {


    @Override
    public byte[] handleRequest(Request request) {



        return new byte[0];
    }
}
