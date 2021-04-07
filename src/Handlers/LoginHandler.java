package Handlers;

import Requests.LoginRequest;
import Results.LoginResult;
import Services.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            InputStream reqBody = exchange.getRequestBody();

            String reqData = readString(reqBody);
            Gson gson = new Gson();

            LoginResult loginResult = new LoginService().service(gson.fromJson(reqData, LoginRequest.class));

            if (loginResult.isSuccess()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            }
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }

            OutputStream respBody = exchange.getResponseBody();
            writeString(gson.toJson(loginResult), respBody);
            respBody.close();
            exchange.close();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
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
