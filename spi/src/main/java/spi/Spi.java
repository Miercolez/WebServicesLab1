package spi;

import utils.Request;
import utils.Response;

@Url
public interface Spi {

    Response handleRequest(Request request);


}
