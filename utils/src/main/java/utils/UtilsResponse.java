package utils;

import java.nio.charset.StandardCharsets;

public class UtilsResponse {

    public Byte[] parseHTTPResponse(Response response){
        parseStatus(response.status);

        StringBuilder returnObject = new StringBuilder();
        returnObject.append(response.httpVersion)
                .append(response.status);
        if(!response.contentType.isEmpty() || response.contentType != null)
            returnObject.append("\r\nContent-Type: ").append(response.contentType);

        returnObject.append("\r\nContent-length: ").append(response.contentLength());

        byte[] returnByteObject = returnObject.toString().getBytes(StandardCharsets.UTF_8);

        if(response.body != null)
        returnByteObject
    }

    public String parseStatus(String status){
        switch (status){

        }
    }

    public Response error404(Response response){
        response.status = "404 Not Found\r\n";
        response.contentLength();
        return response;
    }


    public
}
