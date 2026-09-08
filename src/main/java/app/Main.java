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

        // sequentialFetches(urls);

        Runnable task = () -> {
            System.out.println("Task is running on: "
                    + Thread.currentThread().getName());
        };

        Thread worker = new Thread(task);
        worker.start();

        System.out.println("Main continues on: "
                + Thread.currentThread().getName());

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        List<Thread> threads = new ArrayList<>();

        for (String url: urls){
            Runnable fetchTask = () -> {
                FetchResultDTO result = null;
                try {
                    result = HttpFetcher.fetch(client, url);
                }
                catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(result);
            };

            Thread thread = new Thread(fetchTask);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Main thread was interrupted", ex);
            }
        }

        long totalDuration = System.currentTimeMillis() - totalStart;

        System.out.println("Det hele tog: " + totalDuration + " ms");



    }

    private static void sequentialFetches(List<String> urls) {
        long totalStart = System.currentTimeMillis();



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
