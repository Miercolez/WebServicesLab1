package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Request parseHttpRequest(InputStream inputStream) throws IOException {
        var request = new Request();
        String head = readHead(inputStream);

        request.contentLength = parseContentLength(head);
        request.type = parseHttpRequestType(head);
        request.url = parseUrl(head);

        request.body = readBody(inputStream, request.contentLength);

        try {
            request.urlParams = parseUrlParams(head);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return request;
    }

    private static String readHead(InputStream inputStream) throws IOException {
        StringBuilder head = new StringBuilder();
        while (true) {
            String line = readLine(inputStream);
            head.append(line);
            if (line.isBlank()) {
                break;
            }
        }
        return head.toString();
    }

    private static String readBody(InputStream inputStream, int contentLength) throws IOException {
        byte[] byteBody = inputStream.readNBytes(contentLength);
        return new String(byteBody, StandardCharsets.UTF_8);
    }

    private static int parseContentLength(String head) {
        int contentLength = 0;
        for (String str : head.split("\\r\\n")) {
            if (str.contains("Content-Length:")) {
                str = str.trim();
                String value = str.split(" ")[1];
                contentLength = Integer.parseInt(value.trim());
            }
        }
        return contentLength;
    }

    private static String readLine(InputStream inputFromClient) throws IOException {
        StringBuilder line = new StringBuilder();
        do {
            var b = inputFromClient.readNBytes(1);
            String str = new String(b, StandardCharsets.UTF_8);
            line.append(str);
        } while (!line.toString().contains("\r\n"));
        return line.toString();
    }


    private static Map<String, String> parseUrlParams(String input) throws Exception {

        String[] words = input.split(" ");

        if (!words[1].contains("?")) throw new Exception();

        String[] url = words[1].split("\\?");
        String[] params = url[1].split("\\&");

        Map<String, String> ans = new HashMap<>();
        for (String str : params) {
            str = replaceUrlEncoding(str);
            String[] param = str.split("=");
            ans.put(param[0], param[1]);
        }

        return ans;
    }

    private static String parseUrl(String input) {
        String[] words = input.split(" ");
        String[] urlAndParams = words[1].split("\\?");
        return replaceUrlEncoding(urlAndParams[0]);
    }

    private static HTTPType parseHttpRequestType(String input) {
        if (input.startsWith("G"))
            return HTTPType.GET;
        else if (input.startsWith("H"))
            return HTTPType.HEAD;
        else if (input.startsWith("PO"))
            return HTTPType.POST;

        throw new RuntimeException("Invalid type");
    }

    //Example of switch expressions to handle all possible enum cases without needing default case.
//    public static String handleRequest(Request request) {
//        return switch (request.type) {
//            case GET -> "GET";
//            case HEAD -> "HEAD";
//            case POST -> "POST";
//        };
//    }

    private static String replaceUrlEncoding(String encodedString) {
        try {
            return URLDecoder.decode(encodedString, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return encodedString;
        }
    }

    public static byte[] addTwoByteArrays(byte[] headerByte, byte[] data) {
        return ByteBuffer.allocate(headerByte.length + data.length)
                .put(headerByte)
                .put(data)
                .array();
    }
}
