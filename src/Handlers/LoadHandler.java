package Handlers;

import Requests.LoadRequest;
import Results.LoadResult;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStream reqBody = exchange.getRequestBody();
            String reqData = readString(reqBody);

            Gson gson = new Gson();

            LoadResult loadResult = new LoadService().service(gson.fromJson(reqData, LoadRequest.class));

            if (loadResult.isSuccess()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            OutputStream respBody = exchange.getResponseBody();
            writeString(gson.toJson(loadResult), respBody);
            respBody.close();
            exchange.close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }

    private String readString(InputStream is) throws IOException {
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
