package Handlers;

import Helpers.StringHelper;
import Results.FillResult;
import Results.RegisterResult;
import Services.FillService;
import Services.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

import Requests.RegisterRequest;

public class RegisterHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();
                InputStream reqBody = exchange.getRequestBody();

                String reqData = StringHelper.readString(reqBody);
                RegisterRequest registerRequest = gson.fromJson(reqData, RegisterRequest.class);

                RegisterService registerService = new RegisterService();
                RegisterResult registerResult = registerService.register(registerRequest);

                FillService fillService = new FillService();
                FillResult fillResult = fillService.service(registerRequest.getUsername(), 4);

                if (registerResult.isSuccess() && fillResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                OutputStream respBody = exchange.getResponseBody();
                String jsonOut = gson.toJson(registerResult);
                writeString(jsonOut, respBody);
                respBody.close();
                exchange.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
