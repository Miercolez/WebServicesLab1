package utils;

import utils.HTTPType;
import utils.Request;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Request parseHttpRequest(String input) {

        var request = new Request();
        request.type = parseHttpRequestType(input);
        request.url = parseUrl(input);
        try {
            request.urlParams = parseUrlParams(input);
        } catch (Exception e) {
        }

        return request;
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
        str = str.replace('+', ' ');
        str = str.replace("%20", " ");
        str = str.replace("%C3%A5", "å");
        return str;
    }

    public String message() {
        return "Hello from utils.Utils";
    }
}
