package utils;

import java.util.HashMap;
import java.util.Map;

public class Request {
    public HTTPType type;
    public String url;
    public Map<String, String> urlParams = new HashMap<>();
    public int contentLength;
    public String body;
}

