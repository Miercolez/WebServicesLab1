package utils;

import java.nio.charset.StandardCharsets;

public class UtilsResponse {

    public static byte[] parseHTTPResponse(Response response) {
        StringBuilder headerSB = new StringBuilder();

        headerSB.append(response.httpVersion)
                .append(response.status);
        if (response.contentType != null)
            headerSB.append("\r\nContent-Type: ")
                    .append(response.contentType);

        headerSB.append("\r\nContent-length: ")
                .append(response.contentLength())
                .append("\r\n\r\n");

        byte[] header = headerSB.toString().getBytes(StandardCharsets.UTF_8);

        if (response.body != null)
            return Utils.addTwoByteArrays(header, response.body);
        else
            return header;

    }


}
