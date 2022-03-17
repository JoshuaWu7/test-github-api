import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import server.ServerRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
//import okhttp3.*;


public class client {

    @Test
    public void smokeTestGET() {
        Gson gson = new Gson();
        //returns a new HttpClient with default settings
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/users/JoshuaWu7"))
            .setHeader("Authorization", "token: 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")
            .GET()
            .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();

        assert(true);
    }

    @Test
    public void smokeTestPUT() throws FileNotFoundException, JsonProcessingException {

        //Request body set up
        Gson gson = new Gson();
        String description = "Created a private gist from Java!";
        String name = "hello_world.txt";
        String content = "UBC IRP Student QA!";

        ServerRequest request_body = new ServerRequest(description, content, false);
        String output_body = gson.toJson(request_body);
        System.out.println(output_body);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/gists"))
            .setHeader("Accept", "application/vnd.github.v3+json")
            .setHeader("Authorization", "Bearer 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")
            .POST(HttpRequest.BodyPublishers.ofString(output_body))
            .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();
    }

}
