package app;

public record FetchResultDTO(
        String url,
        int statusCode,
        int responseSize,
        long durationMs,
        String threadName
) {
}
