package plugin;

import spi.Spi;
import spi.Url;
import utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@Url("/index.html")
public class Index implements Spi {

    @Override
    public Response handleRequest(Request request) throws IOException {
        Response response = new Response();

        if (request.type == HTTPType.POST) {
            return HttpStatus.status400();
        }

        response.type = request.type;

        File file = Path.of("/web/files", "index.html").toFile();

        response.status = HttpStatus.status200();


        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] body = new byte[(int) file.length()];
        fileInputStream.read(body);
        response.body = body;
        response.contentType = Utils.getContentType(file);


        return response;
    }
}
