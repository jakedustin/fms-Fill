package Handlers;

import Results.AuthorizationResult;
import Results.EventResult;
import Services.AuthorizationService;
import Services.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
            Headers reqHeaders = exchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {
                String authtoken = reqHeaders.getFirst("Authorization");
                //check for authtoken in database
                    AuthorizationResult authorizationResult = new AuthorizationService().service(authtoken);
                    String[] parsedURL = String.valueOf(exchange.getRequestURI()).split("/");
                    EventResult eventResult;
                    if (parsedURL.length < 3) {
                        //do the thing where you pull all the people for the username
                        eventResult = new EventService().service(authorizationResult.getUsername());
                    }
                    else {
                        //do the thing where you pull one person
                        String eventID = parsedURL[2];
                        eventResult = new EventService().service(authorizationResult.getUsername(), eventID);
                    }
                    if (authorizationResult.isSuccess() && eventResult.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(gson.toJson(eventResult), respBody);
                        respBody.close();
                    }
                    else {
                        String responseString;
                        if (authorizationResult.isSuccess()) {
                            responseString = eventResult.getMessage();
                        }
                        else {
                            responseString = authorizationResult.getUsername();
                        }
                        exchange.sendResponseHeaders (HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(gson.toJson(new EventResult(responseString)), respBody);
                        respBody.close();
                    }
            }
            else {
                exchange.sendResponseHeaders (HttpURLConnection.HTTP_BAD_REQUEST, 0);
                OutputStream respBody = exchange.getResponseBody();
                writeString(gson.toJson(new EventResult("Error: Bad request")), respBody);
                respBody.close();
            }
        }
        else {
            exchange.sendResponseHeaders (HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream respBody = exchange.getResponseBody();
            writeString(gson.toJson(new EventResult("Error: Bad request")), respBody);
            respBody.close();
            exchange.close();
        }

    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


}
