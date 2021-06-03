package spi;

import utils.Request;

@Url
public interface Spi {

    String handleRequest(Request request);

}
