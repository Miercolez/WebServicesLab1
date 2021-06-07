package utils;

import utils.HTTPType;
import utils.Request;

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
        }

        return request;
    }

    private static String readHead(InputStream inputStream) throws IOException {
        String head = "";
        while (true) {
            String line = readLine(inputStream);
            head += line;
            if (line.isBlank()) {
                break;
            }
        }
        return head;
    }

    private static String readBody(InputStream inputStream, int contentLength) throws IOException {
        byte[] byteBody = inputStream.readNBytes(contentLength);
        String body = new String(byteBody, StandardCharsets.UTF_8);
        return body;
    }

    private static int parseContentLength(String head) {
        int contentLength = 0;
        for (String str : head.split("\\r\\n")) {
            if (str.contains("Content-Length:")) {
                String value = str.split(" ")[1];
                contentLength = Integer.parseInt(value.trim());
            }
        }
        return contentLength;
    }

    private static String readLine(InputStream inputFromClient) throws IOException {
        String line = "";
        while (true) {
            var b = inputFromClient.readNBytes(1);
            String str = new String(b, StandardCharsets.UTF_8);
            line += str;
            if (line.contains("\r\n")) {
                break;
            }
        }
        return line;
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
//        int firstSpace = input.indexOf(' ') + 1;
//        int secondSpace = input.indexOf(' ', firstSpace);
//        return input.substring(firstSpace, secondSpace);
        String[] words = input.split(" ");
        String[] urlAndParams = words[1].split("\\?");
        String url = replaceUrlEncoding(urlAndParams[0]);
        return url;
    }


    //TODO Används ej ta bort
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
    public static String handleRequest(Request request) {
        return switch (request.type) {
            case GET -> "GET";
            case HEAD -> "HEAD";
            case POST -> "POST";
        };
    }

    private static String replaceUrlEncoding(String str) {
    /*encoded
        try {
            String decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8);
            return decoded;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return encoded;
   */
        str = str.replace('+', ' ');
        str = str.replace("%20", " ");
        str = str.replace("%C3%A5", "å");
        return str;

    }

    public static byte[] addTwoByteArrays(byte[] headerByte, byte[] data) {
        byte[] twoByteArraysCombined = ByteBuffer.allocate(headerByte.length + data.length)
                .put(headerByte)
                .put(data)
                .array();
        return twoByteArraysCombined;
    }
}
