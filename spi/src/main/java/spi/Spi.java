package spi;

import utils.Request;

import java.io.OutputStream;

@Url
public interface Spi {

    byte[] handleRequest(Request request);

}
