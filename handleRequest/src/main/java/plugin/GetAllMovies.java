package plugin;

import com.google.gson.Gson;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HTTPType;
import utils.Request;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Url("/getallmovies")
public class GetAllMovies implements Spi {

    @Override
    public byte[] handleRequest(Request request) {
        List<entity.Movie> movies = Functions.getAllMovies();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(movies);

        byte[] data = jsonStr.getBytes(StandardCharsets.UTF_8);
        String header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length + "\r\n\r\n";
        byte[] headerByte = header.getBytes(StandardCharsets.UTF_8);
        byte[] ans = ByteBuffer.allocate(headerByte.length + data.length)
                .put(headerByte)
                .put(data)
                .array();
        if (request.type.equals(HTTPType.HEAD)) {
            return headerByte;
        } else if (request.type.equals(HTTPType.GET)) {
            return ans;
        } else
            return "HTTP/1.1 400 Bad Request\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
    }
}
