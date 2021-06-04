package plugin;

import spi.Spi;
import spi.Url;
import utils.Request;

@Url("/addmovie")
public class AddMovie implements Spi {


    @Override
    public byte[] handleRequest(Request request) {
        return new byte[0];
    }
}
