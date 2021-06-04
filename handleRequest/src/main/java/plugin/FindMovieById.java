package plugin;

import com.google.gson.Gson;
import entity.Movie;
import functions.Functions;
import spi.Spi;
import spi.Url;
import utils.HTTPType;
import utils.Request;

import java.nio.charset.StandardCharsets;

import static utils.Utils.addTwoByteArrays;

@Url("/findmoviebyid")
public class FindMovieById implements Spi {

    @Override
    public byte[] handleRequest(Request request) {

        long idValue = Long.parseLong(request.urlParams.get("id"));

        Movie movie = Functions.findMovieById(idValue);

        Gson gson = new Gson();
        String jsonStr = "";

        if (movie != null){
            jsonStr = gson.toJson(movie);
        }else{
            jsonStr = "Could not find movie with id=" + idValue + "\r\n";
        }

        byte[] data = jsonStr.getBytes(StandardCharsets.UTF_8);

        //Byte Array for header
        String header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length + "\r\n\r\n";
        byte[] headerByte = header.getBytes(StandardCharsets.UTF_8);


        byte[] headerAndData = addTwoByteArrays(headerByte, data);
        System.out.println(header);

        if (request.type.equals(HTTPType.HEAD)) {
            return headerByte;
        } else if (request.type.equals(HTTPType.GET)) {
            System.out.println(headerAndData);
            return headerAndData;
        } else
            return "HTTP/1.1 400 Bad Request\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
    }

}
