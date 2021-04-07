package Handlers;

import Results.FillResult;
import Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class FillHandler  implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String[] parsedURL = String.valueOf(exchange.getRequestURI()).split("/");
            String username = parsedURL[2];
            int generations;
            if (parsedURL[3] == null)
            {
                generations = 4;
            }
            else {
                generations = Integer.parseInt(parsedURL[3]);
            }
            FillService fillService = new FillService();

            FillResult fillResult = fillService.service(username, generations);

            if (fillResult.isSuccess()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            Gson gson = new Gson();
            OutputStream respBody = exchange.getResponseBody();
            writeString(gson.toJson(fillResult), respBody);
            respBody.close();
            exchange.close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }

    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
