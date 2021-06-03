package plugin;

import spi.Spi;
import spi.Url;
import utils.Request;

@Url("/url")
public class HandelUrl implements Spi {


    @Override
    public String handleRequest(Request request) {
        return "url!!";
    }
}
