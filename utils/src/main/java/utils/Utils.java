package utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Utils {

    public static Request parseHttpRequest(InputStream inputStream) throws IOException {
        var request = new Request();
        String head = readHead(inputStream);

        request.contentLength = parseContentLength(head);
        request.type = parseHttpRequestType(head);
        request.url = parseUrl(head);

        request.body = readBody(inputStream, request.contentLength);

        request.urlParams = parseUrlParams(head);

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


    private static Map<String, String> parseUrlParams(String input) {
        Map<String, String> ans = new HashMap<>();
        String[] words = input.split(" ");

        if (!words[1].contains("?")) return ans;

        String[] url = words[1].split("\\?");
        String[] params = url[1].split("&");

        for (String str : params) {
            String[] param = str.split("=");
            ans.put(replaceUrlEncoding(param[0]), replaceUrlEncoding(param[1]));
        }
        System.out.println("params fin");
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

    private static String replaceUrlEncoding(String encodedString) {

        return URLDecoder.decode(encodedString, StandardCharsets.UTF_8);
    }

    public static byte[] addTwoByteArrays(byte[] headerByte, byte[] data) {
        return ByteBuffer.allocate(headerByte.length + data.length)
                .put(headerByte)
                .put(data)
                .array();
    }

    public static String getContentType(File file) {
        String contentType = null;
        try {
            contentType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Objects.requireNonNullElse(contentType, "application/javascript");
    }
}
