package Handlers;

import Results.ClearResult;
import Services.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            ClearResult clearResult = new ClearService().service();

            String jsonOut = new Gson().toJson(clearResult);
            OutputStream respBody = exchange.getResponseBody();

            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            writeString(jsonOut, respBody);
            respBody.close();
            exchange.close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            exchange.close();

            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
