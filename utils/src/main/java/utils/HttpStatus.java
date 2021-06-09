package utils;

import utils.HTTPType;
import utils.Response;

public class HttpStatus {
    public static String status200() {
        return "200 OK";
    }

    public static Response status404() {
        Response response = new Response();
        response.status = "404 Not found";
        response.type = HTTPType.GET;

        return response;
    }

    public static Response status400() {
        Response response = new Response();
        response.status = "400 Bad request";
        response.type = HTTPType.GET;

        return response;
    }

    public static Response status500() {
        Response response = new Response();
        response.status = "500 Internal Server Error";
        response.type = HTTPType.GET;

        return response;
    }
}
