package spi;

import utils.Request;
import utils.Response;

import java.io.OutputStream;

@Url
public interface Spi {

    Response handleRequest(Request request);


}
