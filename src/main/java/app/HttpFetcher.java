package app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpFetcher {



    public static FetchResultDTO fetch(HttpClient client, String url) throws IOException, InterruptedException {
        long totalStart = System.currentTimeMillis();

        // 1. Build an HttpRequest for the URL.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        // 3. Send the request.
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        int httpStatus = response.statusCode();
        int responseSize = response.body().length();
        String threadName = Thread.currentThread().getName();
        long totalDuration = System.currentTimeMillis() - totalStart;


        FetchResultDTO fetchResultDTO = new FetchResultDTO(url, httpStatus, responseSize, totalDuration,  threadName );

        return fetchResultDTO;
    }
}
