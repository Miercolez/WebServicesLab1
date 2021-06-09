package utils;

public class Response {
    public final String httpVersion = "HTTP/1.1 ";
    public String status;
    public String contentType;
    public Byte[] body;
    public final String beforeContentLength = "Content-length: ";
    public String contentLength(){
        try {
            return String.valueOf(body.length);
        }catch (NullPointerException n){
            return "0";
        }
    }

}