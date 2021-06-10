package spi;

import utils.Request;
import utils.Response;

import java.io.IOException;

@Url
public interface Spi {

    Response handleRequest(Request request) throws IOException;


}
