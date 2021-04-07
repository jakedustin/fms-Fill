package Handlers;

import DAOs.DataAccessException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        boolean fileOK = true;
        File requestFile = null;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {

                String urlPath = exchange.getRequestURI().toString();
                if (urlPath.length() <= 1) {
                    urlPath = "/index.html";
                }

                String filePath = "web" + urlPath;

                requestFile = new File(filePath);
                if (!(requestFile.exists())) {
                    fileOK = false;
                    requestFile = new File("web/HTML/404.html");
                }
                else {
                    OutputStream respBody = exchange.getResponseBody();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    Files.copy(requestFile.toPath(), respBody);
                    respBody.close();
                    success = true;
                }
            }

            if (!success) {
                if (!fileOK) {
                    OutputStream respBody = exchange.getResponseBody();
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    Files.copy(requestFile.toPath(), respBody);
                    exchange.getResponseBody().close();
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
