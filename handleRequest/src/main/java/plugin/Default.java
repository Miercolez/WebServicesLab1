package plugin;

import com.google.gson.Gson;
import spi.Spi;
import spi.Url;
import utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Url(value ="/")
public class Default implements Spi {

    @Override
    public Response handleRequest(Request request) {

        Response response = new Response();

        if(request.type == HTTPType.POST){
            return  HttpStatus.status400();
        }

        response.type = request.type;

        File file = Path.of("/web/files", "movie.html").toFile();

        response.status = HttpStatus.status200();

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] body = new byte[(int) file.length()];
            fileInputStream.read(body);
            response.body = body;
            response.contentType = Utils.getContentType(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
