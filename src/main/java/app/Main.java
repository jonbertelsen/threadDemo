package app;

import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static void main() throws IOException, InterruptedException {
        System.out.println("Main is running on: " +
                Thread.currentThread().getName());

        long totalStart = System.currentTimeMillis();

        List<String> urls = new ArrayList<String>(Arrays.asList(
            "https://3sem.kursusmaterialer.dk/",
            "https://ekstrabladet.dk/",
                "https://www.campusbornholm.dk/"));

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        urls.forEach( url -> {
            try {
                FetchResultDTO fetchResultDTO = HttpFetcher.fetch(client, url);
                System.out.println(fetchResultDTO);

            }
            catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        long totalDuration = System.currentTimeMillis() - totalStart;

        System.out.println("Det hele tog: " + totalDuration + " ms");

    }
}
