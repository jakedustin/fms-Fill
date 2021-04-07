package Handlers;

import Results.AuthorizationResult;
import Results.EventResult;
import Results.PersonResult;
import Services.AuthorizationService;
import Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
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
                PersonResult personResult = new PersonResult("Error: Internal server error");
                if (parsedURL.length < 3) {
                    //do the thing where you pull all the people for the username
                    personResult = new PersonService().service(authorizationResult.getUsername());
                } else {
                    //do the thing where you pull one person
                    String personID = parsedURL[2];
                    personResult = new PersonService().service(authorizationResult.getUsername(), personID);
                }
                if (authorizationResult.isSuccess() && personResult.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(gson.toJson(personResult), respBody);
                    respBody.close();
                } else {
                    String responseString;
                    if (authorizationResult.isSuccess()) {
                        responseString = personResult.getMessage();
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
                writeString(gson.toJson(new PersonResult("Error: Bad request")), respBody);
                respBody.close();
            }
        }
        else {
            exchange.sendResponseHeaders (HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream respBody = exchange.getResponseBody();
            writeString(gson.toJson(new PersonResult("Error: Bad request")), respBody);
            respBody.close();
        }
        exchange.close();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
