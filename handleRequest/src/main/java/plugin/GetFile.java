package plugin;

import spi.Spi;
import spi.Url;
import utils.HTTPType;
import utils.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static utils.Utils.addTwoByteArrays;

@Url("/getfile")
public class GetFile implements Spi {
    @Override
    public byte[] handleRequest(Request request) {

        byte[] data = new byte[0];
        String header = "";
        File file;
        if (request.urlParams.containsKey("image")) {
            String image = request.urlParams.get("image");
            file = Path.of("/web/images", image).toFile();
        } else if (request.urlParams.containsKey("file")) {
            String requestedFile = request.urlParams.get("file");
            file = Path.of("/web/files", requestedFile).toFile();
        } else {
            return "HTTP/1.1 400 Bad Request\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
        }

        if (!(file.exists() && !file.isDirectory())) {

            header = "HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n";
        } else {

            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                data = new byte[(int) file.length()];
                int dataLength = fileInputStream.read(data);
                String contentType = Files.probeContentType(file.toPath());
                header = "HTTP/1.1 200 OK\r\nContent-Type: " + contentType + "\r\nContent-length: " + dataLength + "\r\n\r\n";

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[] headerByte = header.getBytes(StandardCharsets.UTF_8);
        byte[] headerAndData = addTwoByteArrays(headerByte, data);
        if (request.type.equals(HTTPType.HEAD)) {
            return headerByte;
        } else if (request.type.equals(HTTPType.GET)) {
            return headerAndData;
        } else
            return "HTTP/1.1 400 Bad Request\r\nContent-length: 0\r\n\r\n".getBytes(StandardCharsets.UTF_8);
    }
}
