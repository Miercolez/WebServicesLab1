import org.junit.jupiter.api.Test;
import utils.HTTPType;
import utils.Request;
import utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ALL")
public class UtilsTest {

    @Test
    void requestTypeGetWithRootUrl() throws IOException {
        String str = """
                GET / HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));

        Request request = Utils.parseHttpRequest(inputStream);

        assertThat(request.url).isEqualTo("/");
    }

    @Test
    void requestWithFilePath() throws IOException {
        String str = """
                GET /index.html HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        var request = Utils.parseHttpRequest(inputStream);
        assertThat(request.url).isEqualTo("/index.html");
        assertThat(request.type).isEqualTo(HTTPType.GET);
    }

    @Test
    void requestTypeHead() throws IOException {
        String str = """
                HEAD /index.html HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Request request = Utils.parseHttpRequest(inputStream);
        assertThat(request.type).isEqualTo(HTTPType.HEAD);
    }

    @Test
    void requestTypeGetWithOneUrlParameter() throws IOException {
        String str = """
                GET /products?id=23 HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Request request = Utils.parseHttpRequest(inputStream);
        assertThat(request.type).isEqualTo(HTTPType.GET);
        assertThat(request.url).isEqualTo("/products");
        assertThat(request.urlParams).containsEntry("id", "23");
    }

    @Test
    void requestTypePOSTWithTwoUrlParameters() throws IOException {
        String str = """
                POST /products?id=23&order=ascend HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Request request = Utils.parseHttpRequest(inputStream);
        assertThat(request.type).isEqualTo(HTTPType.POST);
        assertThat(request.url).isEqualTo("/products");
        assertThat(request.urlParams).containsEntry("id", "23").containsEntry("order", "ascend");
    }

    @Test
    void requestTypeGETWithUrlParameterIncludingSpace() throws IOException {
        String str = """
                GET /products?text=Hello+there HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Request request = Utils.parseHttpRequest(inputStream);
        assertThat(request.type).isEqualTo(HTTPType.GET);
        assertThat(request.url).isEqualTo("/products");
        assertThat(request.urlParams).containsEntry("text", "Hello there");
    }

    @Test
    void requestTypeGETWithUrlParameterIncludingSpaceUsingUrlEncoding() throws IOException {
        String str = """
                GET /products?t%20e%20x%20t=M%C3%A5ste%20fixa HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Request request = Utils.parseHttpRequest(inputStream);
        assertThat(request.type).isEqualTo(HTTPType.GET);
        assertThat(request.url).isEqualTo("/products");
        assertThat(request.urlParams).containsEntry("t e x t", "Måste fixa");
    }

    @Test
    void requestUrlWithSpaces() throws IOException {
        String str = """
                GET /a%20folder/first%20document.pdf HTTP/1.1\r\n \
                Host: www.example.com\r\n \
                \r\n \
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Request request = Utils.parseHttpRequest(inputStream);
        assertThat(request.type).isEqualTo(HTTPType.GET);
        assertThat(request.url).isEqualTo("/a folder/first document.pdf");
        assertThat(request.urlParams).isEmpty();
    }

    //    //This test needs new fields on our utils.Request object to compile.
//    //Also observe that the content in the body normaly can't be read as a string for two reasons.
//    //1. It's not always of type text/string. Might be binary data when uploading a image file.
//    //2. There is no lineending. Instead we must use the Content-Length: value
//    //   as a guide how many bytes we need to read from InputStream.
    @Test
    void requestPostWithJsonInBody() throws IOException {
        String str = """
                POST /upload HTTP/1.1\r\n \
                Host: localhost:5050\r\n \
                User-Agent: insomnia/2021.3.0\r\n \
                Cookie: JSESSIONID=490B71AEC6F7B91E31BBDB1037F53B26\r\n \
                Content-Type: application/json\r\n \
                Accept: */*\r\n \
                Content-Length: 25\r\n\r\n\
                {"name":"hej","title":12}\
                """;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Request request = Utils.parseHttpRequest(inputStream);
        assertThat(request.type).isEqualTo(HTTPType.POST);
        assertThat(request.url).isEqualTo("/upload");
        assertThat(request.urlParams).isEmpty();

//        assertThat(request.contentType).isEqualTo("application/json");
//        assertThat(request.contentLength).isEqualTo(25);
        assertThat(request.body).isEqualTo("{\"name\":\"hej\",\"title\":12}");
    }

    @Test
    void requestPostWithJsonInBody2() throws IOException {
        String body = "{\"name\":\"hej\",\"title\":12}";
        int contenLength = body.getBytes(StandardCharsets.UTF_8).length;
        String str = "POST /upload HTTP/1.1\r\nContent-Length: " + contenLength + "\r\n\r\n" + body;
        InputStream inputStream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
        Request request = Utils.parseHttpRequest(inputStream);
        assertThat(request.body).isEqualTo("{\"name\":\"hej\",\"title\":12}");
    }

}
