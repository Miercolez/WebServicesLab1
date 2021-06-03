package plugin;

import spi.Spi;
import spi.Url;
import utils.Request;

@Url("/url2")
public class handleUrl2 implements Spi {


    @Override
    public String handleRequest(Request request) {
        return "url2";
    }
}