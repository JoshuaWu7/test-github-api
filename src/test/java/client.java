import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import server.ServerRequest;
import server.ServerResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.io.*;
import java.util.concurrent.CompletableFuture;
//import okhttp3.*;


public class client {
    private static final int STATUS_CREATED = 201;
    private static final int STATUS_PENDING = 202;
    private static final int STATUS_OK = 200;

    public static String GIST_PATH = "https://api.github.com/gists";
    public static String ACCEPT = "application/vnd.github.v3+json";
    public static String CONTENTS = "UBC IRP Student QA!";
    public static String DESCRIPTIONS = "Created a private gist from Java!";
    public static String TOKEN = "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";
    public static String FILE_NAME = "hello_world.txt";

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
    public void smokeTestPUT() throws IOException, InterruptedException {

        //Request body set up
        Gson gson = new Gson();

        ServerRequest request_body = new ServerRequest(DESCRIPTIONS, CONTENTS, false);
        String output_body = gson.toJson(request_body);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(GIST_PATH))
            .setHeader("Accept", ACCEPT)
            .setHeader("Authorization", "Bearer " + TOKEN)
            .POST(HttpRequest.BodyPublishers.ofString(output_body))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ServerResponse response_body = gson.fromJson(response.body(), ServerResponse.class);

        ServerResponse expected_body = new ServerResponse();
        expected_body.setDescription(DESCRIPTIONS);

        //Verify POST status was successful
        assertEquals(STATUS_CREATED, response.statusCode());

        //Verify the Expected Content
        //Create the new gist URL path
        StringBuilder sb = new StringBuilder(GIST_PATH);
        sb.append("/");
        sb.append(response_body.getID());
        System.out.println("New gist URL: " + sb.toString());

        HttpRequest requestGet = HttpRequest.newBuilder()
            .uri(URI.create(sb.toString()))
            .setHeader("Accept", ACCEPT)
            .setHeader("Authorization", "Bearer " + TOKEN)
            .GET()
            .build();

        HttpResponse<String> response_from_get = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
        //Verify that GET request was OK
        assertEquals(STATUS_OK, response_from_get.statusCode());
        ServerResponse response_body_from_get = gson.fromJson(response_from_get.body(), ServerResponse.class);

        //Verify that content is as expected
        System.out.println(response_body_from_get.toString());
        assertEquals(CONTENTS, response_body_from_get.getFiles().getFile().getContent());

    }

}
